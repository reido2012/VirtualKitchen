package com.example.oreid.virtualkitchen;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
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

    public FoodItem(String name, int i, StorageArea storageArea, int parseInt) {}

    public FoodItem(String name, int qty, StorageArea storedWhere, int shelfLife, String cat) {
        this.name = name;
        this.qty = qty;
        this.storedWhere = storedWhere;
        this.shelfLife = shelfLife;
        this.dateAdded = new Date(); // now
        this.setCategory(cat);

    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
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

    public StorageArea getLocation() {
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

    public void setLocation(StorageArea storedWhere) {
        this.storedWhere = storedWhere;
    }

    public void setShelfLife(int shelfLife) {
        if (shelfLife <= 0) {
            return; // invalid
        }
        this.shelfLife = shelfLife;
    }

    public boolean getFavourite() {
        return this.favourite;
    }

    public void setFavourite(boolean fav) {
        this.favourite = fav;
    }

    // set category and also image based on category
    public void setCategory(String cat) {
        this.category = cat;
//        this.img = findImgByCat(cat);
        Log.d("FoodItem", this.category + ": " + this.img);
    }

    public String getCategory() {
        return this.category;
    }

    public int getImage() {
        return this.img;
    }

    public void setImage(int img) {
        this.img = img;
    }

    private void findImgByCat(String cat) {
//        switch(cat) {
//            case "Fruit":
//                return IMG_FRUIT;
//            case "Vegetables":
//                return IMG_VEGETABLES;
//            case "Protein":
//                return IMG_PROTEIN;
//            case "Dairy":
//                return IMG_DAIRY;
//            case "Grains":
//                return IMG_GRAINS;
//            case "Other":
//                return IMG_OTHER;
//            default:
//                return IMG_DEFAULT;
//        }
    }

    /**
     * Comparator, used in sorting, that allows items to be sorted by name.
     * Compares item names lexicographically to sort in alphabetical order.
     */
    public static final Comparator nameComparator = new Comparator<FoodItem>() {
        @Override
        public int compare(FoodItem f1, FoodItem f2) {
            return f1.getName().compareTo(f2.getName());
        }
    };

    /**
     * Comparator, used in sorting, that allows items to be sorted by number of days until expiry.
     * Compares days left so items are sorted in ascending order of days until expiry.
     */
    public static final Comparator expiryDateComparator = new Comparator<FoodItem>() {
        @Override
        public int compare(FoodItem f1, FoodItem f2) {
            return f1.getDaysLeft() - f2.getDaysLeft();
        }
    };

    // From stackoverflow: http://stackoverflow.com/questions/7103064/java-calculate-the-number-of-days-between-two-dates
    private int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private String name;
    private int qty;
    private Date dateAdded;
    private int shelfLife;
    private StorageArea storedWhere;
    private String category;
    private Boolean favourite = false;
    private int img;

//    public static final int IMG_DEFAULT = R.drawable.img_default;
//    public static final int IMG_OTHER = R.drawable.img_other;
//    public static final int IMG_FRUIT = R.drawable.img_fruit;
//    public static final int IMG_VEGETABLES = R.drawable.img_veg;
//    public static final int IMG_DAIRY = R.drawable.img_dairy;
//    public static final int IMG_PROTEIN = R.drawable.img_protein;
//    public static final int IMG_GRAINS = R.drawable.img_grain;






}
