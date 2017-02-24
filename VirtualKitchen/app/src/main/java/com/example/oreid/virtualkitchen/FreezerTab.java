package com.example.oreid.virtualkitchen;

import android.os.Bundle;

/**
 * Freezer view in kitchen view
 *
 * Created by hollie on 22/02/2017.
 */

public class FreezerTab extends KitchenTab {

    public static final String TAG = "A_TO_Z_TAB";

    public void onCreate(Bundle savedInstanceState) {
        setTabName("Freezer");
        super.onCreate(savedInstanceState);
        super.setListData(VKData.getInstance().getFreezerFoodItems());
        updateUI();
    }

    public void updateUI() {
        setListData(VKData.getInstance().getFreezerFoodItems());
        super.updateUI();
    }

}
