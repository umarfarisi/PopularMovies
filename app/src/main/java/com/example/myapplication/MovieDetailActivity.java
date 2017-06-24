package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MovieDetailActivity extends AppCompatActivity {

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

        movie = (Movie) getIntent().getSerializableExtra(Constants.MOVIE);

        if(movie != null) {
            setTitle(movie.getTitle());
            Picasso.with(this).load(ApiUtils.IMG_BASE_URL + movie.getPosterPath()).into(movieDetailPosterIV);
            movieDetailSynopsisTV.setText(movie.getSynopsis());
            movieDetailUserRatingTV.setText(getString(R.string.movie_detail_rating_text) + movie.getUserRating());
            movieDetailReleaseDateTV.setText(getString(R.string.movie_detail_release_date_text) + DateUtils.datePosting(movie.getReleaseDate()));
        }

    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
