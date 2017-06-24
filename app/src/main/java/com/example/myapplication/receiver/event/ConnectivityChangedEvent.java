package com.example.myapplication.receiver.event;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
public class ConnectivityChangedEvent {

    private boolean isConnected;

    public ConnectivityChangedEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
