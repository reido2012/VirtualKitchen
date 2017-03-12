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

    public VirtualKitchenProfile(){
        //Default constructor needed for snapshot
    }


    protected VirtualKitchenProfile(Parcel in) {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fname) {
        this.firstName = fname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lname) {
        this.lastName = lname;
    }

    public String getUid() {
        return uID;
    }

    public void setUid(String uid) {
        this.uID = uid;
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
