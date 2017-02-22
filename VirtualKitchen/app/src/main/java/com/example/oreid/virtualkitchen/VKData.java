package com.example.oreid.virtualkitchen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * VirtualKitchen Data
 *
 * In any class where the application needs to have access to the data (e.g profile, database of
 * items, etc) it can be accessed using VKData.getInstance() and then calling any available
 * methods to set and get data within VKData.
 *
 * Created by hollie on 22/02/2017.
 */

public class VKData {

    private ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
    private static final VKData holder = new VKData(); // static singleton class needs this

    public ArrayList<FoodItem> getFoodItems() { return foodItems; }

    public ArrayList<FoodItem> getSortedFoodItems() {
        ArrayList<FoodItem> sortedFood = (ArrayList<FoodItem>)foodItems.clone();
        Collections.sort(sortedFood, new Comparator<FoodItem>() {
            @Override
            public int compare(FoodItem f1, FoodItem f2) {
                return  f1.getName().compareTo(f2.getName());
            }
        });
        return sortedFood;
    }

    private ArrayList<FoodItem> filterByLocation(String l) {
        ArrayList<FoodItem> filtered = new ArrayList<FoodItem>();
        for (FoodItem f : foodItems) {
            if (f.getLocation().equals(l)) {
                filtered.add(f);
            }
        }

        return filtered;

    }

    public ArrayList<FoodItem> getFridgeFoodItems() {
        return filterByLocation("Fridge");
    }

    public ArrayList<FoodItem> getFreezerFoodItems() {
        return filterByLocation("Freezer");
    }

    public ArrayList<FoodItem> getCupboardFoodItems() {
        return filterByLocation("Cupboard");
    }


    public void addFoodItem(FoodItem f) { foodItems.add(f); }

    public void addTestFoodItems() {
        addFoodItem(new FoodItem("Chicken", 1, "Fridge", 5));
        addFoodItem(new FoodItem("Banana", 4, "Cupboard", 7));
        addFoodItem(new FoodItem("Oven chips", 1, "Freezer", 60));
        addFoodItem(new FoodItem("Tomatoes", 1, "Fridge", 10));
    }

    // return instance of VKData to work with.
    public static VKData getInstance() { return holder; }


}
