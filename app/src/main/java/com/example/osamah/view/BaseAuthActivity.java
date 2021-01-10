package com.example.osamah.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.osamah.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseAuthActivity extends AppCompatActivity {
    // in here japatck and controller should be here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
}