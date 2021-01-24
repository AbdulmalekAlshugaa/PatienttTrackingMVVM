package com.example.osamah.Fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.osamah.R;

import java.time.temporal.Temporal;

public class OnclickList extends AppCompatActivity {

    private TextView names,graph;
    private Button SubmitNote;
    private EditText editNoteMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onclick_list);
        String fullName = getIntent().getStringExtra("name");
        SubmitNote = findViewById(R.id.SubmitNote);
        names = findViewById(R.id.names);
        graph = findViewById(R.id.graphs);
        editNoteMessage = findViewById(R.id.editNoteMessage);
        names.setText(fullName+" \tRecords");
        graph.setText(fullName+" \tgraphs");
        names.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        SubmitNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editNoteMessage.getText().toString() == ""){
                    String text = editNoteMessage.getText().toString().trim();

                }

            }
        });
    }
}