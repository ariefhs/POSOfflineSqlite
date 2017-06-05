package com.lupawktu.possqlite.PublicMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mind on 6/1/2017.
 */

public class GetTimeMiliSecond {

    public String timeMiliSecond(String datetime) {
        long timeInMilliseconds = 0;
        String timemilis = datetime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(timemilis);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(timeInMilliseconds);
    }
}
