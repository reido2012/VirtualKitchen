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
    private FoodStorageData db;
    private static final VKData holder = new VKData(); // static singleton class needs this

    public void setProfile(VirtualKitchenProfile p) {
        this.profile = p;
        this.db = new FoodStorageData(profile.getUid());
    }

    public VirtualKitchenProfile getProfile() {
        return this.profile;
    }

    public FoodStorageData getFoodDB() {
        return db;
    }

    // return instance of VKData to work with.
    public static VKData getInstance() { return holder; }


}
