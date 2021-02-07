package com.example.osamah.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.osamah.R;

public class GraphNav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_nav);
    }

    public void g1(View view) {
        Intent intent = new Intent(GraphNav.this, Graph1.class);
        startActivity(intent);
        finish();

    }

    public void g2(View view) {
        Intent intent = new Intent(GraphNav.this, Graph2.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GraphNav.this, DoctorActivites.class);
        startActivity(intent);
        finish();

    }
}