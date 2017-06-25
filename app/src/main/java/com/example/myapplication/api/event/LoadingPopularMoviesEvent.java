package com.example.myapplication.api.event;

import com.example.myapplication.model.Movie;

import java.util.ArrayList;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
@SuppressWarnings("ALL")
public class LoadingPopularMoviesEvent {

    private int currentPage;
    private int totalPage;
    private ArrayList<Movie> movies;

    public LoadingPopularMoviesEvent(int currentPage, int totalPage, ArrayList<Movie> movies) {
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

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
