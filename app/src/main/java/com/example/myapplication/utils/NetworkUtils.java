package com.example.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
public class NetworkUtils {

    public static boolean isNetworkConnected(){
        if (GlobalVariable.APP_CONTEXT != null) {
            ConnectivityManager manager = (ConnectivityManager) GlobalVariable.APP_CONTEXT.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            return (activeNetwork != null) && activeNetwork.isConnected();
        }
        return false;
    }

}
