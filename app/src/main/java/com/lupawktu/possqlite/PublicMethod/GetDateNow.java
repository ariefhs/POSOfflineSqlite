package com.lupawktu.possqlite.PublicMethod;

import android.annotation.SuppressLint;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class GetDateNow {
    static String dateNow;
    static String dateOnly;
    static String timeOnly;
    static String dateTimespan;
    static String maxTime;

    public static String getDateNow() {
        try{
            Calendar c = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            dateNow = sdf.format(c.getTime());
        }catch(Exception e){
            System.out.println("Cannot Get date now");
        }
        return dateNow;
    }

    public static String getDateOnly(){
        try{
            Calendar c = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            dateOnly = sdf.format(c.getTime());
        }catch (Exception e){
            System.out.println("Cannot get date only");
        }
        return dateOnly;
    }

    public static String getTimeOnly(){
        try{
            Calendar c = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            timeOnly = sdf.format(c.getTime());
        }catch (Exception e){
            System.out.println("Cannot get date only");
        }
        return timeOnly;
    }

    public static String getTimeMaxOnly(){
        try{
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MINUTE, 120);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
            maxTime = sdf.format(c.getTime());
        }catch (Exception e){
            System.out.println("Cannot get date only");
        }
        return maxTime;
    }

    public static String getDateTimespan(){
        try{
            Calendar c = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            dateNow = sdf.format(c.getTime());
        }catch (Exception e){
            System.out.println("Cannot get date only");
        }
        return dateTimespan;
    }
}
