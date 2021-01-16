package com.example.osamah.Fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.osamah.R;


import com.example.osamah.model.SeisureModel;
import com.example.osamah.view.ControllerActivity;
import com.example.osamah.viewModel.SesiureViewModel;
import com.example.osamah.viewModel.UserViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.irozon.sneaker.Sneaker;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.ParseException;
import java.util.Calendar;

import jrizani.jrspinner.JRSpinner;

import static android.app.Activity.RESULT_OK;
import static com.example.osamah.Fragments.camare.REQUEST_VIDEO_CAPTURE;
import static com.example.osamah.helper.constants.Activities;
import static com.example.osamah.helper.constants.Location;
import static com.example.osamah.helper.constants.Triggers;


public class Seizure_content extends Fragment {
  com.example.osamah.databinding.CamreContentBinding binding;
    private View view;
    private Uri uri_image;
    private SesiureViewModel sesiureViewModel;
    private static final String TAG = "Seizure_content";
    String mTrigger, mActivity, mLocation;
    String getDate,getTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sesiureViewModel  = ViewModelProviders.of(this).get(SesiureViewModel.class);
        sesiureViewModel.getSeisureModelMutableLiveData().observe(this, new Observer<SeisureModel>() {
            @Override
            public void onChanged(SeisureModel seisureModel) {
                if(seisureModel.getDate() !=null){
                    // shows successfull message

                    Sneaker.with(getActivity()) // Activity, Fragment or ViewGroup
                            .setTitle("Added")
                            .setMessage("Data has added successfully")
                            .sneakSuccess();
                }else {
                    Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = com.example.osamah.databinding.CamreContentBinding.inflate(inflater, container,false);
        view = binding.getRoot();
        binding.Trigger.setItems(Triggers);
        binding.Location.setItems(Location);
        binding.Activity.setItems(Activities);
   ;
        // set li


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
        binding.Trigger.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
             mTrigger = String.valueOf(item);
            }
        });
        binding.Activity.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                mActivity = String.valueOf(item);
            }
        });

        binding.Location.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                mLocation = String.valueOf(item);
            }
        });
        binding.Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
               int mYear = c.get(Calendar.YEAR);
              int  mMonth = c.get(Calendar.MONTH);
             int   mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                 getDate = year + "-" + monthOfYear + "-" + dayOfMonth;
                                 binding.Date.setText(getDate);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        binding.Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar myCalender = Calendar.getInstance();
                int hour = myCalender.get(Calendar.HOUR_OF_DAY);
                int minute = myCalender.get(Calendar.MINUTE);


                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (view.isShown()) {
                            myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            myCalender.set(Calendar.MINUTE, minute);
                            getTime = String.valueOf(hourOfDay +"\t:"+ minute);
                            binding.Time.setText(getTime);


                        }
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
                timePickerDialog.setTitle("Choose hour:");
                //timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });

        binding.Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                String note =  binding.Note.getText().toString();
                String  leanth = binding.leanth.getText().toString();
                if(note.isEmpty() || leanth.isEmpty()){
                    Sneaker.with(getActivity()) // Activity, Fragment or ViewGroup
                            .setTitle("Error")
                            .setMessage("seems some filed are empty")
                            .sneakError();
                }else {
                    SeisureModel seisureModel = new SeisureModel("URl",getDate,getTime,leanth,mTrigger,mActivity,mLocation,note, FirebaseAuth.getInstance().getUid());
                    sesiureViewModel.addSeisure(seisureModel);
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
