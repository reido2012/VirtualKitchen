package com.example.oreid.virtualkitchen;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Notification adapter
 *
 * Created by hollie on 18/02/2017.
 *
 * Custom list adapter for displaying notifications
 *
 */

public class NotificationAdapter extends ArrayAdapter {

    private static final String TAG = "NotificationAdapter";

    public NotificationAdapter(Context context, int layoutResourceId, ArrayList<Notification> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        NotificationHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new NotificationHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.notification_list_icon);
            holder.txtTitle = (TextView)row.findViewById(R.id.notification_list_text);

            row.setTag(holder);
        }
        else
        {
            holder = (NotificationHolder)row.getTag();
        }

        setItemData(data.get(position), holder);

        return row;
    }

    private void setItemData(Notification d, NotificationHolder h) {

        String title = d.listMessage();

        Log.d(TAG, "adding " + title);

        h.txtTitle.setText(title);
        h.imgIcon.setImageResource(d.food.getImage());
    }


    Context context;
    int layoutResourceId;
    ArrayList<Notification> data = null;

    /* Provides a holder for items in the list
     * Supposed to improve performance
     * see: http://www.ezzylearning.com/tutorial/customizing-android-listview-items-with-custom-arrayadapter
     */
    static class NotificationHolder {
        ImageView imgIcon;
        TextView txtTitle;
    }

}
