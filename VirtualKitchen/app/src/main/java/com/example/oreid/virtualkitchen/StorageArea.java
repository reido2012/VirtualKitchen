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
    RECIPELIST("Recipe List"),
    SHOPPINGLIST("Shopping List");


    private final String storageName;

    StorageArea(String name) {
        storageName = name;
    }

    public String toString() {
        return storageName;
    }

    public static StorageArea fromString(String val) {
        switch(val) {
            case "Fridge":
                return FRIDGE;
            case "Freezer":
                return FREEZER;
            case "Recipe List":
                return RECIPELIST;
            case "Pantry":
                return CUPBOARD;
            case "Shopping List":
                return SHOPPINGLIST;
            default:
                return null;
        }
    }

    public static String[] stringValues() {
        StorageArea[] states = values();
        String[] names = new String[states.length];

        for (int i = 0; i < states.length; i++) {
            names[i] = states[i].toString();
        }

        return names;
    }

}
