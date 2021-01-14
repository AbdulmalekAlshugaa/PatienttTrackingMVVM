package com.example.osamah.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.osamah.databinding.CamreContentBinding;
import com.example.osamah.model.SeisureModel;
import com.example.osamah.view.ControllerActivity;
import com.example.osamah.viewModel.SesiureViewModel;
import com.example.osamah.viewModel.UserViewModel;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.osamah.Fragments.camare.REQUEST_VIDEO_CAPTURE;


public class Seizure_content extends Fragment {
    CamreContentBinding binding;
    private View view;
    private Uri uri_image;
    private SesiureViewModel sesiureViewModel;
    private static final String TAG = "Seizure_content";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sesiureViewModel  = ViewModelProviders.of(this).get(SesiureViewModel.class);
        sesiureViewModel.getSeisureModelMutableLiveData().observe(this, new Observer<SeisureModel>() {
            @Override
            public void onChanged(SeisureModel seisureModel) {
                if(seisureModel.getDate() !=null){
                    // shows successfull message
                    Toast.makeText(getActivity(),"Added",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CamreContentBinding.inflate(inflater, container,false);
        view = binding.getRoot();

        binding.Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                SeisureModel seisureModel = new SeisureModel("URl","JASD","asnd","AS]KD","SAD","OIAsd","iASD","OIASnd");
                sesiureViewModel.addSeisure(seisureModel);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                } else {
                    dispatchTakeVideoIntent();
                }
            }
        });

    }

    private void PickerImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            Log.d(TAG, "onActivityResult: "+videoUri);
            binding.image.setVideoURI(videoUri);
        }
        }
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
//        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
//            Uri videoUri = intent.getData();
//            Log.d(TAG, "onActivityResult: "+videoUri);
//            binding.image.setVideoURI(videoUri);
//        }
//    }

    private void dispatchTakeVideoIntent() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        } else {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }
        }
    }
}
