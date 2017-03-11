package com.example.oreid.virtualkitchen;

import android.os.Bundle;

/**
 * Created by daniel on 03/03/2017.
 */

public class ShoppingListTab extends KitchenTab{

    public static final String TAG = "A_TO_Z_TAB";

    public void onCreate(Bundle savedInstanceState) {
        setTabName("Shopping List");
        super.onCreate(savedInstanceState);
        super.setStorageArea(StorageArea.SHOPPINGLIST,true);
        updateUI();
    }

    public void updateUI() {
        updateListData();
        super.updateUI();
    }
}
