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
 * Custom list adapter for displaying food items
 *
 */

public class FoodItemAdapter extends ArrayAdapter {

    private static final String TAG = "FoodItemAdapter";

    public FoodItemAdapter(Context context, int layoutResourceId, ArrayList<FoodItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FoodItemHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new FoodItemHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.list_image);
            holder.txtTitle = (TextView)row.findViewById(R.id.list_title);
            holder.txtSubtitle = (TextView)row.findViewById(R.id.list_subtitle);
            holder.btnShoppingList = (ImageButton)row.findViewById(R.id.list_btn_shoppinglist);
            holder.btnDelete = (ImageButton)row.findViewById(R.id.list_btn_deletefood);

            row.setTag(holder);
        }
        else
        {
            holder = (FoodItemHolder)row.getTag();
        }

        setItemData(data.get(position), holder);

        return row;
    }

    private void setItemData(FoodItem d, FoodItemHolder h) {
        String title = d.getName() + " x " + d.getQty(); // name + quantity in brakcets
        String subtitle = "Expires in " + d.getDaysLeft() + " days."; // number of days remaining
        // TODO 18/02/17 set image

        h.txtTitle.setText(title);
        h.txtSubtitle.setText(subtitle);
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
    static class FoodItemHolder {
        ImageView imgIcon;
        TextView txtTitle;
        TextView txtSubtitle;
        ImageButton btnShoppingList;
        ImageButton btnDelete;
    }

}
