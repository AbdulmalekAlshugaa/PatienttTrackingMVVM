package com.example.osamah.Fragments.caching;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;


public class SingealtonLocalData {
    private static SingealtonLocalData mInstance;
    private Context mContext;
    private static final String SHARED_PREF_NAME = "my_shared_preff";
    private static final String triggersName = "TriggersNumber";
    private static final String TAG = "SingealtonLocalData";


    private SingealtonLocalData(Context mContext)
    {
        this.mContext = mContext;

    }
    public static synchronized SingealtonLocalData getInstance(Context context){
        if (mInstance == null){
            mInstance = new SingealtonLocalData(context);
        }
        return mInstance;
    }

    public void SaveUserLocalData(UserPerf localUsers){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(localUsers);
        editor.putString("USERDATA",serializedObject);
        editor.apply();
    }
    public void SaveTriggers(int triggers){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(triggersName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Triggers", triggers);
        editor.apply();

    }
    public void SaveTriggers2(int triggers){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(triggersName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Triggers", triggers);
        editor.apply();

    }
    public void SaveTriggers3(int triggers){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(triggersName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Triggers", triggers);
        editor.apply();

    }
    public int getTrigger(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(triggersName, Context.MODE_PRIVATE);
        int json = sharedPreferences.getInt("Triggers", 0);
        return json;
    }
    public int getTrigger2(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(triggersName, Context.MODE_PRIVATE);
        int json = sharedPreferences.getInt("Triggers", 0);
        return json;
    }
    public int getTrigger3(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(triggersName, Context.MODE_PRIVATE);
        int json = sharedPreferences.getInt("Triggers", 0);
        return json;
    }



    public UserPerf getLocalUserData(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("USERDATA", null);
        UserPerf obj = gson.fromJson(json, UserPerf.class);

        return obj;
    }

}
