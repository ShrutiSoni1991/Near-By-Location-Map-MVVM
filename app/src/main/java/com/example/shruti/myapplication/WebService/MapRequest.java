package com.example.shruti.myapplication.WebService;

import com.example.shruti.myapplication.Model.MapData;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MapRequest {

    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDh1uMLQVuy6inwVBqM6R3xIojj28r_5fE")
    Call<MapData> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);

}