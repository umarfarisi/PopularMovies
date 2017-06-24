package com.example.myapplication.api.result;

import android.util.Log;

import com.example.myapplication.api.event.LoadingPopularMoviesEvent;
import com.example.myapplication.api.response.PopularMoviesResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
public class PopularMoviesResult extends BaseResult<PopularMoviesResponse> {

    public PopularMoviesResult(boolean isTryingToRequestAgainIfConnectionIsLost, int apiRequestPriority) {
        super(isTryingToRequestAgainIfConnectionIsLost, apiRequestPriority);
    }

    @Override
    public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
        Log.d("TESTTTT","onResponse, isSuccessful: "+response.isSuccessful());
        if(response.errorBody() != null){
            Log.d("TESTTTT","onResponse, errorBody:: "+response.errorBody());
        }
        if(response.isSuccessful()){
            PopularMoviesResponse body = response.body();
            EventBus.getDefault().post(new LoadingPopularMoviesEvent(body.getPage(),body.getTotalPages(),body.getMovies()));
        }
    }

}
