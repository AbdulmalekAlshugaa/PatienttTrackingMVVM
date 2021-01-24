package com.example.osamah.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osamah.R;
import com.example.osamah.model.Notifications;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.irozon.sneaker.Sneaker;

import java.time.temporal.Temporal;
import java.util.HashMap;

public class OnclickList extends AppCompatActivity {

    private TextView names,graph;
    private Button SubmitNote;
    private EditText editNoteMessage;
    private FirebaseFirestore db;
    private static final String TAG = "OnclickList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onclick_list);
        String fullName = getIntent().getStringExtra("name");
        Log.d(TAG, "onCreate: "+getIntent().getStringExtra("email"));

        SubmitNote = findViewById(R.id.SubmitNote);
        names = findViewById(R.id.names);
        graph = findViewById(R.id.graphs);
        editNoteMessage = findViewById(R.id.editNoteMessage);
        db = FirebaseFirestore.getInstance();
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
                String text = editNoteMessage.getText().toString().trim();
                if(text.isEmpty()){
                    Sneaker.with(OnclickList.this) // Activity, Fragment or ViewGroup
                            .setTitle("Error")
                            .setMessage("Something went wrong")
                            .sneakError();
                }else {
                    Notifications notifications = new Notifications(FirebaseAuth.getInstance().getCurrentUser().getEmail(),getIntent().getStringExtra("email"),text );
                    db.collection("Comments").add(notifications)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        if(task.isSuccessful()){
                                            Sneaker.with(OnclickList.this) // Activity, Fragment or ViewGroup
                                                    .setTitle("Added")
                                                    .setMessage("comment has added successfully")
                                                    .sneakSuccess();

                                        }else {
                                            Sneaker.with(OnclickList.this) // Activity, Fragment or ViewGroup
                                                    .setTitle("Error")
                                                    .setMessage("Something went wrong")
                                                    .sneakError();
                                        }
                                    }
                                }
                            });
                }

            }
        });
    }
}