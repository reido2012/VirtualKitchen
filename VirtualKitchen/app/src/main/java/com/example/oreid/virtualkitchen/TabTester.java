package com.example.oreid.virtualkitchen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class TabTester extends AppCompatActivity {

    ListView listView;
    FoodItemAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_tester);

        ArrayList<FoodItem> testItems = new ArrayList<FoodItem>();
        testItems.add(new FoodItem("Cheese", 1, "Fridge", 14));
        testItems.add(new FoodItem("Egg", 6, "Fridge", 7));
        testItems.add(new FoodItem("Tomato", 7, "Fridge", 5));


        listView = (ListView)findViewById(R.id.list_view);
        listAdapter = new FoodItemAdapter(this,
                R.layout.food_list_item,
                testItems);
        listView.setAdapter(listAdapter);

    }
}
