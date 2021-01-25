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
import com.example.osamah.view.SaplashScreen;
import com.example.osamah.viewModel.SesiureViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
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
import java.util.Calendar;
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
    String getDate,getTime;
/// video
private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    // key to store image path in savedInstance state
    public static final String KEY_IMAGE_STORAGE_PATH = "image_path";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    // Bitmap sampling size
    public static final int BITMAP_SAMPLE_SIZE = 8;
    // Gallery directory name to store the images or videos
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    // Image and Video file extensions
    public static final String IMAGE_EXTENSION = "jpg";
    public static final String VIDEO_EXTENSION = "mp4";
    private static String imageStoragePath;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    static final int REQUEST_VIDEO_CAPTURE = 1;
    Uri video ;

    ProgressDialog mProgressDialog;
    StorageReference storageRef;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if()

        if(SingealtonLocalData.getInstance(getActivity()).getLocalUserData() == null){
            Log.d(TAG, "onCreate: we are null");
            UserPerf userPerf = new UserPerf(0,0,0);
            SingealtonLocalData.getInstance(getActivity()).SaveUserLocalData(userPerf);
            SingealtonLocalData.getInstance(getActivity()).getLocalUserData().getNoactivites();
        }
 
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please wait, Uploading");
        sesiureViewModel  = ViewModelProviders.of(this).get(SesiureViewModel.class);
        sesiureViewModel.getSeisureModelMutableLiveData().observe(this, new Observer<SeisureModel>() {
            @Override
            public void onChanged(SeisureModel seisureModel) {
                if(seisureModel.getDate() !=null){
                    // show successful message
                    mProgressDialog.dismiss();
                    Sneaker.with(getActivity()) // Activity, Fragment or ViewGroup
                            .setTitle("Added")
                            .setMessage("Data has added successfully")
                            .sneakSuccess();
                    mProgressDialog.dismiss();
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
        binding.videoPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                } else {
                    if (CameraUtils.checkPermissions(getActivity())) {
                        captureVideo();
                    } else {
                        requestCameraPermission(MEDIA_TYPE_VIDEO);
                    }
                }
            }
        });

        restoreFromBundle(savedInstanceState);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                    mProgressDialog.show();
                    int noActvites = SingealtonLocalData.getInstance(getActivity()).getLocalUserData().getNoactivites()+1;
                    int notrigger = SingealtonLocalData.getInstance(getActivity()).getLocalUserData().getNotriggers()+1;
                    int nolocation = SingealtonLocalData.getInstance(getActivity()).getLocalUserData().getNolocations()+1;

                    SeisureModel seisureModel = new SeisureModel(String.valueOf("uri"),getDate,getTime,leanth,mTrigger,mActivity,mLocation,note,
                            FirebaseAuth.getInstance().getUid()
                    , notrigger,noActvites,nolocation);
                    sesiureViewModel.addSeisure(seisureModel);

//                    if(video!=null){
//                        // get the video url
//                        ;
//                        UserPerf userPerf = new UserPerf(noActvites,notrigger,nolocation);
//                        SingealtonLocalData.getInstance(getActivity()).SaveUserLocalData(userPerf);
////                        mProgressDialog.show();
////                        SeisureModel seisureModel = new SeisureModel(String.valueOf("uri"),getDate,getTime,leanth,mTrigger,mActivity,mLocation,note, FirebaseAuth.getInstance().getUid());
////                        sesiureViewModel.addSeisure(seisureModel);
////                        mProgressDialog.dismiss();
////                        StorageReference folder = FirebaseStorage.getInstance().getReference().child("ProductImages");
////                        final StorageReference filename = folder.child(System.currentTimeMillis()+
////                                FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
////                        filename.putFile(video).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////                            @Override
////                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                                filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
////                                    @Override
////                                    public void onSuccess(Uri uri) {
////                                        Log.d(TAG, "onSuccess: urls "+uri);
////
////                                    }
////                                });
////                            }
////                        });
//                        Log.d(TAG, "onClick: here is"+video.toString());
//
//                    }

                }


            }
        });




    }

    private void PickerImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(getActivity());
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



    //
    private void restoreFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_IMAGE_STORAGE_PATH)) {
                imageStoragePath = savedInstanceState.getString(KEY_IMAGE_STORAGE_PATH);
                if (!TextUtils.isEmpty(imageStoragePath)) {
                    if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + IMAGE_EXTENSION)) {
                        previewCapturedImage();
                    } else if (imageStoragePath.substring(imageStoragePath.lastIndexOf(".")).equals("." + VIDEO_EXTENSION)) {
                        Uri video = Uri.parse(imageStoragePath);
                        binding.videoPreview.setVideoURI(video);

                    }
                }
            }
        }
    }
    private void requestCameraPermission(final int type) {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                            if (type == MEDIA_TYPE_IMAGE) {
                                // capture picture
                                captureImage();
                            } else {
                                captureVideo();
                            }

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
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getActivity(), file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(KEY_IMAGE_STORAGE_PATH, imageStoragePath);
    }
    private void captureVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        File file = CameraUtils.getOutputMediaFile(MEDIA_TYPE_VIDEO);
        if (file != null) {
            imageStoragePath = file.getAbsolutePath();
        }

        Uri fileUri = CameraUtils.getOutputMediaFileUri(getActivity(), file);

        // set video quality
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file

        // start the video capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Refreshing the gallery
                CameraUtils.refreshGallery(getActivity(), imageStoragePath);

                // video successfully recorded
                // preview the recorded video
                 video = Uri.parse(imageStoragePath);
                binding.videoPreview.setVideoURI(video);
                binding.videoPreview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        binding.videoPreview.start();
                    }
                });

            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled recording
                Toast.makeText(getActivity(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to record video
                Toast.makeText(getActivity(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private void previewCapturedImage() {
        try {

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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
}
