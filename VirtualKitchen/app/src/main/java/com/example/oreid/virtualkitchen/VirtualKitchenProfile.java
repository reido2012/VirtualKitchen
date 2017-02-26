package com.example.oreid.virtualkitchen;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;


public class VirtualKitchenProfile implements Parcelable {

    private String email;
    private String firstName;
    private String lastName;
    private String uID;
    private FoodStorageData foodDB; // TODO 25/02/16 (Hollie) - please could someone integrate this into our firebase, or if it's wrong tell me :)

    public VirtualKitchenProfile(FirebaseUser acct, String fname, String lname) {
        this.email = acct.getEmail();
        this.firstName = fname;
        this.lastName = lname;
        this.uID = acct.getUid();
    }

    public VirtualKitchenProfile(GoogleSignInAccount acct) {
        this.email = acct.getEmail();
        this.firstName = acct.getGivenName();
        this.lastName = acct.getFamilyName();
        this.uID = acct.getId();
    }

    protected VirtualKitchenProfile(Parcel in) {
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUid() {
        return uID;
    }

    public FoodStorageData getFoodDB() {
        return foodDB;
    }

    public void setFoodDB(FoodStorageData fsd) {
        this.foodDB = fsd;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VirtualKitchenProfile> CREATOR = new Creator<VirtualKitchenProfile>() {
        @Override
        public VirtualKitchenProfile createFromParcel(Parcel in) {
            return new VirtualKitchenProfile(in);
        }

        @Override
        public VirtualKitchenProfile[] newArray(int size) {
            return new VirtualKitchenProfile[size];
        }
    };
}
