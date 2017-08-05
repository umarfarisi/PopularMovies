package com.example.myapplication.api.response;

import com.example.myapplication.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
@SuppressWarnings("ALL")
public class GettingMoviesResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private ArrayList<Movie> movies;

    public GettingMoviesResponse(){

    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
