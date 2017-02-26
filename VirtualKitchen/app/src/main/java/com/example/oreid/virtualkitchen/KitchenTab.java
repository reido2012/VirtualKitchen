package com.example.oreid.virtualkitchen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Base Kitchen View Tab Class
 *
 * Sets up a list view showing items.
 *
 * create by Hollie on 22/02/17
 *
 */

public class KitchenTab extends AppCompatActivity {

    private static final String TAG = "KitchenTab";

    protected FoodStorageData db;

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

        db = VKData.getInstance().getFoodDB();

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

    /**
     * Updates list data based on the specified storage area. If null, then all items are retrieved.
     */
    public void updateListData() {
        if (this.storageArea == null) {
            this.setListData(db.getAllItems());
        } else { // get items based on specified storage area.
            this.setListData(db.get(this.storageArea));
        }
    }

    /**
     * Updates the list view.
     */
    public void updateUI() {
        listAdapter.clear();
        listAdapter.addAll(listData);
        listAdapter.notifyDataSetChanged();
    }

    // called when add button is pressed.
    // TODO hollie 22/02/17 this should lead to a new intent where the food can be created properly.
    public void addFood(View v) {
        final EditText txtField = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add an item.")
                .setMessage("Name of item to add to the " + tabName)
                .setView(txtField)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = String.valueOf(txtField.getText());
                        db.add(new FoodItem(name, 1, storageArea, 3));
                        updateUI();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
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
     * @param updateList update the list based on the storage area?
     */
    public void setStorageArea(StorageArea sa, boolean updateList) {
        this.storageArea = sa;
        if (updateList) {
            this.updateListData();
        }
    }

    // activity is resumed when it's tab is selected.
    // Lists may have changed while in another tab, so update them.
    public void onResume() {
        super.onResume();
        updateUI();
    }


}
