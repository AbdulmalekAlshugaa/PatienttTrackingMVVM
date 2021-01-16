package com.example.osamah.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.osamah.R;
import com.example.osamah.adapters.SeziureList_Adapter;
import com.example.osamah.model.SeisureModel;
import com.example.osamah.viewModel.SesiureViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Controller_main extends Fragment {
    private SeziureList_Adapter seziureList_adapter;
    private ArrayList<SeisureModel> seisureModelArrayList = new ArrayList<>();
    private RecyclerView ListDataView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private View view;
    private SesiureViewModel sesiureViewModel;

    private static final String TAG = "Controller_main";
    NavController navController;
    public Controller_main() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sesiureViewModel  = ViewModelProviders.of(this).get(SesiureViewModel.class);
        sesiureViewModel.getSeisureModelMutableLiveData().observe(this, new Observer<SeisureModel>() {
            @Override
            public void onChanged(SeisureModel seisureModel) {
                seisureModelArrayList.add(seisureModel);
                seziureList_adapter.notifyDataSetChanged();

                }


        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_controller_main, container, false);
        ListDataView = view.findViewById(R.id.ListJobPost);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.ContinerRefersh);
        // osamah
        TextView get = view.findViewById(R.id.JobDiscover);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        RecyclerViewMethods();
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         navController = Navigation.findNavController(view);
         // osama update ui
        sesiureViewModel.getSensiure(FirebaseAuth.getInstance().getUid());



//        Button button = view.findViewById(R.id.finishGameButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navController.navigate(R.id.action_gameFragment_to_endgameFragment);
//            }
//        });
    }

    private void RecyclerViewMethods() {
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        ListDataView.setLayoutManager(manager);
        ListDataView.setHasFixedSize(true);
        seziureList_adapter = new SeziureList_Adapter(view.getContext(), seisureModelArrayList);
        ListDataView.setAdapter(seziureList_adapter);
        ListDataView.invalidate();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
