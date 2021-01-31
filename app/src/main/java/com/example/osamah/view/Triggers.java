package com.example.osamah.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.osamah.R;

public class Triggers extends AppCompatActivity {

    private Button triggers, activity, location;
    private static final String TAG = "Triggers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triggers);
        Log.d(TAG, "onCreate:  triggers");



    }
}