package com.cs407.calendarapp4000;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by patron on 3/5/16.
 */
public interface EventClient {
    @GET("/api/events/{shortDate}")
    Call<ArrayList<Event>> getEvents(@Path("shortDate") String shortDate);

}
