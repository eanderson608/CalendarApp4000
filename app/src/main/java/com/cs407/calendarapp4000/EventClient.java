package com.cs407.calendarapp4000;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by patron on 3/5/16.
 */
public interface EventClient {
    @GET("/api/events")
    Call<ArrayList<Event>> getEvents();
}
