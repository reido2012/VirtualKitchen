package com.example.oreid.virtualkitchen;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Food item adapter
 *
 * Created by hollie on 18/02/2017.
 *
 * Custom list adapter for displaying shopping lists
 *
 */

public class ShoppinglistAdapter extends ArrayAdapter {


    public ShoppinglistAdapter(Context context, int layoutResourceId, ArrayList<FoodItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ShoppingListHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ShoppingListHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.list_shoppingimage);
            holder.txtTitle = (TextView)row.findViewById(R.id.list_foodname);
            holder.btnAddKitchen = (ImageButton)row.findViewById(R.id.list_btn_addtokitchen);
            holder.btnDelete = (ImageButton)row.findViewById(R.id.list_btn_deleteshoppinglist);

            row.setTag(holder);
        }
        else
        {
            holder = (ShoppingListHolder)row.getTag();
        }

        setItemData(data.get(position), holder);

        return row;
    }

    private void setItemData(FoodItem d, ShoppingListHolder h) {
        String title = d.getName() + " x " + d.getQty(); // name + quantity in brakcets

        h.txtTitle.setText(title);
        h.imgIcon.setImageResource(d.getImage());
    }

    private void removeItemAtIndex(int i) {
        this.data.remove(i);
        this.notifyDataSetChanged();
    }

    Context context;
    int layoutResourceId;
    ArrayList<FoodItem> data = null;

    /* Provides a holder for items in the list
     * Supposed to improve performance
     * see: http://www.ezzylearning.com/tutorial/customizing-android-listview-items-with-custom-arrayadapter
     */
    static class ShoppingListHolder {
        ImageView imgIcon;
        TextView txtTitle;
        ImageButton btnAddKitchen;
        ImageButton btnDelete;
    }

}
