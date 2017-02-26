package com.example.oreid.virtualkitchen;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class manages storage of items in the virtual kitchen.
 * It includes storage of food in the fridge, freezer and cupboard, along with methods to manipulate
 * and access this data. E.g sorting, or 'addItem' which determines which storage area to add to.
 *
 * Created by hollie on 25/02/2017.
 */

public class FoodStorageData {

    /**
     * A convenient method to get items the items from a specific storage area
     * @param storageArea storage area to get items from.
     * @return items stored in this storage area
     */
    public ArrayList<FoodItem> get(StorageArea storageArea) {
        switch(storageArea) {
            case FRIDGE:
                return getFridgeItems();
            case FREEZER:
                return getFreezerItems();
            case CUPBOARD:
                return getCupboardItems();
            default:
                return null;
        }
    }

    public ArrayList<FoodItem> getFridgeItems() {
        return this.fridge;
    }

    public void addToFridge(FoodItem f) {
        this.fridge.add(f);
    }

    public ArrayList<FoodItem> getFreezerItems() {
        return this.freezer;
    }

    public void addToFreezer(FoodItem f) {
        this.freezer.add(f);
    }

    public ArrayList<FoodItem> getCupboardItems() {
        return this.cupboard;
    }

    public void addToCupboard(FoodItem f) {
        this.cupboard.add(f);
    }

    public ArrayList<FoodItem> getAllItems() {
        ArrayList<FoodItem> allItems = new ArrayList<FoodItem>();
        allItems.addAll(getFridgeItems());
        allItems.addAll(getFreezerItems());
        allItems.addAll(getCupboardItems());

        return allItems;
    }

    /**
     * General add. Determines where to put the item based on it's location.
     * @param f item to add
     */
    public void add(FoodItem f) {
        switch(f.getLocation()) {
            case FRIDGE:
                addToFridge(f);
                break;
            case FREEZER:
                addToFreezer(f);
                break;
            case CUPBOARD:
                addToCupboard(f);
                break;
            default:
                // ERROR - storage area is not recognised.
                Log.d(TAG, "Unable to add item because of invalid storage area \'" + f.getLocation() + "\'.");

        }
    }

    public FoodItem remove(int index, StorageArea s) {

        if (s == null) {
           Log.d(TAG, "Storage area not specified, not enough information to remove an item.");
        }

        FoodItem f = null;

        switch(s) {
            case FRIDGE:
                f = this.fridge.get(index);
                this.fridge.remove(index);
                break;
            case FREEZER:
                f = this.freezer.get(index);
                this.freezer.remove(index);
                break;
            case CUPBOARD:
                f = this.cupboard.get(index);
                this.cupboard.remove(index);
            default:
                // nothing
        }

        return f;
    }

    public FoodItem decrement(int index, StorageArea s) {

        if (s == null) {
            Log.d(TAG, "Storage area not specified, not enough information to remove an item.");
        }

        FoodItem f = null;

        switch(s) {
            case FRIDGE:
                f = this.fridge.get(index);
                break;
            case FREEZER:
                f = this.freezer.get(index);
                break;
            case CUPBOARD:
                f = this.cupboard.get(index);
            default:
                // nothing
        }

        if (f == null) {
            return null; // not found
        }

        FoodItem toReturn = (FoodItem)f.clone();
        int qty = f.getQty();
        if (qty == 1) {
            remove(index,s);
        } else {
            f.setQty(qty - 1); // decrement quantity
        }

        return toReturn;
    }



    /**
     * Sorts an arrayList of foods using the given comparator.
     * @param food list of food to sort
     * @param sortBy comparator indicating a method to sort by
     * @return
     */
    public static ArrayList<FoodItem> sort(ArrayList<FoodItem> food, Comparator sortBy) {
        Collections.sort(food, sortBy);
        return food;
    }

    public static final String TAG = "FoodStorageData";
    private ArrayList<FoodItem> fridge = new ArrayList<FoodItem>();
    private ArrayList<FoodItem> freezer = new ArrayList<FoodItem>();
    private ArrayList<FoodItem> cupboard = new ArrayList<FoodItem>();

}
