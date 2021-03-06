package com.example.osamah.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.AEADBadTagException;

public class SeisureModel {
    private String seizureImage;
    private String date,time,seizureLen,trigger,activity,location, note;
    private Integer mActivity,mLocaiton;
    HashMap<String, Object> triggersList;
    private static  ArrayList<SeisureModel> seisureModelArrayList = new ArrayList<>();
    private String UID;

    



    public SeisureModel(String seizureImage, String date, String time, String seizureLen, String trigger,
                        String activity, String location, String note, String UID, HashMap<String, Object> triggersList, Integer mActivity, Integer mLocaiton) {
        this.seizureImage = seizureImage;
        this.date = date;
        this.time = time;
        this.seizureLen = seizureLen;
        this.trigger = trigger;
        this.activity = activity;
        this.location = location;
        this.note = note;
        this.UID = UID;
        this.triggersList = triggersList;
        this.mActivity = mActivity;
        this.mLocaiton = mLocaiton;

    }

    public HashMap<String, Object> getTriggersList() {
        return triggersList;
    }

    public void setTriggersList(HashMap<String, Object> triggersList) {
        this.triggersList = triggersList;
    }

    public Integer getmActivity() {
        return mActivity;
    }

    public void setmActivity(Integer mActivity) {
        this.mActivity = mActivity;
    }

    public Integer getmLocaiton() {
        return mLocaiton;
    }

    public void setmLocaiton(Integer mLocaiton) {
        this.mLocaiton = mLocaiton;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public static ArrayList<SeisureModel> getSeisureModelArrayList() {
        return seisureModelArrayList;
    }

    public static void setSeisureModelArrayList(ArrayList<SeisureModel> seisureModelArrayList) {
        SeisureModel.seisureModelArrayList = seisureModelArrayList;
    }

    public SeisureModel() {
    }

    public String getSeizureImage() {
        return seizureImage;
    }

    public void setSeizureImage(String seizureImage) {
        this.seizureImage = seizureImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeizureLen() {
        return seizureLen;
    }

    public void setSeizureLen(String seizureLen) {
        this.seizureLen = seizureLen;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
