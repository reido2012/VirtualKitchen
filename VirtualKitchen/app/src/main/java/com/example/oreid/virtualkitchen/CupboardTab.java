package com.example.oreid.virtualkitchen;

import android.os.Bundle;

/**
 * Cupboard view in kitchen view
 *
 * Created by hollie on 22/02/2017.
 */

public class CupboardTab extends KitchenTab {

    public static final String TAG = "A_TO_Z_TAB";

    public void onCreate(Bundle savedInstanceState) {
        setTabName("Cupboard");
        super.onCreate(savedInstanceState);
        super.setListData(VKData.getInstance().getCupboardFoodItems());
        updateUI();
    }

    public void updateUI() {
        setListData(VKData.getInstance().getCupboardFoodItems());
        super.updateUI();
    }
}
