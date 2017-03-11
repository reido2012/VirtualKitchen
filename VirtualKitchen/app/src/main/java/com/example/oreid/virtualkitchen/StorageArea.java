package com.example.oreid.virtualkitchen;

/**
 * Very Simple Storage Area Enum
 *
 * Created by hollie on 25/02/2017.
 */

public enum StorageArea {
    FRIDGE("Fridge"),
    FREEZER("Freezer"),
    CUPBOARD("Pantry"),
    SHOPPINGLIST("Shopping List");

    private final String storageName;

    StorageArea(String name) {
        storageName = name;
    }

    public String toString() {
        return storageName;
    }

}
