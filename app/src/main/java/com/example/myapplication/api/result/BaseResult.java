package com.example.myapplication.api.result;

import com.example.myapplication.api.core.ApiRequest;
import com.example.myapplication.api.core.ApiRequestQueue;
import com.example.myapplication.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author Muhammad Umar Farisi
 * @created 23/06/2017
 */
public abstract class BaseResult<T> implements Callback<T> {

    private boolean isTryingToRequestAgainIfConnectionIsLost;
    private int apiRequestPriority;

    public BaseResult(boolean isTryingToRequestAgainIfConnectionIsLost, int apiRequestPriority) {
        this.isTryingToRequestAgainIfConnectionIsLost = isTryingToRequestAgainIfConnectionIsLost;
        this.apiRequestPriority = apiRequestPriority;
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        boolean isFailedToRequest = !NetworkUtils.isNetworkConnected();
        if(isFailedToRequest && isTryingToRequestAgainIfConnectionIsLost){
            ApiRequestQueue.get().addRequestApi(new ApiRequest<>(call.clone(),this, apiRequestPriority));
        }
    }
}
