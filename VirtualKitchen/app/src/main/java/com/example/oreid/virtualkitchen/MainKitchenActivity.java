package com.example.oreid.virtualkitchen;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import static com.example.oreid.virtualkitchen.StorageArea.CUPBOARD;
import static com.example.oreid.virtualkitchen.StorageArea.FREEZER;
import static com.example.oreid.virtualkitchen.StorageArea.FRIDGE;

public class MainKitchenActivity extends AppCompatActivity implements NotificationSenderActivity {

    private static final String TAG = "MainKitchenActivity";

    private TabHost tabHost;
    private static final int REQUEST_CODE = 3;

    // Information about the tabs
    private final int NUM_TABS = 4;
    private final StorageArea[] storageAreas = {null, StorageArea.FRIDGE, StorageArea.FREEZER, StorageArea.CUPBOARD};
    private final String[] TAB_NAMES = {"All",
            FRIDGE.toString(),
            FREEZER.toString(),
            CUPBOARD.toString()};
    private final Class[] TAB_ACTIVITIES = {SortedTab.class,
            FridgeTab.class,
            FreezerTab.class,
            CupboardTab.class};

    private NotificationManager notificationManager;
    private int notificationNo = 0; // each notification needs a unique number (apparently)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kitchen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // tab activity doesn't support action bar

        tabHost = (TabHost)findViewById(R.id.tabhost);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        setupTabs();

        VKData.getInstance().getFoodDB().setNotificationActivity(this);

        update(); // notifications
    }

    // update notifications
    public void update() {
        Notification n = null;
        while ((n = VKData.getInstance().getFoodDB().getNextNotification()) != null) {
            sendNotification(n);
        }
    }

    private void sendNotification(Notification n) {
        String title = "";
        String msg = n.food.getQty() + "x " + n.food.getName() + " in the " + n.food.getLocation();

        switch (n.category) {
            case EXPIRED:
                title = (0 - n.food.getDaysLeft()) + " days out of date";
                break;
            case TODAY:
                title = "Expiring today";
                break;
            case TOMORROW:
                title = "Expiring tomorrow";
                break;
            case SOON:
                title = (n.food.getDaysLeft()) + " days left";
                break;
            default:

        }

        Intent intent = new Intent(this, NotificationsActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo2)
                        .setContentTitle(title)
                        .setContentText(msg)
                        .setContentIntent(pIntent);
        notificationManager.notify(notificationNo++, mBuilder.build());
        Log.d(TAG, "Sending notification for " + n.food.getName());
    }

    private void setupTab(int i) {
        TabSpec spec = tabHost.newTabSpec(TAB_NAMES[i]);
        Intent intent = new Intent(MainKitchenActivity.this, TAB_ACTIVITIES[i]);
        spec.setContent(intent);
        spec.setIndicator(TAB_NAMES[i]);
        tabHost.addTab(spec);

    }

    private void setupTabs() {

        // tabhost has already been set up.

        for (int i = 0; i < NUM_TABS; i++) {
            setupTab(i);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kitchen_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(menu.findItem(R.id.search));

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(getApplicationContext(), KitchenSearchActivity.class)));


        return true;
    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult (requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                String name = data.getStringExtra("NAME");
                String quan = data.getStringExtra("QUAN");
                String expiry = data.getStringExtra("EXP");
                StorageArea storage = StorageArea.fromString(data.getStringExtra("STORAGE"));
                if (storage == null) {
                    return;
                }
                String cat = data.getStringExtra("CAT");
                boolean fav = data.getStringExtra("FAV").equals("True");

                FoodItem newFood = new FoodItem(name, Integer.parseInt(quan), storage, Integer.parseInt(expiry), cat);
                newFood.setFavourite(fav);
                VKData.getInstance().getFoodDB().add(newFood);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent newIntent;

        switch (item.getItemId()) {
            case R.id.add:
                Intent intentItem = new Intent(MainKitchenActivity.this, AddItem.class);
                intentItem.putExtra("STORAGEAREA", TAB_NAMES[tabHost.getCurrentTab()]);
                startActivityForResult(intentItem,REQUEST_CODE);
                break;
            case R.id.action_kitchen:
                newIntent = new Intent(MainKitchenActivity.this, MainKitchenActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_notifications:

                newIntent = new Intent(MainKitchenActivity.this, NotificationsActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_recipes:

//                newIntent = new Intent(MainKitchenActivity.this, RecipeFinderActivity.class);
                newIntent = new Intent(MainKitchenActivity.this, FoodSearch.class);
                startActivity(newIntent);

                return true;

            case R.id.action_shoppingList:

                newIntent = new Intent(MainKitchenActivity.this, ShoppinglistActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_settings:

                newIntent = new Intent(MainKitchenActivity.this, MainKitchenActivity.class);
                startActivity(newIntent);

                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
