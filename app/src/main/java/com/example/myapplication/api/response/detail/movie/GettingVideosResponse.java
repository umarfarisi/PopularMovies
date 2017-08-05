package com.example.myapplication.api.response.detail.movie;

import com.example.myapplication.model.Video;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public class GettingVideosResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private List<Video> results;

    public GettingVideosResponse(){

    }

    public int getId() {
        return id;
    }

    public List<Video> getResults() {
        return results;
    }
}
