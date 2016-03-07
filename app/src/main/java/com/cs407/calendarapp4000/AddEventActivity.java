package com.cs407.calendarapp4000;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {

    Bundle extras;
    Calendar cal;
    Calendar currentCal;
    TextView dateTextView;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        timePicker = (TimePicker) findViewById(R.id.time_picker);

        // retrieve calendar from bundle
        extras = getIntent().getExtras();
        cal = (Calendar) extras.get("EXTRA_CALENDAR");
        currentCal = Calendar.getInstance();

        // set up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_addevent);

        // format date and display in text view
        dateTextView = (TextView) findViewById(R.id.date_text_view);
        SimpleDateFormat f = new SimpleDateFormat("EEE MMM d yyyy");
        dateTextView.setText(f.format(cal.getTime()));

        // Set timepicker to current time
        // Handle deprecated methods below API 23
        if (Build.VERSION.SDK_INT >= 23 ) {
            timePicker.setHour(currentCal.HOUR_OF_DAY);
            timePicker.setMinute(currentCal.MINUTE);
        }
        else {
            timePicker.setCurrentHour(currentCal.HOUR_OF_DAY);
            timePicker.setCurrentMinute(currentCal.MINUTE);
        }


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

                // Handle deprecated methods below API 23
                if (Build.VERSION.SDK_INT >= 23 ) {
                    cal.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    cal.set(Calendar.MINUTE, timePicker.getMinute());
                }
                else {
                    cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                    cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                }

                SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
                SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US);
                Log.d("longDate", longDateFormat.format(cal.getTime()));

            default:
                break;
        }
        return true;
    }


}
