package com.example.oreid.virtualkitchen;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import static com.example.oreid.virtualkitchen.StorageArea.SHOPPINGLIST;

/**
 * Shopping list class
 *
 * Sets up a list view showing items.
 *
 * create by Hollie on 22/02/17
 *
 */

public class ShoppinglistActivity extends AppCompatActivity implements HasListView {

    protected FoodStorageData db = VKData.getInstance().getFoodDB();
    private static final int REQUEST_CODE = 3;

    private int contentViewId = R.layout.activity_kitchen_tab;
    private int listViewId  = R.id.list_view;

    private ArrayList<FoodItem> listData = new ArrayList<FoodItem>();

    ListView listView;
    ShoppinglistAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewId);

        listView = (ListView)findViewById(listViewId);
        listAdapter = new ShoppinglistAdapter(this,
                R.layout.shopping_list_item,
                listData);
        listView.setAdapter(listAdapter);

        db.setListUpdater(SHOPPINGLIST, this);

        setListData(db.get(SHOPPINGLIST));
        updateUI();
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

    public void addToKitchenHandler(View v) {

        int position = listView.getPositionForView((View)v.getParent());

        FoodItem toAdd = listData.get(position);

        db.add(toAdd);

        db.remove(position, SHOPPINGLIST);

    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult (requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                String name = data.getStringExtra("NAME");
                String quan = data.getStringExtra("QUAN");
                String expiry = data.getStringExtra("EXP");
                Log.d("ShoppingList", "activity sa " + data.getStringExtra("STORAGE"));

                StorageArea storage = StorageArea.fromString(data.getStringExtra("STORAGE"));
                if (storage == null) {
                    Log.d("ShoppingList", "activity result " + requestCode);
                    return;
                }
                String cat = data.getStringExtra("CAT");
                boolean fav = data.getStringExtra("FAV").equals("True");

                FoodItem newFood = new FoodItem(name, Integer.parseInt(quan), storage, Integer.parseInt(expiry), cat);
                newFood.setFavourite(fav);
                db.add(newFood);

            }
        }
    }

    public void deleteShoppinglistHandler(View v) {

        int position = listView.getPositionForView((View)v.getParent());
        db.remove(position, SHOPPINGLIST);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent newIntent;

        switch (item.getItemId()) {
            case R.id.add:
                Intent intentItem = new Intent(ShoppinglistActivity.this, AddItem.class);
                intentItem.putExtra("STORAGEAREA", SHOPPINGLIST.toString());
                startActivityForResult(intentItem,REQUEST_CODE);
                break;
            case R.id.action_kitchen:

                newIntent = new Intent(ShoppinglistActivity.this, MainKitchenActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_notifications:

                newIntent = new Intent(ShoppinglistActivity.this, MainKitchenActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_recipes:

                newIntent = new Intent(ShoppinglistActivity.this, MainKitchenActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_shoppingList:

                newIntent = new Intent(ShoppinglistActivity.this, ShoppinglistActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_settings:

                newIntent = new Intent(ShoppinglistActivity.this, MainKitchenActivity.class);
                startActivity(newIntent);

                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kitchen_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(getApplicationContext(), KitchenSearchActivity.class)));


        return true;
    }

}
