package com.example.oreid.virtualkitchen;

import android.os.Bundle;

/**
 * A-Z view in kitchen view
 *
 * Created by hollie on 22/02/2017.
 * TODO Hollie 22/02/17 when switching to this tab, the list may have changed (added to from other
 *                      tabs, so the list needs to be reloaded (updateUI() called)
 */

public class SortedTab extends KitchenTab {

    public static final String TAG = "A_TO_Z_TAB";

    public void onCreate(Bundle savedInstanceState) {
        // different layout - with sorting spinner
        setContentViewId(R.layout.activity_sorted_tab);
        setListViewId(R.id.list_view_sorted);
        super.onCreate(savedInstanceState);

        setListData(VKData.getInstance().getSortedFoodItems());
        updateUI();
    }

    public void updateUI() {
        setListData(VKData.getInstance().getSortedFoodItems());
        super.updateUI();
    }

}
