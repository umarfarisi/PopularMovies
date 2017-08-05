package com.example.myapplication.api.service;

import com.example.myapplication.api.result.MovieDetailResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public interface MovieDetailService {

    @GET("movie/{id}/videos")
    Call<MovieDetailResult.GettingVideosResult> getVideos(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<MovieDetailResult.GettingReviewsResult> getReviews(@Path("id") String id, @Query("api_key") String apiKey);

}
