package com.example.myapplication.api.service;

import com.example.myapplication.api.response.detail.movie.GettingReviewsResponse;
import com.example.myapplication.api.response.detail.movie.GettingVideosResponse;

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
    Call<GettingVideosResponse> getVideos(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<GettingReviewsResponse> getReviews(@Path("id") int id, @Query("api_key") String apiKey);

}
