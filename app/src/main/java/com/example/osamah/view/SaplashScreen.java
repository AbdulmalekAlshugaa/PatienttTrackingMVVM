package com.example.osamah.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.osamah.Fragments.LoginFragment;
import com.example.osamah.R;

public class SaplashScreen extends AppCompatActivity {

    ImageView mLogo;

    Animation uptodown,downtoup;
    private static final String TAG = "MainActivity";

    private TextView AppVerion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saplash_screen);
        mLogo = findViewById(R.id.troopersIcon);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        AppVerion  = findViewById(R.id.AppVerion);
        userNav();
    }

    public void userNav(){
        //    addPhotoBottomDialogFragment.dismiss();
        Thread myThread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(1000);
                        Intent intent = new Intent(getApplicationContext(), ControllerActivity.class);
                        startActivity(intent);
                        finish();


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

}