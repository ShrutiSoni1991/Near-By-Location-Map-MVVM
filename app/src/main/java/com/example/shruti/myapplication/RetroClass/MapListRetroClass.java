package com.example.shruti.myapplication.RetroClass;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.Toast;

import com.example.shruti.myapplication.Model.NearByPlacePOJO;
import com.example.shruti.myapplication.View.Activity.MainActivity;
import com.example.shruti.myapplication.WebService.NearByRESTrequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapListRetroClass {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static NearByRESTrequest getNearByRequest(){
        return getRetrofitInstance().create(NearByRESTrequest.class);
    }
    public LiveData<List<NearByPlacePOJO>> getData() {
        final MutableLiveData<List<NearByPlacePOJO>> nearByPlacePOJOMutableLiveData = new MutableLiveData<>();

        NearByRESTrequest requestNearBy = MapListRetroClass.getNearByRequest();
        requestNearBy.getAllPhotos().enqueue(new Callback<List<NearByPlacePOJO>>() {
            @Override
            public void onResponse(Call<List<NearByPlacePOJO>> call, Response<List<NearByPlacePOJO>> response) {
                nearByPlacePOJOMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<NearByPlacePOJO>> call, Throwable t) {

                Log.e("Failure..!!!!","Network Call Failed..!!!!");
                //Toast.makeText(MapListRetroClass.this, "Failure", Toast.LENGTH_SHORT).show();

            }
        });
    return  nearByPlacePOJOMutableLiveData;
    }

}
