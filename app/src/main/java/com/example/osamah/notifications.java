package com.example.osamah;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.osamah.adapters.CommentsListAdapter;
import com.example.osamah.adapters.SeziureList_Adapter;
import com.example.osamah.model.Notifications;
import com.example.osamah.model.SeisureModel;
import com.example.osamah.viewModel.SesiureViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link notifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notifications extends Fragment {

    private CommentsListAdapter commentsListAdapter;
    private ArrayList<Notifications> seisureModelArrayList = new ArrayList<>();
    private RecyclerView ListDataView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private View view;
    private Notifications sesiureViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    public notifications() {
        // Required empty public constructor
    }
    private void RecyclerViewMethods() {
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        ListDataView.setLayoutManager(manager);
        ListDataView.setHasFixedSize(true);
        commentsListAdapter = new CommentsListAdapter(view.getContext(), seisureModelArrayList);
        ListDataView.setAdapter(commentsListAdapter);
        ListDataView.invalidate();

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment notifications.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    public void getComments(){
        FirebaseFirestore
                .getInstance().collection("Comments")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (int i=0; i<task.getResult().size(); i++){
                    Notifications notifications = new Notifications();
                    String patID = task.getResult().getDocuments().get(i).getString("paEmail");
                    if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(patID)){
                        String comments = task.getResult().getDocuments().get(i).getString("comments");
                        String DrEmail = task.getResult().getDocuments().get(i).getString("drEmail");
                        notifications.setComments(comments);
                        notifications.setDrEmail(DrEmail);
                        seisureModelArrayList.add(notifications);
                    }

                }
                commentsListAdapter.notifyDataSetChanged();
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_notifications, container, false);

        ListDataView = view.findViewById(R.id.ListComments);


        RecyclerViewMethods();
        getComments();

        return view;
    }
}