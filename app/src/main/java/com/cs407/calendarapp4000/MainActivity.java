package com.cs407.calendarapp4000;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private static CustomAdapter adapter;
    private CalendarView calendarView;
    private String selectedDate;
    private Calendar cal;
    private static ArrayList<Event> eventArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize fields
        listView = (ListView) findViewById(R.id.list_view);
        calendarView = (CalendarView) findViewById(R.id.calendar_view);
        eventArrayList = new ArrayList<>();
        adapter = new CustomAdapter(this, eventArrayList);
        final SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
        cal = Calendar.getInstance();


        //set adapter
        listView.setAdapter(adapter);

        //get selected date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedDate = shortDateFormat.format(cal.getTime());
                getEventsRetro(selectedDate);
            }
        });

        // get events for today
        getEventsRetro(shortDateFormat.format(cal.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Open AddEventActivity
            case R.id.action_add:
                Intent intent = new Intent(this, com.cs407.calendarapp4000.AddEventActivity.class);
                intent.putExtra("EXTRA_CALENDAR", cal);
                startActivity(intent);

            default:
                break;
        }
        return true;
    }



    static public void getEventsRetro(String date) {

        eventArrayList.clear();
        EventClient client = ServiceGenerator.createService(EventClient.class);
        Call<ArrayList<Event>> call = client.getEvents(date);

        call.enqueue(new Callback<ArrayList<Event>>() {

            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {

                if (response.isSuccess()) {
                    Log.d("HTTP_GET_RESPONSE", response.raw().toString());

                    Gson gson = new GsonBuilder().create();

                    for (Event e : response.body()) {
                        Log.d("EVENTS", e.toString());
                        eventArrayList.add(gson.fromJson(e.toString(), Event.class));
                    }
                    adapter.notifyDataSetChanged();

                } else {
                    // error response, no access to resource?
                    Log.d("HTTP_GET_RESPONSE", response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
            }
        });
    }
}
