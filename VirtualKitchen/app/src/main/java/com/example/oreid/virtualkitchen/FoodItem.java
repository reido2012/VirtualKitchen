package com.example.oreid.virtualkitchen;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * Food Item
 *
 * Represents an item of food to be stored within a storage aea.
 *
 * Created by hollie on 17/02/2017.
 *
 */

public class FoodItem implements Serializable {

    public FoodItem(String name, int qty, String storedWhere, int shelfLife) {
        this.name = name;
        this.qty = qty;
        this.storedWhere = storedWhere;
        this.shelfLife = shelfLife;
        this.dateAdded = new Date(); // now
    }

    public int getQty() {
        return qty;
    }

    public String getName() {
        return name;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    // Calculate Expiry Date
    public Date getExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateAdded);
        cal.add(Calendar.DATE, shelfLife);
        return cal.getTime();
    }

    public int getDaysLeft() {
        return daysBetween(dateAdded, getExpiryDate());
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public String getLocation() {
        return storedWhere;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQty(int qty) {
        if (qty <= 0) {
            return; // invalid
        }

        this.qty = qty;
    }

    public void setLocation(String storedWhere) {
        this.storedWhere = storedWhere;
    }

    public void setShelfLife(int shelfLife) {
        if (shelfLife <= 0) {
            return; // invalid
        }
        this.shelfLife = shelfLife;
    }

    // From stackoverflow: http://stackoverflow.com/questions/7103064/java-calculate-the-number-of-days-between-two-dates
    private int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private String name;
    private int qty;
    private Date dateAdded;
    private int shelfLife;
    private String storedWhere;
    // TODO 17/02/17 add photo

}
