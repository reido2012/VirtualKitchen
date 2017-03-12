package com.example.oreid.virtualkitchen;

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

    private VirtualKitchenProfile profile;
    private static final VKData holder = new VKData(); // static singleton class needs this

    public void setProfile(VirtualKitchenProfile p) {
        this.profile = p;
    }

    public VirtualKitchenProfile getProfile() {
        return this.profile;
    }

    public FoodStorageData getFoodDB() {
        FoodStorageData db = this.getProfile().getFoodDB();
        if (db == null) { // create new one with user's UID
            db = new FoodStorageData(this.getProfile().getUid());
        }
        return db;
    }

    // return instance of VKData to work with.
    public static VKData getInstance() { return holder; }


}
