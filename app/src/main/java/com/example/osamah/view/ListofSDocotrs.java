package com.example.osamah.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.osamah.R;
import com.example.osamah.adapters.SeziureList_Adapter;
import com.example.osamah.model.SeisureModel;
import com.example.osamah.viewModel.SesiureViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListofSDocotrs extends AppCompatActivity {

    private SeziureList_Adapter seziureList_adapter;
    private ArrayList<SeisureModel> seisureModelArrayList = new ArrayList<>();
    private RecyclerView ListDataView;
    SwipeRefreshLayout mSwipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_s_docotrs);
        ListDataView = findViewById(R.id.ListJobPosts);
        mSwipeRefreshLayout = findViewById(R.id.ContinerRefersh);



        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        RecyclerViewMethods();
        listDate();
    }

    private void RecyclerViewMethods() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        ListDataView.setLayoutManager(manager);
        ListDataView.setHasFixedSize(true);
        seziureList_adapter = new SeziureList_Adapter(this, seisureModelArrayList);
        ListDataView.setAdapter(seziureList_adapter);
        ListDataView.invalidate();

    }

    public void listDate(){
        FirebaseFirestore
                .getInstance().collection("Seizure")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (int i=0; i<task.getResult().size(); i++){
                                SeisureModel seisureModel = new SeisureModel();
                                String date = task.getResult().getDocuments().get(i).getString("date");
                                String seizureImage = task.getResult().getDocuments().get(i).getString("seizureImage");
                                String time = task.getResult().getDocuments().get(i).getString("time");
                                seisureModel.setDate(date);
                                seisureModel.setTime(time);
                                seisureModel.setSeizureImage(seizureImage);
                                seisureModelArrayList.add(seisureModel);

                            }
                            seziureList_adapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(ListofSDocotrs.this, "Error while loading",Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }

}