package com.example.osamah.Fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.osamah.R;
import com.example.osamah.databinding.FragmentSignupBinding;
import com.example.osamah.model.User;
import com.example.osamah.view.ControllerActivity;
import com.example.osamah.viewModel.UserViewModel;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingUpFragment extends Fragment {

    private View view;
    FragmentSignupBinding binding;
    private UserViewModel userViewModel;
    private static final String TAG = "LoginFragment";
    public SingUpFragment() {


        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // user view model

        userViewModel  = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getFirebaseUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    User user = new User("Abdulmalik ALshugaa",binding.EmailAddressSigup.getText().toString(),binding.PasswordCreate.getText().toString()
                            ,"Ahmed","Ahme");
                    userViewModel.addUserdetails(user);
                }else {
                    Log.d(TAG, "onChanged: Error ");
                }
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        view = binding.getRoot();



        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);
        final NavController navController = Navigation.findNavController(view1);
        userViewModel.getUserMutableLiveData().observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user.getFullName() !=null){
                    // replace fragment
                    Intent intent = new Intent(getActivity(), ControllerActivity.class);
                    startActivity(intent);


                }
            }
        });
        binding.SignUp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                // pass params
                User user = new User("Abdulmalik ALshugaa",binding.EmailAddressSigup.getText().toString(),binding.PasswordCreate.getText().toString()
                        ,"Ahmed","Ahme");
                userViewModel.register(user);


            }
        });
        binding.AlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                navController.navigate(R.id.loginFragment2);
            }
        });
    }
}

