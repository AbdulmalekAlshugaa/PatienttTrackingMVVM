package com.example.osamah;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.osamah.model.SeisureModel;
import com.example.osamah.viewModel.SesiureViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.irozon.sneaker.Sneaker;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link list_posts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class list_posts extends Fragment {
    private SesiureViewModel sesiureViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public list_posts() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment list_posts.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sesiureViewModel  = ViewModelProviders.of(this).get(SesiureViewModel.class);
        sesiureViewModel.getSeisureModelMutableLiveData().observe(this, new Observer<SeisureModel>() {
            @Override
            public void onChanged(SeisureModel seisureModel) {
                if(seisureModel.getDate() !=null){

                }else {
                    Sneaker.with(getActivity()) // Activity, Fragment or ViewGroup
                            .setTitle("Error ")
                            .setMessage("Error while fetching data from the server")
                            .sneakSuccess();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sesiureViewModel.getSensiure(FirebaseAuth.getInstance().getUid());

        return inflater.inflate(R.layout.fragment_list_posts, container, false);
    }
}