package com.example.myapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
public class DateUtils {

    public static String datePosting(Date date){
        return new SimpleDateFormat("d MMMM yyyy").format(date);
    }

}
