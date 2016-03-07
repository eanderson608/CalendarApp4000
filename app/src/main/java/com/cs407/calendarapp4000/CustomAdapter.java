package com.cs407.calendarapp4000;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patron on 3/5/16.
 */
public class CustomAdapter extends BaseAdapter {
    private ArrayList<Event> events;
    private Context context;
    private String formattedTime;
    private Calendar cal;

    // A cache for looking up Views
    private static class ViewHolder {
        TextView title;
        TextView description;
        TextView time;
        ImageButton delete;
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
            viewHolder.time = (TextView) convertView.findViewById(R.id.time_text_view);
            viewHolder.delete = (ImageButton) convertView.findViewById(R.id.delete_button);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        final Event event = getItem(position);

        // Format time from longDate
        cal = Calendar.getInstance();
        SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US);

        try {
            cal.setTime(longDateFormat.parse(event.getLongDate()));
            Log.d("LONG_DATE", event.getLongDate());
        } catch (ParseException e) {
            Log.d("PARSE EXCEPTION", event.getLongDate());
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);

        // Populate the data into the template view using the data object
        viewHolder.time.setText(timeFormat.format(cal.getTime()));
        viewHolder.title.setText(event.getTitle());
        viewHolder.description.setText(event.getDescription());

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CUSTOM_ADAPTER", "Delete button was pressed");
                deleteEventRetro(event);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    private void deleteEventRetro(final Event event) {

        EventClient client = ServiceGenerator.createService(EventClient.class);

        Call<ResponseBody> call = client.deleteEvent(event.get_id());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccess()) {
                    Log.d("SUCCESS", response.raw().toString());
                    MainActivity.getEventsRetro(event.getShortDate());
                } else {
                    // error response, no access to resource?
                    Log.d("ERROR", response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }
}
