package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.adapter.ReviewsAdapter;
import com.example.myapplication.api.core.ApiHelper;
import com.example.myapplication.api.core.ApiRequest;
import com.example.myapplication.api.core.ApiRequestQueue;
import com.example.myapplication.api.result.MovieDetailResult;
import com.example.myapplication.api.service.MovieDetailService;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.Review;
import com.example.myapplication.receiver.event.ConnectivityChangedEvent;
import com.example.myapplication.utils.ApiKeyUtils;
import com.example.myapplication.utils.Constants;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReviewsActivity extends BaseActivity {

    @BindView(R.id.tb_reviews_toolbar)
    Toolbar reviewsToolbarTB;
    @BindView(R.id.pb_reviews_progress_sign)
    ProgressBar reviewsProgressSignPB;
    @BindView(R.id.rv_reviews_content)
    RecyclerView reviewsContentRV;
    @BindView(R.id.tv_reviews_text_sign)
    TextView reviewsTextSignTV;

    private ReviewsAdapter reviewsAdapter;
    private Unbinder unbinder;

    private boolean isRequestingApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(reviewsToolbarTB);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.reviews_title));

        reviewsAdapter = new ReviewsAdapter();

        reviewsContentRV.setLayoutManager(new LinearLayoutManager(this));
        reviewsContentRV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        reviewsContentRV.setAdapter(reviewsAdapter);

        Movie movie = getIntent().getParcelableExtra(Constants.MOVIE_EXTRA);

        if(movie != null){
            getSupportActionBar().setSubtitle(movie.getTitle());
            if(savedInstanceState == null){
                isRequestingApi = true;
                reviewsProgressSignPB.setVisibility(View.VISIBLE);
                ApiRequestQueue.get().addRequestApi(new ApiRequest<>(
                        ApiHelper.service(MovieDetailService.class).getReviews(movie.getId(), ApiKeyUtils.API_KEY_V3),
                        new MovieDetailResult.GettingReviewsResult(), true
                ));
                ApiRequestQueue.get().requestAllRequestedApi();
            }else{
                reviewsAdapter.addAll(savedInstanceState.<Review>getParcelableArrayList(Constants.STATE_REVIEWS));
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onConnectivityChange(ConnectivityChangedEvent event) {
        if(event.isConnected()){
            ApiRequestQueue.get().requestAllRequestedApi();
            if(isRequestingApi){
                reviewsProgressSignPB.setVisibility(View.VISIBLE);
            }else{
                reviewsProgressSignPB.setVisibility(View.GONE);
            }
        }else{
            reviewsProgressSignPB.setVisibility(View.GONE);
            if(reviewsAdapter.isEmpty()){
                reviewsTextSignTV.setVisibility(View.VISIBLE);
                reviewsTextSignTV.setText(getString(R.string.all_connection_lost));
            }else{
                reviewsTextSignTV.setVisibility(View.GONE);
            }
        }
    }

    @Subscribe
    public void onLoadReview(MovieDetailResult.GettingReviewsResult result){
        isRequestingApi = false;
        reviewsProgressSignPB.setVisibility(View.GONE);
        reviewsAdapter.removeAll();
        reviewsAdapter.addAll(result.getResponse().getResults());
        if(reviewsAdapter.isEmpty()){
            reviewsTextSignTV.setVisibility(View.VISIBLE);
            reviewsTextSignTV.setText(getString(R.string.reviews_empty_text));
        }else{
            reviewsTextSignTV.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.STATE_REVIEWS,reviewsAdapter.getElements());
    }
}
