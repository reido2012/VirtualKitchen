package com.example.oreid.virtualkitchen;

import android.os.Bundle;

import static com.example.oreid.virtualkitchen.StorageArea.FREEZER;

/**
 * Freezer view in kitchen view
 *
 * Created by hollie on 22/02/2017.
 */

public class FreezerTab extends KitchenTab {

    public void onCreate(Bundle savedInstanceState) {
        setTabName("Freezer");
        super.onCreate(savedInstanceState);
        super.setStorageArea(FREEZER);

        setListData(db.get(FREEZER));
        updateUI();
    }


}
