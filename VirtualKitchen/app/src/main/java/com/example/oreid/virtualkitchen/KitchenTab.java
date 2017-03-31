package com.example.oreid.virtualkitchen;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Base Kitchen View Tab Class
 *
 * Sets up a list view showing items.
 *
 * create by Hollie on 22/02/17
 *
 */

public class KitchenTab extends AppCompatActivity implements HasListView {

    private static final String TAG = "KitchenTab";

    protected FoodStorageData db = VKData.getInstance().getFoodDB();

    static final int REQUEST_CODE=1;


    private int contentViewId = R.layout.activity_kitchen_tab;
    private int listViewId  = R.id.list_view;
    private String tabName = "Kitchen";
    private StorageArea storageArea = null;

    private ArrayList<FoodItem> listData = new ArrayList<FoodItem>();

    ListView listView;
    FoodItemAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewId);

        listView = (ListView)findViewById(listViewId);
        listAdapter = new FoodItemAdapter(this,
                R.layout.food_list_item,
                listData);
        listView.setAdapter(listAdapter);


    }

    /**
     * Set the list's data
     * @param d list's new data
     */
    public void setListData(ArrayList<FoodItem> d) {
        this.listData = d;
    }
    public ArrayList<FoodItem> getListData() { return this.listData; }

    public void setUpdatedList(ArrayList<FoodItem> newFood) {
        setListData(newFood);
        updateUI();
    }

    /**
     * Updates the list view.
     */
    public void updateUI() {
        listAdapter.clear();
        listAdapter.addAll(listData);
        listAdapter.notifyDataSetChanged();
    }
//
//<<<<<<< HEAD
//
//    public void shoppingListHandler(View v) {
//
//        // index of where the item can be found in the list's data.
//        int position = listView.getPositionForView((View) v.getParent());
//
//        db.addToShoppingList(position, this.storageArea);
//
//        updateUI();
//    }
//
//    // called when add button is pressed.
//    // TODO hollie 22/02/17 this should lead to a new intent where the food can be created properly.
//    public void addFood(View v) {
//        Intent intentItem = new Intent(KitchenTab.this, AddItem.class);
//        startActivityForResult(intentItem,REQUEST_CODE);
//       /* final EditText txtField = new EditText(this);
//        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle("Add an item.")
//                .setMessage("Name of item to add to the " + tabName)
//                .setView(txtField)
//                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String name = String.valueOf(txtField.getText());
//                        db.add(new FoodItem(name, 1, storageArea, 3));
//                    }
//                })
//                .setNegativeButton("Cancel", null)
//                .create();
//        dialog.show();
//*/
//
//    }
//=======

//>>>>>>> parent of 918f51a... Changed how storage area lists are updated

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if(requestCode==1){
            if (resultCode == Activity.RESULT_OK){
                String name = data.getStringExtra("NAME");
                String quan = data.getStringExtra("QUAN");
                String expiry = data.getStringExtra("EXP");
                db.add(new FoodItem (name,Integer.parseInt(quan),storageArea,Integer.parseInt(expiry) ));
                updateUI();
            }
        }
    }
    public void deleteButtonHandler(View v) {

        // index of where the item can be found in the list's data.
        int position = listView.getPositionForView((View)v.getParent());

        db.decrement(position, this.storageArea);

        updateUI();

    }

    public void setContentViewId(int id) {
        contentViewId = id;
    }

    public void setListViewId(int id) {
        listViewId = id;
    }

    public void setTabName(String name) {
        tabName = name;
    }

    /**
     * Sets storage area associated with this tab. Also updates the list based on this data,
     * depending on the boolean passed in.
     * @param sa storage area associated with this tab
     */
    public void setStorageArea(StorageArea sa) {
        this.storageArea = sa;
        db.setListUpdater(this.storageArea, this);
    }

    public StorageArea getStorageArea() {
        return this.storageArea;
    }
}
