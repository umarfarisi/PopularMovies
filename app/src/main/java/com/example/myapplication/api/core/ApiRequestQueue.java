package com.example.myapplication.api.core;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
@SuppressWarnings("ALL")
public class ApiRequestQueue {

    private Queue<ApiRequest> requestApis;
    private int highestPriority;

    private static ApiRequestQueue instance;

    private ApiRequestQueue(){
        //nothing
        requestApis = new PriorityQueue<>();
    }

    public static ApiRequestQueue get(){
        if(instance == null){
            instance = new ApiRequestQueue();
        }
        return instance;
    }

    public <T> void addRequestApi(ApiRequest<T> api){
        if (!requestApis.contains(api)){
            requestApis.add(api);
            if(highestPriority < api.getPriority())highestPriority = api.getPriority();
        }
    }

    public <T> void removeRequestApi(ApiRequest<T> api){
        requestApis.remove(api);
    }

    public void removeAllRequestApi(){
        requestApis.clear();
    }

    public void requestAllRequestedApi(){
        while(!requestApis.isEmpty()) {
            ApiRequest requestApi = requestApis.poll();
            //noinspection unchecked
            requestApi.getRequest().enqueue(requestApi.getCallback());
        }
    }

    public int requestApiCount(){
        return requestApis.size();
    }

    public int getHighestPriority() {
        return highestPriority;
    }

}
