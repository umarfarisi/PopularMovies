package com.example.myapplication.receiver.event;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
@SuppressWarnings("ALL")
public class ConnectivityChangedEvent {

    private final boolean isConnected;

    public ConnectivityChangedEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
