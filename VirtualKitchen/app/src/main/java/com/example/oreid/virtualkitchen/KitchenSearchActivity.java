package com.example.oreid.virtualkitchen;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class KitchenSearchActivity extends KitchenTab {

    private String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTabName("Search");
        setContentViewId(R.layout.activity_kitchen_search);
        setListViewId(R.id.list_view_searchresults);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        handleIntent(getIntent());
    }

    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    public void handleIntent(Intent intent) {
        // handles search query being passed to the activity via an intent
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    public void doSearch(String query) {
        this.searchQuery = query;

        ArrayList<FoodItem> results = db.findByName(query);

        setListData(results);
        updateUI();
    }

    public void deleteButtonHandler(View v) {

        // index of where the item can be found in the list's data.
        int position = listView.getPositionForView((View)v.getParent());

        // get the item from the list and let the database figure out where to delete it from.
        FoodItem toDecrement = getListData().get(position);

        db.decrement(toDecrement);


        // redo the search because data has changed
        doSearch(this.searchQuery);
        updateUI();

    }


}
