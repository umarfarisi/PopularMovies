package com.example.myapplication.api.result;

import com.example.myapplication.api.response.detail.movie.GettingReviewsResponse;
import com.example.myapplication.api.response.detail.movie.GettingVideosResponse;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public interface MovieDetailResult {
    class GettingVideosResult extends BaseResult<GettingVideosResponse>{}
    class GettingReviewsResult extends BaseResult<GettingReviewsResponse>{}
}
