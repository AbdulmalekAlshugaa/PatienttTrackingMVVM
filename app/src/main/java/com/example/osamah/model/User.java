package com.example.osamah.model;

import android.util.Patterns;

public class User {
    private String fullName;
    private String email;
    private String password;
    private String contactNumber, confirmPhoneNumber;
    private String Uid;

    public User() {
    }

    public User(String fullName, String email, String password, String contactNumber, String confirmPhoneNumber, String udi) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.confirmPhoneNumber = confirmPhoneNumber;
        this.Uid = udi;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getConfirmPhoneNumber() {
        return confirmPhoneNumber;
    }

    public void setConfirmPhoneNumber(String confirmPhoneNumber) {
        this.confirmPhoneNumber = confirmPhoneNumber;
    }

    //
    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }

    public boolean isPasswordLengthGreaterThan5() {
        return getPassword().length() > 5;
    }

    public boolean isPasswordMatchAnotherPassword(String password1, String password) {
        if(password.equals(password1)){
            return true;
        }else {
            return false;
        }


    }


}
