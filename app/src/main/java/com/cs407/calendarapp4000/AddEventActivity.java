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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventActivity extends AppCompatActivity {

    Bundle extras;
    Calendar cal;
    Calendar currentCal;
    TextView dateTextView;
    TimePicker timePicker;
    EditText eventTitle;
    EditText eventDescription;

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

                // Assemble Event
                eventTitle = (EditText) findViewById(R.id.event_title_edittext);
                eventDescription = (EditText) findViewById(R.id.event_description_edittext);

                Event event = new Event();
                event.setDescription(eventDescription.getText().toString());
                event.setTitle(eventTitle.getText().toString());
                event.setLongDate(longDateFormat.format(cal.getTime()));
                event.setShortDate(shortDateFormat.format(cal.getTime()));

                postEventRetro(event);

                Intent intent = new Intent(this, com.cs407.calendarapp4000.MainActivity.class);
                startActivity(intent);

            default:
                break;
        }
        return true;
    }

    private void postEventRetro(final Event event) {

        EventClient client = ServiceGenerator.createService(EventClient.class);
        Call<ResponseBody> call = client.postEvent(event);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccess()) {
                    Log.d("SUCCESS", response.raw().toString());

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
