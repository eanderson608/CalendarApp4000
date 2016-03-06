package com.cs407.calendarapp4000;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Event> eventArrayList;
    private CustomAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize fields
        context = this;
        listView = (ListView) findViewById(R.id.list_view);
        eventArrayList = new ArrayList<>();
        adapter = new CustomAdapter(this, eventArrayList);

        //set adapter
        listView.setAdapter(adapter);

        getEventsRetro();
    }


    private void getEventsRetro() {

        // Create REST adapter which points to the Event API endpoint
        EventClient client = ServiceGenerator.createService(EventClient.class);

        // Fetch a list of Events
        Call<ArrayList<Event>> call = client.getEvents();

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
                    Toast.makeText(getApplicationContext(), "ERROR: " + response.raw().toString() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "TRAGIC FAILURE!!!!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
