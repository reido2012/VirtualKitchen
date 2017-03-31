package com.example.oreid.virtualkitchen;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static com.example.oreid.virtualkitchen.StorageArea.CUPBOARD;
import static com.example.oreid.virtualkitchen.StorageArea.FREEZER;
import static com.example.oreid.virtualkitchen.StorageArea.FRIDGE;
import static com.example.oreid.virtualkitchen.StorageArea.SHOPPINGLIST;

/**
 * This class manages storage of items in the virtual kitchen.
 * It includes storage of food in the fridge, freezer and cupboard, along with methods to manipulate
 * and access this data. E.g sorting, or 'addItem' which determines which storage area to add to.
 *
 * Created by hollie on 25/02/2017.
 */

public class FoodStorageData implements ChildEventListener {

    /**
     * Attach a class with list view so it can be updated when a storage area is updated.
     * @param s storage area associated with the tab.
     * @param l page with list view.
     */
    public void setListUpdater(StorageArea s, HasListView l) {
        if (s == null) { // none specified, use all.
            listUpdate.put("All",l);
        } else {
            listUpdate.put(s.toString(),l);
        }
    }

    private void updateLists(StorageArea s) {
        HasListView lv = listUpdate.get(s.toString());
        if (lv != null) {
            lv.setUpdatedList(get(s));
        }
        lv = listUpdate.get("All"); // if we have an all, update that too.
        if (lv != null) {
            lv.setUpdatedList(getAllItems());
        }
    }

    private boolean clearMyKitchen = true;

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        FoodItem f = dataSnapshot.getValue(FoodItem.class);
        kitchen.get(f.getLocation()).add(f);
        updateLists(f.getLocation());


    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    /**
     * A convenient method to get the items from a specific storage area
     * @param storageArea storage area to get items from.
     * @return items stored in this storage area
     */
    public ArrayList<FoodItem> get(StorageArea storageArea) {
        return kitchen.get(storageArea);
    }

    public ArrayList<FoodItem> getFridgeItems() {
        return kitchen.get(FRIDGE);
    }

    public void addToFridge(FoodItem f) {
        dbRef.child("users").child(userId).child(FRIDGE.toString()).push().setValue(f);
    }

    public ArrayList<FoodItem> getFreezerItems() {
        return kitchen.get(FREEZER);
    }

    public void addToFreezer(FoodItem f) {
        dbRef.child("users").child(userId).child(FREEZER.toString()).push().setValue(f);

    }

    public ArrayList<FoodItem> getCupboardItems() {
        return kitchen.get(CUPBOARD);
    }

    public void addToCupboard(FoodItem f) {
        dbRef.child("users").child(userId).child(CUPBOARD.toString()).push().setValue(f);
    }

    public ArrayList<FoodItem> getShoppingListItems() {
        return kitchen.get(SHOPPINGLIST);
    }

    public void addToShoppingList(FoodItem f) {
        dbRef.child("users").child(userId).child(SHOPPINGLIST.toString()).push().setValue(f);

    }

    public ArrayList<FoodItem> getAllItems() {
        ArrayList<FoodItem> allItems = new ArrayList<FoodItem>();
        allItems.addAll(getFridgeItems());
        allItems.addAll(getFreezerItems());
        allItems.addAll(getCupboardItems());

        return allItems;
    }

    /**
     *  Mass moves every shopping list item to storage. Currently only adds to fridge as no storage data kept for
     *  shoppinglist items.
     *  TODO Add a button for this feature.
     *
     */
    public void allShoppingListToStorage() {
        ArrayList<FoodItem> tempList = getShoppingListItems();
        for (int i=0;i<tempList.size();i++) {
            addToFridge(tempList.get(i));
            remove(i,SHOPPINGLIST);
        }
    }

    /**
     * Adds a single food item to fridge from shoppinglist
     * @param f item to add to fridge and remove from shoppinglist.
     * TODO Add button for this
     */
    public void shoppingListItemToStorage(FoodItem f) {
        addToFridge(f);
        removeFromFirebase(f.getName(), SHOPPINGLIST.toString());
    }

    /**
     * Adds single food item to shoppinglist from a storage location
     * @param f a single food item to add to shopping list
     * @param storageName storage location to take item from
     *  TODO add functionality to button
     */
    public void storageItemToShoppingList(FoodItem f, String storageName) {
        addToShoppingList(f);
        removeFromFirebase(f.getName(), storageName.toString());
    }


    /**
     * General add. Determines where to put the item based on it's location.
     * @param f item to add
     */
    public void add(FoodItem f) {
        dbRef.child("users").child(userId).child(f.getLocation().toString()).push().setValue(f);
    }

    // a really bad way of updating an item.
    private void firebaseReplaceQuery(String foodName, String storageArea, FoodItem newItem) {
        removeFromFirebase(foodName, storageArea);
        add(newItem);
    }

    private void firebaseQuery(String foodName, String storageName, final FoodItem newFood) {
        // Query firebase for the data
        Query removeQuery = dbRef.child("users").child(userId).child(storageName).orderByChild("name").equalTo(foodName);

        removeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s: dataSnapshot.getChildren()) {
                    s.getRef().setValue(newFood);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    private void removeFromFirebase(String foodName, String storageName) {
        // Query firebase for the data
        Query removeQuery = dbRef.child("users").child(userId).child(storageName).orderByChild("name").equalTo(foodName);

        removeQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s: dataSnapshot.getChildren()) {
                    s.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    public void remove(int index, StorageArea s) {
        FoodItem f = get(s).get(index);

        removeFromFirebase(f.getName(), s.toString());
        get(s).remove(index);

        updateLists(s);
    }

    public void decrement(int index, StorageArea s) {

        ArrayList<FoodItem> area = get(s);
        FoodItem f = area.get(index);

        int qty = f.getQty();
        if (qty <= 1) {
            remove(index,s);
        } else {
            f.setQty(qty - 1); // decrement quantity
            firebaseQuery(f.getName(), s.toString(), f);
            kitchen.get(s).set(index,f);
            updateLists(s);
        }

    }

    public void remove(FoodItem f) {
        StorageArea s = f.getLocation();
        ArrayList area = get(s);

        int index = area.indexOf(f);

        remove(index,s);
    }

    public void decrement(FoodItem f) {
        StorageArea s = f.getLocation();
        ArrayList area = get(s);
        int index = area.indexOf(f);

        decrement(index,s);
    }



    public ArrayList<FoodItem> findByName(String query) {
        if (query.equals("")) { // no query gets empty list.
            return new ArrayList<FoodItem>();
        }
        // very basic approach where it goes through all items and finds all with the given string in the name.
        ArrayList<FoodItem> allItems = getAllItems();
        ArrayList<FoodItem> searchResults = new ArrayList<FoodItem>();

        query = query.toUpperCase();

        for (int i = 0; i < allItems.size(); i++) {
            FoodItem currentItem = allItems.get(i);
            String itemName = currentItem.getName().toUpperCase();
            if (itemName.contains(query)) {
                searchResults.add(currentItem);
            }
        }
        return searchResults;
    }

    /**
     * Sorts an arrayList of foods using the given comparator.
     * @param food list of food to sort
     * @param sortBy comparator indicating a method to sort by
     * @return the passed ArrayList, sorted.
     */
    public static ArrayList<FoodItem> sort(ArrayList<FoodItem> food, Comparator sortBy) {
        Collections.sort(food, sortBy);
        return food;
    }

    /**
     * Creates a new instance of the food storage database (or interface with the Firebase)
     * @param uID firebase user id, used to get access to user's data.
     */
    public FoodStorageData(String uID) {
        this.userId = uID;

        // Listen for changes on the firebae.
        dbRef.child("users").child(userId).child(FRIDGE.toString()).addChildEventListener(this);
        dbRef.child("users").child(userId).child(FREEZER.toString()).addChildEventListener(this);
        dbRef.child("users").child(userId).child(CUPBOARD.toString()).addChildEventListener(this);
        dbRef.child("users").child(userId).child(SHOPPINGLIST.toString()).addChildEventListener(this);

        kitchen.put(FRIDGE, new ArrayList<FoodItem>());
        kitchen.put(FREEZER, new ArrayList<FoodItem>());
        kitchen.put(CUPBOARD, new ArrayList<FoodItem>());
        kitchen.put(SHOPPINGLIST, new ArrayList<FoodItem>());

//        if (clearMyKitchen) {
//            dbRef.child("users").child(userId).child(StorageArea.CUPBOARD.toString()).removeValue();
//            dbRef.child("users").child(userId).child(StorageArea.FRIDGE.toString()).removeValue();
//            dbRef.child("users").child(userId).child(StorageArea.FREEZER.toString()).removeValue();
//        }

    }

    public static final String TAG = "FoodStorageData";
    private String userId;
    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    private HashMap<StorageArea,ArrayList<FoodItem>> kitchen = new HashMap<>(); // stored food data
    private HashMap<String,HasListView> listUpdate = new HashMap<>(); // list views that need updating.
}
