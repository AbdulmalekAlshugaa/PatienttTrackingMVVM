package com.example.osamah.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.osamah.Fragments.caching.SingealtonLocalData;
import com.example.osamah.Fragments.caching.UserPerf;
import com.example.osamah.R;


import com.example.osamah.helper.CameraUtils;
import com.example.osamah.model.SeisureModel;
import com.example.osamah.model.UserActivites;
import com.example.osamah.view.SaplashScreen;
import com.example.osamah.viewModel.SesiureViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.irozon.sneaker.Sneaker;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.osamah.Fragments.CamareFragmen.REQUEST_VIDEO_CAPTURE;
import static com.example.osamah.helper.constants.Activities;
import static com.example.osamah.helper.constants.Location;
import static com.example.osamah.helper.constants.Triggers;


public class Seizure_content extends Fragment {
    com.example.osamah.databinding.CamreContentBinding binding;
    private View view;

    private SesiureViewModel sesiureViewModel;
    private static final String TAG = "Seizure_content";
    String mTrigger, mActivity, mLocation;
    String getDate, getTime;
/// video

    private Uri videouri;
    private static final int REQUEST_CODE = 101;
    private StorageReference videoref;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    static final int REQUEST_VIDEO_CAPTURE = 1;
    Uri video;

    ProgressDialog mProgressDialog;
    StorageReference storageRef;
    int mFivercounter, mCounter2, mCouner3; // that is will be triggers based on the usr selection in orde to increase the number of the triggers . counter

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if()
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        videoref = storageRef.child("/videos" + "/userIntro.3gp");


        if (SingealtonLocalData.getInstance(getActivity()).getLocalUserData() == null) {
            Log.d(TAG, "onCreate: we are null");
            UserPerf userPerf = new UserPerf(0, 0, 0);
            SingealtonLocalData.getInstance(getActivity()).SaveUserLocalData(userPerf);
            SingealtonLocalData.getInstance(getActivity()).getLocalUserData().getNoactivites();
        }

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please wait, Uploading");
        sesiureViewModel = ViewModelProviders.of(this).get(SesiureViewModel.class);
        sesiureViewModel.getSeisureModelMutableLiveData().observe(this, new Observer<SeisureModel>() {
            @Override
            public void onChanged(SeisureModel seisureModel) {
                if (seisureModel.getDate() != null) {
                    // show successful message
                    mProgressDialog.dismiss();
                    Sneaker.with(getActivity()) // Activity, Fragment or ViewGroup
                            .setTitle("Added")
                            .setMessage("Data has added successfully")
                            .sneakSuccess();
                    mProgressDialog.dismiss();
                } else {
                    mProgressDialog.dismiss();
                }
            }
        });


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = com.example.osamah.databinding.CamreContentBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        binding.Trigger.setItems(Triggers);
        binding.Location.setItems(Location);
        binding.Activity.setItems(Activities);
        ;
        // set li
        binding.videoPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                } else {
                    //
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE);

                }
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.Trigger.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Log.d(TAG, "onItemSelected: postion of triggers" + position);
                if (position == 1) {
                    if (SingealtonLocalData.getInstance(getActivity()).getTrigger() == 0) {
                        mFivercounter = mFivercounter + 1;
                    } else {
                        mFivercounter = SingealtonLocalData.getInstance(getActivity()).getTrigger() + 1;
                    }
                    SingealtonLocalData.getInstance(getActivity()).SaveTriggers(mFivercounter);
                } else if (position == 2) {
                    if (SingealtonLocalData.getInstance(getActivity()).getTrigger2() == 0) {
                        mFivercounter = mFivercounter + 1;
                    } else {
                        mFivercounter = SingealtonLocalData.getInstance(getActivity()).getTrigger2()+ 1;
                    }
                    SingealtonLocalData.getInstance(getActivity()).SaveTriggers(mFivercounter);

                }else  {
                    if (SingealtonLocalData.getInstance(getActivity()).getTrigger2() == 0) {
                        mFivercounter = mFivercounter + 1;
                    } else {
                        mFivercounter = SingealtonLocalData.getInstance(getActivity()).getTrigger2()+ 1;
                    }
                    SingealtonLocalData.getInstance(getActivity()).SaveTriggers(mFivercounter);
                }
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
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

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
                            getTime = String.valueOf(hourOfDay + "\t:" + minute);
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
                Log.d(TAG, "onClick: " + SingealtonLocalData.getInstance(getActivity()).getTrigger());

                String note = binding.Note.getText().toString();
                String leanth = binding.leanth.getText().toString();
                if (note.isEmpty() || leanth.isEmpty()) {
                    Sneaker.with(getActivity()) // Activity, Fragment or ViewGroup
                            .setTitle("Error")
                            .setMessage("seems some filed are empty")
                            .sneakError();
                } else {

                    mProgressDialog.show();
                    int noActvites = SingealtonLocalData.getInstance(getActivity()).getLocalUserData().getNoactivites() + 1;
                    int notrigger = SingealtonLocalData.getInstance(getActivity()).getLocalUserData().getNotriggers() + 1;
                    int nolocation = SingealtonLocalData.getInstance(getActivity()).getLocalUserData().getNolocations() + 1;
                    if (videouri != null) {

                        HashMap<String, Object> listofTrigger = new HashMap<>();
                        listofTrigger.put("fiver", SingealtonLocalData.getInstance(getActivity()).getTrigger());
                        listofTrigger.put("illes", SingealtonLocalData.getInstance(getActivity()).getTrigger2()-3);
                        listofTrigger.put("sleep", SingealtonLocalData.getInstance(getActivity()).getTrigger3()-4);
                        HashMap<String, Object> listofActivites = new HashMap<>();
                        listofTrigger.put("Falling Asleep", SingealtonLocalData.getInstance(getActivity()).getTrigger());
                        listofTrigger.put("WakingUp", SingealtonLocalData.getInstance(getActivity()).getTrigger2()-3);
                        listofTrigger.put("Phone", SingealtonLocalData.getInstance(getActivity()).getTrigger3()-2);
                        HashMap<String, Object> listofLocation = new HashMap<>();
                        listofTrigger.put("Home", SingealtonLocalData.getInstance(getActivity()).getTrigger()-1);
                        listofTrigger.put("School", SingealtonLocalData.getInstance(getActivity()).getTrigger2()-2);
                        listofTrigger.put("Others", SingealtonLocalData.getInstance(getActivity()).getTrigger3()-3);

                        Log.d(TAG, "onClick triggers: " + SingealtonLocalData.getInstance(getActivity()).getTrigger());
                        UserActivites userActivites = new UserActivites(listofTrigger);


                        videoref.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                videoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String VidUrl = uri.toString();
                                        SeisureModel seisureModel = new SeisureModel(VidUrl, getDate, getTime, leanth, mTrigger, mActivity, mLocation, note,
                                                FirebaseAuth.getInstance().getUid()
                                                , listofTrigger, noActvites, nolocation);
                                        sesiureViewModel.addSeisure(seisureModel);
                                        FirebaseFirestore.getInstance().collection("triggers")
                                                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                                                .set(userActivites)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getActivity(), "Upload complete",
                                                                    Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                        Toast.makeText(getActivity(), "Upload complete",
                                                Toast.LENGTH_LONG).show();

                                    }
                                });
                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "Nothing to upload",
                                Toast.LENGTH_LONG).show();
                    }


                }


            }
        });


    }


    //

    private void requestCameraPermission(final int type) {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {


                            //    captureVideo();


                        } else if (report.isAnyPermissionPermanentlyDenied()) {
                            showPermissionsAlert();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).check();
    }


    private void showPermissionsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Permissions required!")
                .setMessage("Camera needs few permissions to work properly. Grant them in settings.")
                .setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        CameraUtils.openSettings(getActivity());
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    public void updateProgress(UploadTask.TaskSnapshot taskSnapshot) {

        @SuppressWarnings("VisibleForTests") long fileSize =
                taskSnapshot.getTotalByteCount();

        @SuppressWarnings("VisibleForTests")
        long uploadBytes = taskSnapshot.getBytesTransferred();

        long progress = (100 * uploadBytes) / fileSize;

        ProgressBar progressBar = binding.pbar;
        progressBar.setProgress((int) progress);
    }

    public void record(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        videouri = data.getData();
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                binding.videoPreview.setVideoURI(videouri);
                Toast.makeText(getActivity(), "Video saved to:\n" +
                        videouri, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
