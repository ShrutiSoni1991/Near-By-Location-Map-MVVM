package com.example.shruti.myapplication.AndroidViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.shruti.myapplication.Model.NearByApiResponse;
import com.example.shruti.myapplication.Model.NearByPlacePOJO;
import com.example.shruti.myapplication.RetroClass.MapListRetroClass;
import com.example.shruti.myapplication.RetroClass.MyApplication;
import com.example.shruti.myapplication.View.Activity.MapFragment;

import java.util.List;

public class SearchMapViewModel extends AndroidViewModel {

    private NearByApiResponse nearByApiResponse;
    private MyApplication myApplication;
    private LiveData<NearByApiResponse> liveData;
    public LiveData<NearByApiResponse> getNearByPlaces()
    {
        return liveData;
    }

    public SearchMapViewModel(@NonNull Application application) {
        super(application);
        myApplication = new MyApplication();
    }
    public void SetMapData(String s , String a, int b){
       liveData = myApplication.getData(s,a,b);
    }


}

