package com.example.oreid.virtualkitchen;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A-Z view in kitchen view
 *
 * Created by hollie on 22/02/2017.
 * TODO Hollie 22/02/17 when switching to this tab, the list may have changed (added to from other
 *                      tabs, so the list needs to be reloaded (updateUI() called)
 */

public class SortedTab extends KitchenTab {

    private static final String TAG = "A_TO_Z_TAB";

    private Spinner selectSortingMethod;
    private ArrayAdapter<String> spinnerAdapter;
    private int sortingType = 0; // sorting type corresponds to items in the drop-down menu.
    private FoodStorageData db = VKData.getInstance().getFoodDB();


    public void onCreate(Bundle savedInstanceState) {
        // different layout - with sorting spinner
        setContentViewId(R.layout.activity_sorted_tab);
        setListViewId(R.id.list_view_sorted);
        super.onCreate(savedInstanceState);

        setListData(db.getAllItems());
        updateUI();

        // set up spinner
        String[] sortingMethods = {"Name", "Expiry Date"};
        selectSortingMethod = (Spinner)findViewById(R.id.sorting_spinner);
        spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                sortingMethods);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectSortingMethod.setAdapter(spinnerAdapter);

        selectSortingMethod.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                sortingType = position;
                updateUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        db.setListUpdater(null, this);

    }

    public void updateUI() {
        ArrayList<FoodItem> data = db.getAllItems();
        switch(sortingType) {
            case 0:
                FoodStorageData.sort(data,FoodItem.nameComparator);
                break;
            case 1:
                FoodStorageData.sort(data,FoodItem.expiryDateComparator);
                break;
            default:
                return;
        }

        setListData(data);
        super.updateUI();
    }

    public void deleteButtonHandler(View v) {

        // index of where the item can be found in the list's data.
        int position = listView.getPositionForView((View)v.getParent());

        // get the item from the list and let the database figure out where to delete it from.
        FoodItem toDecrement = getListData().get(position);

        db.decrement(toDecrement);
    }

    public void shoppingListHandler(View v) {

        // index of where the item can be found in the list's data.
        int position = listView.getPositionForView((View)v.getParent());

        // get the item from the list and let the database figure out where to delete it from.
        FoodItem toAdd = getListData().get(position);

        db.addToShoppingList(toAdd);

        Toast.makeText(this,"Adding " + toAdd.getName() + " to shopping list.", Toast.LENGTH_SHORT).show();
    }

}
