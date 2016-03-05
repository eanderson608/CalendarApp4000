package com.cs407.calendarapp4000;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by patron on 3/5/16.
 */
public class CustomAdapter extends BaseAdapter {
    private ArrayList<Event> events;
    private Context context;

    // A cache for looking up Views
    private static class ViewHolder {
        TextView title;
        TextView description;
        TextView date;
        Button delete;
    }

    public CustomAdapter(Context context, ArrayList<Event> events) {
        this.events = events;
        this.context = context;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Event getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);

            viewHolder.title = (TextView) convertView.findViewById(R.id.title_text_view);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description_text_view);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date_text_view);
            viewHolder.delete = (Button) convertView.findViewById(R.id.delete_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        final Event game = getItem(position);

        // Populate the data into the template view using the data object
        viewHolder.title.setText("Event Title");
        viewHolder.description.setText("a short description of the event");
        viewHolder.date.setText("event date");
        viewHolder.delete.setText("Delete");

        //TODO implement onclick for delete (do this after implementing retrofit)
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CUSTOM_ADAPTER", "Delete button was pressed");
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}