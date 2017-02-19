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

        TabHost th = getTabHost();
        Intent testerIntent = new Intent(MainKitchenActivity.this, TabTester.class);

        TabSpec specAZ = th.newTabSpec("A-Z");
        specAZ.setContent(testerIntent);
        specAZ.setIndicator("A-Z");
        specAZ.setContent(testerIntent);
        th.addTab(specAZ);

        TabSpec specFridge = th.newTabSpec("Fridge");
        specFridge.setContent(testerIntent);
        specFridge.setIndicator("Fridge");
        specFridge.setContent(testerIntent);
        th.addTab(specFridge);

        TabSpec specFreezer = th.newTabSpec("Freezer");
        specFreezer.setContent(testerIntent);
        specFreezer.setIndicator("Freezer");
        specFreezer.setContent(testerIntent);
        th.addTab(specFreezer);

        TabSpec specCupboard = th.newTabSpec("Pantry");
        specFridge.setContent(testerIntent);
        specCupboard.setIndicator("Pantry");
        specCupboard.setContent(testerIntent);
        th.addTab(specCupboard);
    }


}
