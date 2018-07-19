package com.example.shruti.myapplication.View.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.shruti.myapplication.AndroidViewModel.RecyclerDataViewModel;
import com.example.shruti.myapplication.AndroidViewModel.SearchMapViewModel;
import com.example.shruti.myapplication.Model.NearByPlacePOJO;
import com.example.shruti.myapplication.R;
import com.example.shruti.myapplication.View.Adapter.MyAdapter2;
import com.example.shruti.myapplication.View.Adapter.Sample_Adapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.shruti.myapplication.RetroClass.MyApplication;
import com.example.shruti.myapplication.Model.NearByApiResponse;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;


import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener{


    private GoogleMap googleMap;
    ClusterManager<NearByApiResponse> mClusterManagerNear;
    ClusterManager<com.example.shruti.myapplication.Model.Location> mClusterManager;
    private GoogleApiClient mGoogleApiClient;
    private Button btnRestorentFind,btnHospitalFind;
    private LocationRequest mLocationRequest;
    private Location location;
    LatLng latLng;
    final private int PROXIMITY_RADIUS = 8000;
    String sNewMyText = "";
    int nNewMyInt = 0;
    View view;
    MarkerOptions markerOptions;
    Context context;
    RecyclerView recyclerView, sample_recycler;
    RecyclerDataViewModel recyclerDataViewModel;
    MyAdapter2 adapter2;
    String sMyText = "my fragment";
    int nMyInt = 10;
    Sample_Adapter sample_adapter;
    String searchInput = new String();
    Double mLongitude;
    Double mLatitude;
    SearchMapViewModel searchMapViewModel;
    FloatingActionButton floatingActionButton;
    Bundle bundle;
    String str;
    Toolbar mToolbar;
    SampleFragment sampleFragment;

    Observer<NearByApiResponse> nearByApiResponses;
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.map_fragment, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.wallet);
        recyclerDataViewModel = ViewModelProviders.of(this).get(RecyclerDataViewModel.class);
        searchMapViewModel = ViewModelProviders.of(this).get(SearchMapViewModel.class);

         mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(mToolbar);

        ((MainActivity)getActivity()).getSupportActionBar().setIcon(R.mipmap.icon);


        floatingActionButton = (FloatingActionButton)view. findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "active", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                sampleFragment = new SampleFragment();
                bundle  = new Bundle();
                searchMapViewModel.getNearByPlaces().observe(getActivity(), new Observer<NearByApiResponse>() {
                    @Override
                    public void onChanged(@Nullable NearByApiResponse nearByApiResponses) {

                        if(nearByApiResponses != null){
                            bundle.putParcelable("Recycler", nearByApiResponses);
                            sampleFragment.setArguments(bundle);
                        }
                    }
                });

                transaction.replace(R.id.rlMain, sampleFragment).addToBackStack("Here");;
                transaction.commit();
            }
        });

        //To check permissions above M as below it making issue and gives permission denied on samsung and other phones.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        context = getContext();

        //To check google play service available
        if(!isGooglePlayServicesAvailable()){
            Toast.makeText(getContext(),"Google Play Services not available.",Toast.LENGTH_LONG).show();
            getActivity().finish();
        }else{
            // when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.frag_map);
            mapFragment.getMapAsync(this);
        }

        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("my_text", sMyText);
        outState.putInt("my_int", nMyInt);
        Toast.makeText(getContext(), "onSaveInstanceState()", Toast.LENGTH_LONG).show();
    }
    public void onRestoreInstanceState(Bundle inState){
        // restore saved values
        sNewMyText = inState.getString("my_text");
        nNewMyInt = inState.getInt("my_int");
        Toast.makeText(getContext(), "onRestoreInstanceState()", Toast.LENGTH_LONG).show();
        Log.i("onRestoreInstanceState", "onRestoreInstanceState()");
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getContext());
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result, 0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        recyclerDataViewModel.getNearByPlaces().observe(this, new Observer<List<NearByPlacePOJO>>() {
//            @Override
//            public void onChanged(@Nullable List<NearByPlacePOJO> nearByPlacePOJOS) {
//                adapter2 = new MyAdapter2(getContext(),nearByPlacePOJOS);
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
//                recyclerView.setLayoutManager(layoutManager);
//                recyclerView.setAdapter(adapter2);
//            }
//        });

    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    /**********************Search Box Code*****************************************/
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.option_menu, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        final SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                searchInput = sv.getQuery().toString();
                findPlaces(searchInput, str, PROXIMITY_RADIUS);
                Log.e("####",searchInput);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return false;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Make sure you have this line of code.
    }
    /****************** search Box code end *********************************/

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(),"Could not connect google api",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location!=null){
            //this.location = location;
            mLongitude = location.getLongitude();
            mLatitude = location.getLatitude();
            str = String.valueOf(mLatitude) + "," + String.valueOf(mLongitude);
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        setupMap();


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            googleMap.setMyLocationEnabled(true);
        }

     }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    private void setupMap() {
      //  mClusterManager = ClusterManager< com.example.shruti.myapplication.Model.Location>(getContext(), googleMap);
    }
    MyApplication myApplication;

    public void findPlaces(String placeType,String str, int PROXIMITY_RADIUS){

        searchMapViewModel.SetMapData(placeType, str, PROXIMITY_RADIUS);


        searchMapViewModel.getNearByPlaces().observe(this, new Observer<NearByApiResponse>() {
            @Override
            public void onChanged(@Nullable NearByApiResponse nearByApiResponses) {

                if(nearByApiResponses != null){

                    setMapData(nearByApiResponses);

                }
            }
        });

        searchMapViewModel.getNearByPlaces().observe(this, new Observer<NearByApiResponse>() {
            @Override
            public void onChanged(@Nullable NearByApiResponse nearByPlacePOJOS) {
                sample_adapter = new Sample_Adapter(getContext(),nearByPlacePOJOS);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(sample_adapter);

            }
        });

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(getContext(), "Location Permission has been denied, can not search the places you want.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }
    Double lat, lng;
    public void setMapData(NearByApiResponse nearByApiResponses) {

        try {
            mClusterManagerNear = new ClusterManager<NearByApiResponse>(getContext(), googleMap);
            markerOptions = new MarkerOptions();
            for (int i = 0; i < nearByApiResponses.getResults().size(); i++) {
                lat = nearByApiResponses.getResults().get(i).getGeometry().getLocation().getLat();
                lng = nearByApiResponses.getResults().get(i).getGeometry().getLocation().getLng();
                String placeName = nearByApiResponses.getResults().get(i).getName();
                String vicinity = nearByApiResponses.getResults().get(i).getVicinity();
                String image = nearByApiResponses.getResults().get(i).getIcon();
                latLng = new LatLng(lat, lng);
                addItems(lat, lng);
                Log.e("Lat: ",lat.toString());
                Log.e("Long: ", lng.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void addItems(Double lat, Double lng){
        //for (int i = 0; i < 20; i++) {
//            double offset = i / 60d;
//            lat = lat + offset;
//            lng = lng + offset;
            NearByApiResponse offsetItem = new NearByApiResponse(lat, lng);
            mClusterManagerNear.addItem(offsetItem);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 10));
        googleMap.setOnCameraIdleListener(mClusterManagerNear);
        googleMap.setOnMarkerClickListener(mClusterManagerNear);
        //}
    }



}

////        Call<NearByApiResponse> call = MyApplication.getApp().getApiService().getNearbyPlaces(placeType, str, PROXIMITY_RADIUS);
////
////        call.enqueue(new Callback<NearByApiResponse>() {
////            @Override
////            public void onResponse(Call<NearByApiResponse> call, Response<NearByApiResponse> response) {
////                try {
////                    googleMap.clear();
////
////                    // This loop will go through all the results and add marker on each location.
////                    for (int i = 0; i < response.body().getResults().size(); i++) {
////                        Double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
////                        Double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();
////                        String placeName = response.body().getResults().get(i).getName();
////                        String vicinity = response.body().getResults().get(i).getVicinity();
////                        MarkerOptions markerOptions = new MarkerOptions();
////                        LatLng latLng = new LatLng(lat, lng);
////                        // Location of Marker on Map
////                        markerOptions.position(latLng);
////                        // Title for Marker
////                        markerOptions.title(placeName + " : " + vicinity);
////                        // Color or drawable for marker
////                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
////                        // add marker
////                        Marker m = googleMap.addMarker(markerOptions);
////                        // move map camera
////                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
////                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
////                    }
////                } catch (Exception e) {
////                    Log.d("onResponse", "There is an error");
////                    e.printStackTrace();
////                }
//            }
//
//            @Override
//            public void onFailure(Call<NearByApiResponse> call, Throwable t) {
//                Log.d("onFailure", t.toString());
//                t.printStackTrace();
//                //PROXIMITY_RADIUS += 10000;
//            }
//        });


//                markerOptions = new MarkerOptions();
//                markerOptions.position(latLng);
//                markerOptions.title(placeName + " : " + vicinity);
//                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//                Marker m = googleMap.addMarker(markerOptions);
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));