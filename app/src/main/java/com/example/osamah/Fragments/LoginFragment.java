package com.example.osamah.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.osamah.R;
import com.example.osamah.databinding.MloginfragmentBinding;
import com.example.osamah.databinding.TestfragmentBinding;
import com.example.osamah.view.ControllerActivity;
import com.example.osamah.view.DoctorActivites;
import com.example.osamah.viewModel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.irozon.sneaker.Sneaker;

import java.util.ArrayList;

public class LoginFragment extends Fragment {
    private View view;

    private UserViewModel userViewModel;
    MloginfragmentBinding binding;
    private static final String TAG = "LoginFragment";
    FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        userViewModel  = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getFirebaseUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser !=null){
                    // got to maibn
                    db.collection("User")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        ArrayList<DocumentSnapshot> snapshots = (ArrayList<DocumentSnapshot>) task.getResult().getDocuments();
                                        for (int i = 0; i < snapshots.size(); i++) {
                                            String whichUser = snapshots.get(i).getString("confirmPhoneNumber");
                                            if(whichUser.equals("P")){
                                                Intent intent = new Intent(getActivity(), ControllerActivity.class);
                                                startActivity(intent);
                                            }else {
                                                Intent intent = new Intent(getActivity(), DoctorActivites.class);
                                                startActivity(intent);
                                            }


                                            //  SeisureModel seisureModel = new SeisureModel(snapshots.get(i).get("date"));
                                        }


                                    }

                                }
                            });


                }else {
                    Sneaker.with(getActivity()) // Activity, Fragment or ViewGroup
                            .setTitle("Error")
                            .setMessage("Something went wrong")
                            .sneakError();

                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MloginfragmentBinding.inflate(inflater, container,false);
        view = binding.getRoot();
        binding.LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:  I am clicked ");
                userViewModel.UserLogin(binding.EmailAddress.getText().toString(), binding.Passwords.getText().toString());
            }
        });





        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view1);
        binding.AlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.startFragment);
            }
        });
    }
}
