package com.example.oreid.virtualkitchen;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.oreid.virtualkitchen.KitchenTab.REQUEST_CODE;
import static com.example.oreid.virtualkitchen.StorageArea.RECIPELIST;

public class FoodSearch extends AppCompatActivity implements HasListView{


    protected FoodStorageData db = VKData.getInstance().getFoodDB();

    private int contentViewId = R.layout.activity_food_search;
    private int listViewId  = R.id.food_for_recipe_list;
    //    ImageButton check;
    private StorageArea storageArea = null;
    private ArrayList<String> foodToSearch = new ArrayList<>();
    private ArrayList<FoodItem> listData = new ArrayList<FoodItem>();
    ListView listView;
    FoodItemSearchAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentViewId);
        listView = (ListView)findViewById(this.listViewId);
        listAdapter = new FoodItemSearchAdapter(this,
                R.layout.food_search_list_item,
                listData);
        listView.setAdapter(listAdapter);

//        check = (ImageButton) findViewById(R.id.list_btn_food_search);
        db.setListUpdater(RECIPELIST, this);
        setListData(db.getAllItems());
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

    public void setContentViewId(int id) {
        contentViewId = id;
    }

    public void setListViewId(int id) {
        listViewId = id;
    }



    /**
     * Updates the list view.
     */
    public void updateUI() {
        listAdapter.clear();
        listAdapter.addAll(listData);
        listAdapter.notifyDataSetChanged();
    }

    public void foodSearchQueryHandler(View view) {
        int position = listView.getPositionForView((View)view.getParent());
        FoodItem toAdd = listData.get(position);
        foodToSearch.add(toAdd.getName());
//
        Toast.makeText(this, "Searching for recipes containing" + toAdd.getName(), Toast.LENGTH_SHORT).show();

//        db.add(toAdd);
//        db.remove(position, RECIPELIST);
    }

    public void callRecipeSearch(View view) {
        Intent intent = new Intent(FoodSearch.this, RecipeFinderActivity.class);

        intent.putExtra("FOOD", foodToSearch);
        startActivity(intent);
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

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent newIntent;

        switch (item.getItemId()) {
            case R.id.add:
                Intent intentItem = new Intent(FoodSearch.this, AddItem.class);
                startActivityForResult(intentItem,REQUEST_CODE);
                break;
            case R.id.action_kitchen:
                newIntent = new Intent(FoodSearch.this, MainKitchenActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_notifications:

                newIntent = new Intent(FoodSearch.this, NotificationsActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_recipes:

//                newIntent = new Intent(MainKitchenActivity.this, RecipeFinderActivity.class);
                newIntent = new Intent(FoodSearch.this, FoodSearch.class);
                startActivity(newIntent);

                return true;

            case R.id.action_shoppingList:

                newIntent = new Intent(FoodSearch.this, ShoppinglistActivity.class);
                startActivity(newIntent);

                return true;

            case R.id.action_settings:

                newIntent = new Intent(FoodSearch.this, MainKitchenActivity.class);
                startActivity(newIntent);

                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
