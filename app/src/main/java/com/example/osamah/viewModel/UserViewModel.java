package com.example.osamah.viewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.osamah.Repository.AppRepository;
import com.example.osamah.model.User;
import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends AndroidViewModel {


    private AppRepository appRepository;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<User> userMutableLiveData;
    private User user;


    @RequiresApi(api = Build.VERSION_CODES.P)
    public UserViewModel(@NonNull Application application) {
        super(application);
        user = new User();

        appRepository = new AppRepository(application);
        firebaseUserMutableLiveData = appRepository.getFirebaseUserMutableLiveData();
        // complete user signup
        userMutableLiveData = appRepository.getUserMutableLiveData();

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(User user){
        appRepository.createAuser(user);
    }

    public void addUserdetails(User user){
        appRepository.addUserData(user);
    }
    public void UserLogin(String  email , String password){
        appRepository.LoginusingEmaailAndPassword(email,password);
    }



    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }


    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }
}
