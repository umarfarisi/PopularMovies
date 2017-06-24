package com.example.myapplication.api.response;

import com.example.myapplication.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
public class PopularMoviesResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<Movie> movies;

    public PopularMoviesResponse(){

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

    public List<Movie> getMovies() {
        return movies;
    }
}
