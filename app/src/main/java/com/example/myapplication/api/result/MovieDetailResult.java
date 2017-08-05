package com.example.myapplication.api.result;

import com.example.myapplication.api.response.detail.movie.GettingReviewsResponse;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public interface MovieDetailResult {
    class GettingVideosResult extends BaseResult<GettingVideosResult>{}
    class GettingReviewsResult extends BaseResult<GettingReviewsResponse>{}
}
