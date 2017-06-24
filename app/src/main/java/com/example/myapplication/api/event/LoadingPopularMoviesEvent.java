package com.example.myapplication.api.event;

import com.example.myapplication.model.Movie;

import java.util.List;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
public class LoadingPopularMoviesEvent {

    private int currentPage;
    private int totalPage;
    private List<Movie> movies;

    public LoadingPopularMoviesEvent(int currentPage, int totalPage, List<Movie> movies) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.movies = movies;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
