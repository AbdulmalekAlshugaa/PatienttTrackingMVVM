package com.example.osamah.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.osamah.databinding.TestfragmentBinding;

public class SignUpFragment extends Fragment {
    private View view;
    TestfragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TestfragmentBinding.inflate(inflater, container,false);
        view = binding.getRoot();

        return view;

    }
}
