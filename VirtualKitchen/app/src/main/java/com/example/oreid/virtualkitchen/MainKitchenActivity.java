package com.example.oreid.virtualkitchen;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import static android.icu.util.MeasureUnit.CUP;
import static com.example.oreid.virtualkitchen.KitchenTab.REQUEST_CODE;
import static com.example.oreid.virtualkitchen.R.string.storage;
import static com.example.oreid.virtualkitchen.StorageArea.CUPBOARD;
import static com.example.oreid.virtualkitchen.StorageArea.FREEZER;
import static com.example.oreid.virtualkitchen.StorageArea.FRIDGE;
import static com.example.oreid.virtualkitchen.StorageArea.SHOPPINGLIST;

public class MainKitchenActivity extends AppCompatActivity {

    private static final String TAG = "MainKitchenActivity";

    private TabHost tabHost;

    // Information about the tabs
    private final int NUM_TABS = 5;
    private final StorageArea[] storageAreas = {null, StorageArea.FRIDGE, StorageArea.FREEZER, StorageArea.CUPBOARD, StorageArea.SHOPPINGLIST};
    private final String[] TAB_NAMES = {"All",
                                        StorageArea.FRIDGE.toString(),
                                        FREEZER.toString(),
                                        CUPBOARD.toString(),
                                        SHOPPINGLIST.toString()};
    private final Class[] TAB_ACTIVITIES = {SortedTab.class,
                                            FridgeTab.class,
                                            FreezerTab.class,
                                            CupboardTab.class,
                                            ShoppingListTab.class};

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

        setupTabs();

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
        Log.d(TAG, "Activity result: request_code = " + requestCode);
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
        switch (item.getItemId()) {
            case R.id.add:
                Intent intentItem = new Intent(MainKitchenActivity.this, AddItem.class);
                intentItem.putExtra("STORAGEAREA", TAB_NAMES[tabHost.getCurrentTab()]);
                startActivityForResult(intentItem,REQUEST_CODE);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
