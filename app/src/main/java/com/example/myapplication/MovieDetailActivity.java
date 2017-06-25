package com.example.myapplication;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.model.Movie;
import com.example.myapplication.utils.ApiUtils;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.DateUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressWarnings("ALL")
public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.tb_movie_detail_toolbar)
    Toolbar movieDetailToolbarTB;
    @BindView(R.id.cb_movie_detail_toolbar_container)
    CollapsingToolbarLayout movieDetailToolbarContainerCB;
    @BindView(R.id.im_movie_detail_poster)
    ImageView movieDetailPosterIV;
    @BindView(R.id.tv_movie_detail_synopsis)
    TextView movieDetailSynopsisTV;
    @BindView(R.id.tv_movie_detail_user_rating)
    TextView movieDetailUserRatingTV;
    @BindView(R.id.tv_movie_detail_release_date)
    TextView movieDetailReleaseDateTV;

    Unbinder unbinder;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(movieDetailToolbarTB);

        movie = (Movie) getIntent().getParcelableExtra(Constants.MOVIE);

        if(movie != null) {

            movieDetailToolbarContainerCB.setTitle(movie.getTitle());
            movieDetailToolbarContainerCB.setExpandedTitleColor(android.R.color.transparent);

            Picasso.with(this).load(ApiUtils.IMG_BASE_URL + movie.getThumbnailPath()).into(movieDetailPosterIV);
            movieDetailSynopsisTV.setText(movie.getSynopsis());
            movieDetailUserRatingTV.setText(getString(R.string.movie_detail_rating_text));
            movieDetailUserRatingTV.append(String.valueOf(movie.getUserRating()));
            movieDetailReleaseDateTV.setText(getString(R.string.movie_detail_release_date_text));
            movieDetailReleaseDateTV.append(DateUtils.datePosting(movie.getReleaseDate()));
        }

    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
