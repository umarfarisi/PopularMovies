package com.example.myapplication.api.service;

import com.example.myapplication.api.response.PopularMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
@SuppressWarnings("ALL")
public interface PopularMoviesService {

    @GET("movie/popular")
    Call<PopularMoviesResponse> getAllMostPopularMovie(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<PopularMoviesResponse> getAllTopRatedMovie(@Query("api_key") String apiKey);

}
