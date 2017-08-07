package com.example.myapplication;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.receiver.ConnectivityChangeReceiver;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public class BaseActivity extends AppCompatActivity {

    private ConnectivityChangeReceiver connectivityChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectivityChangeReceiver = new ConnectivityChangeReceiver();
        registerReceiver(connectivityChangeReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(connectivityChangeReceiver);
        super.onDestroy();
    }
}
