package com.example.shruti.myapplication.AndroidViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.shruti.myapplication.Model.NearByPlacePOJO;
import com.example.shruti.myapplication.RetroClass.MapListRetroClass;
import com.example.shruti.myapplication.WebService.NearByRESTrequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RecyclerDataViewModel extends AndroidViewModel {

    private NearByPlacePOJO nearby;
    private MapListRetroClass retroClass = new MapListRetroClass();
    private LiveData<List<NearByPlacePOJO>> liveData;

    public RecyclerDataViewModel(@NonNull Application application) {
        super(application);
         liveData = retroClass.getData();
    }

    public LiveData<List<NearByPlacePOJO>> getNearByPlaces()
    {
        return liveData;
    }
}
