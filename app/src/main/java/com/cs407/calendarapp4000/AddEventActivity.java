package com.cs407.calendarapp4000;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    Bundle extras;
    Calendar cal;
    TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // retrieve calendar from bundle
        extras = getIntent().getExtras();
        cal = (Calendar) extras.get("EXTRA_CALENDAR");

        // set up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_addevent);

        // format date and display in text view
        dateTextView = (TextView) findViewById(R.id.date_text_view);
        SimpleDateFormat f = new SimpleDateFormat("EEE MMM d yyyy");
        dateTextView.setText(f.format(cal.getTime()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_addevent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // add Event
            case R.id.action_done:

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                Toast.makeText(this, dateFormat.format(cal.getTime()), Toast.LENGTH_LONG).show();

            default:
                break;
        }

        return true;
    }
}
