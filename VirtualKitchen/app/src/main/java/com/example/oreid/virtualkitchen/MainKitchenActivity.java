package com.example.oreid.virtualkitchen;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainKitchenActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        // tab activity doesn't support action bar

        setupTabs();
    }

    private void setupTabs() {
        VKData.getInstance().addTestFoodItems();
        TabHost th = getTabHost();

        TabSpec specAZ = th.newTabSpec("All");
        Intent azIntent = new Intent(MainKitchenActivity.this, SortedTab.class);
        specAZ.setContent(azIntent);
        specAZ.setIndicator("All");
        th.addTab(specAZ);

        TabSpec specFridge = th.newTabSpec("Fridge");
        Intent fridgeIntent = new Intent(MainKitchenActivity.this, FridgeTab.class);
        specFridge.setContent(fridgeIntent);
        specFridge.setIndicator("Fridge");
        th.addTab(specFridge);

        TabSpec specFreezer = th.newTabSpec("Freezer");
        Intent freezerIntent = new Intent(MainKitchenActivity.this, FreezerTab.class);
        specFreezer.setContent(freezerIntent);
        specFreezer.setIndicator("Freezer");
        th.addTab(specFreezer);

        TabSpec specCupboard = th.newTabSpec("Pantry");
        Intent cupboardIntent = new Intent(MainKitchenActivity.this, CupboardTab.class);
        specCupboard.setContent(cupboardIntent);
        specCupboard.setIndicator("Pantry");
        th.addTab(specCupboard);

    }


}
