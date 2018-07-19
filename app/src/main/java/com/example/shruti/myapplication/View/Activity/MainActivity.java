package com.example.shruti.myapplication.View.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.example.shruti.myapplication.R;

public class MainActivity extends AppCompatActivity {

    public static final String TAG ="MainActivity";
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        if (savedInstanceState != null)
        {
            fragment = (MapFragment)getSupportFragmentManager().findFragmentByTag("MyFragment1");
        }
        else {
            fragment = new MapFragment();
        }
        fragment = new MapFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.rlMain, fragment , "Map Fragment");
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
    }
}
