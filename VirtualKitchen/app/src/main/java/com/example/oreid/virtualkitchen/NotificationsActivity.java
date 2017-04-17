package com.example.oreid.virtualkitchen;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import static com.example.oreid.virtualkitchen.KitchenTab.REQUEST_CODE;
import static com.example.oreid.virtualkitchen.StorageArea.SHOPPINGLIST;

public class NotificationsActivity extends AppCompatActivity {

    protected FoodStorageData db = VKData.getInstance().getFoodDB();

    private static final String TAG = "Notifications";

    private ListView notificationList;
    private ArrayAdapter listAdapter;
    private ArrayList<Notification> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        notificationList = (ListView)findViewById(R.id.list_view_notifications);
        listAdapter = new NotificationAdapter(this,
                R.layout.notification_list_item, listData);
        notificationList.setAdapter(listAdapter);

        updateList();
    }

    public void updateList() {
        listData = db.getAllNotifications();

        updateUI();

    }

    public void updateUI() {
        listAdapter.clear();
        listAdapter.addAll(listData);
        listAdapter.notifyDataSetChanged();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent newIntent;

        switch (item.getItemId()) {
            case R.id.add:
                Intent intentItem = new Intent(NotificationsActivity.this, AddItem.class);
                intentItem.putExtra("STORAGEAREA", SHOPPINGLIST.toString());
                startActivityForResult(intentItem,REQUEST_CODE);
                break;
            case R.id.action_kitchen:

                newIntent = new Intent(NotificationsActivity.this, MainKitchenActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_notifications:

                newIntent = new Intent(NotificationsActivity.this, NotificationsActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_recipes:

                newIntent = new Intent(NotificationsActivity.this, RecipeFinderActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_shoppingList:

                newIntent = new Intent(NotificationsActivity.this, ShoppinglistActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_settings:

                newIntent = new Intent(NotificationsActivity.this, MainKitchenActivity.class);
                startActivity(newIntent);

                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kitchen_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(getApplicationContext(), KitchenSearchActivity.class)));


        return true;
    }
}
