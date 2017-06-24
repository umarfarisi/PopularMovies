package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.utils.GlobalVariable;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
public class PopularMoviesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalVariable.APP_CONTEXT = getApplicationContext();
    }
}
