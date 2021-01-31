package com.example.osamah.model;

import java.util.HashMap;

public class UserActivites {
    private HashMap<String, Object> mTriggers;


    public UserActivites(HashMap<String, Object> mTriggers) {
        this.mTriggers = mTriggers;

    }

    public UserActivites() {
    }

    public HashMap<String, Object> getmTriggers() {
        return mTriggers;
    }

    public void setmTriggers(HashMap<String, Object> mTriggers) {
        this.mTriggers = mTriggers;
    }




}

