package com.example.myapplication.api.core;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.myapplication.api.result.BaseResult;
import com.example.myapplication.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
@SuppressWarnings("ALL")
public class ApiRequest <T> implements Comparable<ApiRequest<T>> {

    private Call<T> request;
    private Callback<T> callback;
    private int priority;

    public ApiRequest(Call<T> request, final BaseResult<T> result, final boolean isTryingToRequestAgainIfConnectionIsLost, final int priority){
        this.priority = priority;
        this.request = request;
        this.callback = new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                Log.d("buggggg","onResponse, isSuccessfull: "+response.isSuccessful());
                Log.d("buggggg","onResponse, message: "+response.message());
                Log.d("buggggg","onResponse, code: "+response.code());
                result.setResponse(response.body());
                EventBus.getDefault().post(result);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.d("buggggg","onFailure, message: "+t.getMessage());
                boolean isFailedToRequest = !NetworkUtils.isNetworkConnected();
                if(isFailedToRequest && isTryingToRequestAgainIfConnectionIsLost){
                    ApiRequestQueue.get().addRequestApi(new ApiRequest<>(call.clone(), result,true, priority));
                }
            }
        };
    }

    public ApiRequest(Call<T> request, final BaseResult<T> result, final boolean isTryingToRequestAgainIfConnectionIsLost){
        this(request,result,isTryingToRequestAgainIfConnectionIsLost,0);
    }

    public Call<T> getRequest() {
        return request;
    }

    public Callback<T> getCallback() {
        return callback;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiRequest<?> that = (ApiRequest<?>) o;


        return !request.request().url().toString().equals(that.request.request().url().toString()) &&
                callback.getClass().getName().equals(that.callback.getClass().getName());

    }

    @Override
    public int hashCode() {
        int result1 = request.request().url().toString().hashCode();
        result1 = 31 * result1 + callback.getClass().getName().hashCode();
        return result1;
    }

    @Override
    public int compareTo(@NonNull ApiRequest<T> o) {
        return o.priority - priority;
    }

}
