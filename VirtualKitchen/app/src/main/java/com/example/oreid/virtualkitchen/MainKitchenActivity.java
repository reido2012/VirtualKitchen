package com.example.oreid.virtualkitchen;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class MainKitchenActivity extends TabActivity {

    private static final String TAG = "MainKitchenActivity";

    private TabHost tabHost;

    private final int NUM_TABS = 4;
    private final String[] TAB_NAMES = {"All", "Fridge", "Freezer", "Pantry"};
    private final Class[] TAB_ACTIVITIES = {SortedTab.class,
                                            FridgeTab.class,
                                            FreezerTab.class,
                                            CupboardTab.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        // tab activity doesn't support action bar

        VKData.getInstance().getProfile().setFoodDB(new FoodStorageData());
        setupTabs();

        addSampleData();

        String name = VKData.getInstance().getProfile().getFirstName();
        Toast.makeText(getApplicationContext(), "Hi, " + name + "! Welcome to your virtual kitchen.", Toast.LENGTH_LONG).show();
    }

    private void addSampleData() {
        FoodStorageData fsd = VKData.getInstance().getFoodDB();

        fsd.add(new FoodItem("Chicken", 1, StorageArea.FRIDGE, 3));
        fsd.add(new FoodItem("Eggs", 6, StorageArea.FRIDGE, 10));
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

        tabHost = getTabHost();

        for (int i = 0; i < NUM_TABS; i++) {
            setupTab(i);
        }

    }

}
