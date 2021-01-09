package com.example.osamah.Repository;

import android.app.Application;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.osamah.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class AppRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    FirebaseFirestore db;
    private MutableLiveData<User> userMutableLiveData;

    public AppRepository(Application application) {
        this.application = application;
        // instance function
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUserMutableLiveData = new MutableLiveData<>();
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

    public void addUserData(User user){
        db.collection("User").document(firebaseAuth.getCurrentUser().getUid())
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            // from here u can get the user data
                            userMutableLiveData.postValue(user);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }
}
