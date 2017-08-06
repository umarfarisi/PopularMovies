package com.example.myapplication.api.response.detail.movie;

import com.example.myapplication.model.Review;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public class GettingReviewsResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private ArrayList<Review> results;
    @SerializedName("total_pages")
    private int totalPage;
    @SerializedName("total_results")
    private int totalResults;

    public GettingReviewsResponse(){

    }

    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public ArrayList<Review> getResults() {
        return results;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
