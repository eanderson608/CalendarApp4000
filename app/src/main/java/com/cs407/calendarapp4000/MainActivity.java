package com.cs407.calendarapp4000;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Event> eventArrayList;
    private CustomAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize fields
        context = this;
        listView = (ListView) findViewById(R.id.list_view);
        eventArrayList = new ArrayList<>();
        adapter = new CustomAdapter(this, eventArrayList);

        //set adapter
        listView.setAdapter(adapter);
    }
}
