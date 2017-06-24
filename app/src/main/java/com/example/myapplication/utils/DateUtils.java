package com.example.myapplication.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
public class DateUtils {

    @SuppressLint("SimpleDateFormat")
    public static String datePosting(Date date){
        return new SimpleDateFormat("d MMM yyyy").format(date);
    }

}
