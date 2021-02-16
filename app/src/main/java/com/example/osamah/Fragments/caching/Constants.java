package com.example.osamah.Fragments.caching;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getCurrentDate () throws ParseException {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("dd-MM-yyy ");
        Date myDate = new Date();
        String getCurrentDateDate= timeStampFormat.format(myDate);
        return  getCurrentDateDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTimeIn12Hours (String hour) throws ParseException {
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mma");
        Date _24HourDt = _24HourSDF.parse(hour);
        return  _12HourSDF.format(_24HourDt);
    }

}
