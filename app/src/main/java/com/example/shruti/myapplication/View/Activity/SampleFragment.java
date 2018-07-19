package com.example.shruti.myapplication.View.Activity;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.shruti.myapplication.AndroidViewModel.RecyclerDataViewModel;
import com.example.shruti.myapplication.Model.NearByApiResponse;
import com.example.shruti.myapplication.Model.NearByPlacePOJO;
import com.example.shruti.myapplication.R;
import com.example.shruti.myapplication.RetroClass.MapListRetroClass;
import com.example.shruti.myapplication.View.Adapter.MyAdapter;
import com.example.shruti.myapplication.View.Adapter.Sample_Adapter;
import com.example.shruti.myapplication.View.Adapter.Sample_Adapter2;
import com.example.shruti.myapplication.WebService.NearByRESTrequest;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SampleFragment extends Fragment {
    ProgressDialog progressDoalog;
    RecyclerView recyclerView;
    Sample_Adapter2 sample_adapter;
    View v;
    CircleImageView circleImageView;
    NearByApiResponse nearByApiResponse;

    public static SampleFragment newInstance(String text) {

        SampleFragment f = new SampleFragment();
        Bundle b = new Bundle();
        b.putString("text", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.sample_recycler2, container, false);

        nearByApiResponse =  (NearByApiResponse)getArguments().getParcelable("Recycler");
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        circleImageView = (CircleImageView)v.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "active", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                MapFragment mapFragment = new MapFragment();
                String backStateName = mapFragment.getClass().getName();
                transaction.replace(R.id.rlMain, mapFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack("Recycler");
                transaction.commit();
            }
        });

        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        return v;
    }
    String sMyText = "some text";
    int nMyInt = 10;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.getParcelable("Recycler");
        Toast.makeText(getContext(), "onSaveInstanceState()", Toast.LENGTH_LONG).show();
        Log.i("onSaveInstanceState", "onSaveInstanceState()");
    }
    String sNewMyText = "";
    int nNewMyInt = 0;
    public void onRestoreInstanceState(Bundle inState){
        inState.putParcelable("Recycler", nearByApiResponse);
        Toast.makeText(getContext(), "onRestoreInstanceState()", Toast.LENGTH_LONG).show();
        Log.i("onRestoreInstanceState", "onRestoreInstanceState()");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressDoalog.dismiss();
        super.onViewCreated(view, savedInstanceState);

        sample_adapter = new Sample_Adapter2(getContext(),nearByApiResponse);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(sample_adapter);
    }
}
