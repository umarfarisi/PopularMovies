package com.example.myapplication.api.result;

import com.example.myapplication.api.event.LoadingPopularMoviesEvent;
import com.example.myapplication.api.response.PopularMoviesResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
@SuppressWarnings("ALL")
public class PopularMoviesResult extends BaseResult<PopularMoviesResponse> {

    public PopularMoviesResult(boolean isTryingToRequestAgainIfConnectionIsLost, int apiRequestPriority) {
        super(isTryingToRequestAgainIfConnectionIsLost, apiRequestPriority);
    }

    @Override
    public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
        if(response.isSuccessful()){
            PopularMoviesResponse body = response.body();
            if(body != null) {
                EventBus.getDefault().post(new LoadingPopularMoviesEvent(body.getPage(),body.getTotalPages(),body.getMovies()));
            }
        }
    }

}
