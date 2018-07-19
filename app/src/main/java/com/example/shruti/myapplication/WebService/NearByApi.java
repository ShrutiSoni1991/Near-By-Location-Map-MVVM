package com.example.shruti.myapplication.WebService;


import com.example.shruti.myapplication.Model.NearByApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface NearByApi {
    
    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDmCD05GK9Ls1lsn0IDmk7X1W4Z7SdC2Bc")
    Call<NearByApiResponse> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);
}
