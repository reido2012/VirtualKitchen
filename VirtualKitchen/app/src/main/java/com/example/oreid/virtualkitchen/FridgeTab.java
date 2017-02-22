package com.example.oreid.virtualkitchen;

import android.os.Bundle;

/**
 * Fridge view in kitchen view
 *
 * Created by hollie on 22/02/2017.
 */

public class FridgeTab extends KitchenTab {

    public static final String TAG = "A_TO_Z_TAB";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setListData(VKData.getInstance().getFridgeFoodItems());
        updateUI();
    }


}