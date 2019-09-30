package com.homework.homework.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static long convertDateStringToUnixTimeStamp(String dateString) {
        try {
            String pattern = "yyyy";
            if (dateString.contains("-")) pattern = "yyyy-MM-dd";
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            Date date = dateFormat.parse(dateString);
            return  date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
