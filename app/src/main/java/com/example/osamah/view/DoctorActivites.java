package com.example.osamah.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.osamah.R;
import com.example.osamah.adapters.UserListAdapter;
import com.example.osamah.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DoctorActivites extends AppCompatActivity {
    private ArrayList<User> jobPostslists = new ArrayList<>(); // mNote
    private UserListAdapter mPostsAdapter; // Adapter
    private RecyclerView ListDataView; // View Items
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_activites);
        ListDataView = findViewById(R.id.listView);
        db = FirebaseFirestore.getInstance();
        RecyclerViewMethods();
        // fetch data
        fetchUserData();

    }


    private void RecyclerViewMethods() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ListDataView.setLayoutManager(manager);
        ListDataView.setHasFixedSize(true);
        mPostsAdapter = new UserListAdapter(this, jobPostslists);
        ListDataView.setAdapter(mPostsAdapter);
        ListDataView.invalidate();

    }

    void fetchUserData() {
        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<DocumentSnapshot> snapshots = (ArrayList<DocumentSnapshot>) task.getResult().getDocuments();
                            for (int i = 0; i < snapshots.size(); i++) {
                                String userType = snapshots.get(i).getString("confirmPhoneNumber");
                                if (userType.equals("P")) {
                                    String FullAName = snapshots.get(i).getString("fullName");
                                    String email = snapshots.get(i).getString("email");
                                    String uid = snapshots.get(i).getString("uid");
                                    User user = new User();
                                    user.setFullName(FullAName);
                                    user.setEmail(email);
                                    user.setUid(uid);

                                    jobPostslists.add(user);
                                }


                                //  SeisureModel seisureModel = new SeisureModel(snapshots.get(i).get("date"));
                            }

                            ListDataView.getRecycledViewPool().clear();
                            mPostsAdapter.notifyDataSetChanged();

                        }

                    }
                });


    }

}