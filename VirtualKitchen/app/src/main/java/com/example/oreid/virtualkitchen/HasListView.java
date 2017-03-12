package com.example.oreid.virtualkitchen;

import java.util.ArrayList;

/**
 *
 * Pages of the app with a list view could implement this interface so that the database can notify
 * it when the list of data has changed.
 *
 * Created by hollie on 12/03/2017.
 */

public interface HasListView {

    /**
     * Gives the page with list view an updated list of data.
     * The page should update it's UI to display this list.
     * @param newList new list of data to display.
     */
    public void setUpdatedList(ArrayList<FoodItem> newList);

}
