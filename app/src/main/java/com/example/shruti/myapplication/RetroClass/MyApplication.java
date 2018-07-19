package com.example.shruti.myapplication.RetroClass;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.util.Log;
import com.example.shruti.myapplication.Model.NearByApiResponse;
import com.example.shruti.myapplication.WebService.NearByApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application
{
    MutableLiveData<List<NearByApiResponse>> liveData;
    public static String PLACE_API_BASE_URL = "https://maps.googleapis.com/maps/";
    public static NearByApi nearByApi;
    static MyApplication app;
    private int PROXIMITY_RADIUS = 8000;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public NearByApi getApiService() {
        if (nearByApi == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true).readTimeout(80, TimeUnit.SECONDS).connectTimeout(80, TimeUnit.SECONDS).addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(PLACE_API_BASE_URL).addConverterFactory(getApiConvertorFactory()).client(client).build();

            nearByApi = retrofit.create(NearByApi.class);
            return nearByApi;
        } else {
            return nearByApi;
        }
    }

    /***************** Live Data Code *********************/

    public  LiveData<NearByApiResponse> getData(String placeType, String str , int radius)
    {
        final MutableLiveData<NearByApiResponse> nearByApiResponsePOJOMutableLiveData = new MutableLiveData<>();
        NearByApi nearByApi = app.getApiService();
        //Call<NearByApiResponse> call= getApiService().getNearbyPlaces(placeType, str, PROXIMITY_RADIUS);
        nearByApi.getNearbyPlaces(placeType, str, radius).enqueue(new Callback<NearByApiResponse>() {
            @Override
            public void onResponse(Call<NearByApiResponse> call, Response<NearByApiResponse> response) {
                try{
                    NearByApiResponse nearByApiResponse = response.body();
                nearByApiResponsePOJOMutableLiveData.postValue(nearByApiResponse);

            } catch (Exception e) {
                Log.d("onResponse", "There is an error");
                e.printStackTrace();
            }
        }

            @Override
            public void onFailure(Call<NearByApiResponse> call, Throwable t) {

                Log.d("onFailure", t.toString());
                t.printStackTrace();
                PROXIMITY_RADIUS += 10000;

            }
        });

        return nearByApiResponsePOJOMutableLiveData;

    }

    private static GsonConverterFactory getApiConvertorFactory() {
        return GsonConverterFactory.create();
    }

    public static MyApplication getApp() {
        return app;
    }
    
}
