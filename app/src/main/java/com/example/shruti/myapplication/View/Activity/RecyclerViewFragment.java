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
import android.view.LayoutInflater;
import android.view.View;

import com.example.shruti.myapplication.AndroidViewModel.RecyclerDataViewModel;
import com.example.shruti.myapplication.Model.NearByApiResponse;
import com.example.shruti.myapplication.Model.NearByPlacePOJO;
import com.example.shruti.myapplication.R;
import com.example.shruti.myapplication.RetroClass.MapListRetroClass;
import com.example.shruti.myapplication.View.Adapter.MyAdapter;
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

public class RecyclerViewFragment extends Fragment {
    ProgressDialog progressDoalog;
    RecyclerView recyclerView;
    RecyclerDataViewModel recyclerDataViewModel;
    MyAdapter adapter;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
    View v;
    CircleImageView circleImageView;
    NearByApiResponse nearByApiResponse;

    public static RecyclerViewFragment newInstance(String text) {

        RecyclerViewFragment f = new RecyclerViewFragment();

        Bundle b = new Bundle();


        b.putString("text", text);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.recyclerview_fragment, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);

        recyclerDataViewModel = ViewModelProviders.of(this).get(RecyclerDataViewModel.class);


        circleImageView = (CircleImageView)v.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "active", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                MapFragment mapFragment = new MapFragment();
                transaction.replace(R.id.rlMain, mapFragment);
                transaction.commit();
                }
        });

        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

        /*Create handle for the RetrofitInstance interface*/
        /*NearByRESTrequest service = MapListRetroClass.getRetrofitInstance().create(NearByRESTrequest.class);
        Call<List<NearByPlacePOJO>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<NearByPlacePOJO>>() {
            @Override
            public void onResponse(Call<List<NearByPlacePOJO>> call, Response<List<NearByPlacePOJO>> response) {
                progressDoalog.dismiss();
                recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
                adapter = new MyAdapter(getContext(), response.body());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<NearByPlacePOJO>> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });*/

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressDoalog.dismiss();
        super.onViewCreated(view, savedInstanceState);

        recyclerDataViewModel.getNearByPlaces().observe(this, new Observer<List<NearByPlacePOJO>>() {
            @Override
            public void onChanged(@Nullable List<NearByPlacePOJO> nearByPlacePOJOS) {
                adapter = new MyAdapter(getContext(),nearByPlacePOJOS);
                //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.setAdapter(adapter);
            }
        });
    }
}

//    /*Method to generate List of data using RecyclerView with custom adapter*/
//    private void generateDataList(List<NearByPlacePOJO> photoList) {
//        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
//        adapter = new MyAdapter(getContext(),photoList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//    }
        //        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
//
//        //        adapter = new MyAdapter();
////
////        DividerDecoration dividerDecoration = new DividerDecoration(getContext());
////        recyclerView.setLayoutManager(layoutManager);
////        recyclerView.addItemDecoration(dividerDecoration);
////        recyclerView.setAdapter(adapter);
////        return v;
////    }
////}
