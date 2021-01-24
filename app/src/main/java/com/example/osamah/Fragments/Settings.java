package com.example.osamah.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.osamah.R;
import com.example.osamah.databinding.FragmentSettingsBinding;
import com.example.osamah.view.ControllerActivity;
import com.example.osamah.view.SaplashScreen;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters


    public Settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters

    FragmentSettingsBinding binding;
    View view;
    private static final String TAG = "Settings";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: settings ");
        binding = FragmentSettingsBinding.inflate(inflater, container,false);
        view = binding.getRoot();
        view =  inflater.inflate(R.layout.fragment_settings, container, false);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), SaplashScreen.class);
        startActivity(intent);



        return  view;
    }
}