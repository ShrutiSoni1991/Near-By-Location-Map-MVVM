package com.example.shruti.myapplication.WebService;
import com.example.shruti.myapplication.Model.NearByPlacePOJO;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NearByRESTrequest {

    @GET("/photos")
    Call<List<NearByPlacePOJO>> getAllPhotos();
}
