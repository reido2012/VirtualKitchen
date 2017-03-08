package com.example.oreid.virtualkitchen;

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
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import static com.example.oreid.virtualkitchen.StorageArea.FRIDGE;

public class MainKitchenActivity extends AppCompatActivity {

    private static final String TAG = "MainKitchenActivity";

    private TabHost tabHost;

    // Information about the tabs
    private final int NUM_TABS = 4;
    private final String[] TAB_NAMES = {"All",
                                        StorageArea.FRIDGE.toString(),
                                        StorageArea.FREEZER.toString(),
                                        StorageArea.CUPBOARD.toString()};
    private final Class[] TAB_ACTIVITIES = {SortedTab.class,
                                            FridgeTab.class,
                                            FreezerTab.class,
                                            CupboardTab.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kitchen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // tab activity doesn't support action bar

        VirtualKitchenProfile vkp = VKData.getInstance().getProfile();

        if (vkp.getFoodDB() == null) {
            vkp.setFoodDB(new FoodStorageData());
            addSampleData();
        }

        tabHost = (TabHost)findViewById(R.id.tabhost);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);

        setupTabs();

    }

    private void addSampleData() {
        FoodStorageData fsd = VKData.getInstance().getFoodDB();

        fsd.add(new FoodItem("Chicken", 1, FRIDGE, 3));
        fsd.add(new FoodItem("Eggs", 6, FRIDGE, 10));
        fsd.add(new FoodItem("Bananas", 3, StorageArea.CUPBOARD, 6));
        fsd.add(new FoodItem("Ice Cream", 2, StorageArea.FREEZER, 60));

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

        TabSpec shoppingList = th.newTabSpec("List");

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kitchen_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(menu.findItem(R.id.search));
        Log.d(TAG, "search view: " + searchView.toString());

        Log.d(TAG, "search item: " + menu.findItem(R.id.search).toString());

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(getApplicationContext(), KitchenSearchActivity.class)));


        return true;
    }




}
