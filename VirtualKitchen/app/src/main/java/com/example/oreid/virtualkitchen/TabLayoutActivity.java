package com.example.oreid.virtualkitchen;

import android.app.Activity;

/**
 * Created by Bdour on 18-Feb-17.
 */
import android.os.Bundle;

// main tab activity call others
public class TabLayoutActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
    }
}