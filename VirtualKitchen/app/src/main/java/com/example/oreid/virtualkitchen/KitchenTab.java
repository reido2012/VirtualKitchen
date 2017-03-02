package com.example.oreid.virtualkitchen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private int contentViewId = R.layout.activity_kitchen_tab;
    private int listViewId  = R.id.list_view;
    private String tabName = "Kitchen";

    private ArrayList<FoodItem> listData = new ArrayList<FoodItem>();

    ListView listView;
    FoodItemAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewId);

        Log.d(TAG, Integer.toString(listViewId));
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
                        VKData.getInstance().addFoodItem(new FoodItem(name, 1, tabName, 3));
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

}
