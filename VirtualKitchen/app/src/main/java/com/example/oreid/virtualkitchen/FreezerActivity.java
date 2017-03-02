package com.example.oreid.virtualkitchen;

/**
 * Created by Bdour on 18-Feb-17.
 */
import android.app.Activity;
import android.os.Bundle;
// same display but different content from the database
// items that are in the freezer

public class FreezerActivity extends Activity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
    }
}
