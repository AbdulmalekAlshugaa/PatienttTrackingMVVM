package com.example.osamah.Repository;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.osamah.errorHandlling.ErrorHandlling;
import com.example.osamah.model.SeisureModel;
import com.example.osamah.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class AppRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    FirebaseFirestore db;
    private MutableLiveData<User> userMutableLiveData;
    private MutableLiveData<String> errorHandlling;


    private MutableLiveData<SeisureModel> seisureModelMutableLiveData;
    private static final String TAG = "AppRepository";

    public AppRepository(Application application) {
        this.application = application;
        // instance function
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUserMutableLiveData = new MutableLiveData<>();
        seisureModelMutableLiveData = new MutableLiveData<>();
        // Error Handlling
        errorHandlling = new MutableLiveData<>();
        // add db and user object
        db = FirebaseFirestore.getInstance();
        userMutableLiveData = new MutableLiveData<>();


    }
    // registerion function
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void createAuser (User user){
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).
                addOnCompleteListener(application.getMainExecutor(),new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // add other prams (Complete reg)
                    // data will be posted here
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                }else {
                    // create INterface here in order to pass an erro to the the view
                    Toast.makeText(application, "Error",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    // complete user registeratioon
    public void addUserData(User user){
        db.collection("User").document(firebaseAuth.getCurrentUser().getUid())
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            // from here u can get the user data
                            userMutableLiveData.postValue(user);
                        }else {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    // is uer login
    public boolean isLogin(String UID){
        if(UID !=null){
            return true;
        }else {
            return false;
        }
    }


    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    // login repo
    public void LoginusingEmaailAndPassword (String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                }else {
                    Toast.makeText(application, "Something went wrong ",Toast.LENGTH_LONG).show();
                    errorHandlling.setValue(task.getException().getMessage().toString());
                }


            }
        });
    }
    // Sesiure model
    public void addSesiureModel(SeisureModel seisureModel){
        db.collection("Seizure").document(firebaseAuth.getCurrentUser().getUid()).set(seisureModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            seisureModelMutableLiveData.setValue(seisureModel);
                        }else {
                            Toast.makeText(application, "Something went wrong ",Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }
    //


    public MutableLiveData<SeisureModel> getSeisureModelMutableLiveData() {
        return seisureModelMutableLiveData;
    }

    public void fetchSizureData(String UID){
        db.collection("Seizure").document(UID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            SeisureModel seisureModel = document.toObject(SeisureModel.class);
                            seisureModelMutableLiveData.setValue(seisureModel);
                        }else {
                            Toast.makeText(application,"Erro while fetching",Toast.LENGTH_LONG).show();
                        }


                    }

                });
    }




}
