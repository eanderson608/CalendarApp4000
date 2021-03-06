package com.cs407.calendarapp4000;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by patron on 3/5/16.
 */
public interface EventClient {
    @GET("/api/events/{shortDate}")
    Call<ArrayList<Event>> getEvents(@Path("shortDate") String shortDate);

    @DELETE("/api/events/{id}")
    Call<ResponseBody> deleteEvent(@Path("id") String id);

    @POST("/api/events/")
    Call<ResponseBody> postEvent(@Body Event event);
}
