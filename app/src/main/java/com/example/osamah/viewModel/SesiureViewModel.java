package com.example.osamah.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.osamah.Repository.AppRepository;
import com.example.osamah.model.SeisureModel;
import com.example.osamah.model.User;
import com.google.firebase.auth.FirebaseUser;

public class SesiureViewModel extends AndroidViewModel {
    private AppRepository appRepository;
    private MutableLiveData<SeisureModel> seisureModelMutableLiveData;

    private User user;
    public SesiureViewModel(@NonNull Application application) {
        super(application);

        appRepository = new AppRepository(application);
        seisureModelMutableLiveData = appRepository.getSeisureModelMutableLiveData();
        // complete user signup

    }

    public void addSeisure(SeisureModel seisureModel){
        appRepository.addSesiureModel(seisureModel);
    }

    public MutableLiveData<SeisureModel> getSeisureModelMutableLiveData() {
        return seisureModelMutableLiveData;
    }
    public void getSensiure(String UID){
        appRepository.fetchSizureData(UID);

    }
}
