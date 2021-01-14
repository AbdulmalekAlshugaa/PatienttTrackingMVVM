package com.example.osamah.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.osamah.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ControllerActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        setUpNavigation();
    }
    public void setUpNavigation(){
        bottomNavigationView =findViewById(R.id.PartimerBottomMenu);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,
                navHostFragment.getNavController());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.camare:
                        dispatchTakeVideoIntent();
                    case R.id.discover:

                        return true;
                    case R.id.myJobs:

                        return true;
                    case R.id.notification:

                        return true;

                }


                return true;
            }
        });
    }
    private void dispatchTakeVideoIntent() {
        if (ContextCompat.checkSelfPermission(ControllerActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ControllerActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            Toast.makeText(ControllerActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
        } else {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            // videoView.setVideoURI(videoUri);
        }
    }
}