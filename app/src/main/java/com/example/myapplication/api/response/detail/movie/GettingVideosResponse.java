package com.example.myapplication.api.response.detail.movie;

import com.example.myapplication.model.Video;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public class GettingVideosResponse {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private ArrayList<Video> results;

    public GettingVideosResponse(){

    }

    public int getId() {
        return id;
    }

    public ArrayList<Video> getResults() {
        return results;
    }
}
