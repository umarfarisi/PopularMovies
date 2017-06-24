package com.example.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.myapplication.receiver.event.ConnectivityChangedEvent;
import com.example.myapplication.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Muhammad Umar Farisi
 * @created 14/06/2017
 */
public class ConnectivityChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().post(new ConnectivityChangedEvent(NetworkUtils.isNetworkConnected()));
    }

}
