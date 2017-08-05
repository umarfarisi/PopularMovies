package com.example.myapplication.api.service;

import com.example.myapplication.api.response.GettingMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
@SuppressWarnings("ALL")
public interface MoviesService {

    public static final String ORDER_BY_POPULAR = "popular";
    public static final String ORDER_BY_TOP_RATED = "top_rated";

    @GET("movie/{sort}")
    Call<GettingMoviesResponse> getMovies(@Path("sort") String order, @Query("api_key") String apiKey);

}
