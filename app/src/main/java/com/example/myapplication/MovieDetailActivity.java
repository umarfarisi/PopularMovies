package com.example.myapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.data.database.contract.PopularMovieContract;
import com.example.myapplication.model.Movie;
import com.example.myapplication.receiver.event.ConnectivityChangedEvent;
import com.example.myapplication.utils.ApiUtils;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.DateUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;

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
    @BindView(R.id.btn_movie_detail_favorite)
    Button movieDetailFavoriteBtn;

    Unbinder unbinder;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        unbinder = ButterKnife.bind(this);

        setSupportActionBar(movieDetailToolbarTB);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movie = (Movie) getIntent().getParcelableExtra(Constants.MOVIE_EXTRA);

        if(movie != null) {

            movieDetailToolbarContainerCB.setTitle(movie.getTitle());

            Picasso.with(this).load(ApiUtils.IMG_BASE_URL + movie.getThumbnailPath()).into(movieDetailPosterIV);
            movieDetailSynopsisTV.setText(movie.getSynopsis());
            movieDetailUserRatingTV.setText(getString(R.string.movie_detail_rating_text));
            movieDetailUserRatingTV.append(String.valueOf(movie.getUserRating()));
            movieDetailReleaseDateTV.setText(getString(R.string.movie_detail_release_date_text));
            movieDetailReleaseDateTV.append(DateUtils.datePosting(movie.getReleaseDate()));
            if(movie.getIsFavorite() != null && movie.getIsFavorite().equals(Movie.FAVORITE)){
                movieDetailFavoriteBtn.setText(Movie.UNFAVORITE);
            }else{
                movieDetailFavoriteBtn.setText(Movie.FAVORITE);
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

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }


    public void onClickFavoriteButton(View view) {
        ContentResolver resolver = getContentResolver();
        if(movie.getIsFavorite() == null || movie.getIsFavorite().equals(Movie.FAVORITE)){
            ContentValues values = new ContentValues();
            values.put(PopularMovieContract.MovieEntry._ID,movie.getId());
            values.put(PopularMovieContract.MovieEntry.COLUMN_TITLE,movie.getTitle());
            values.put(PopularMovieContract.MovieEntry.COLUMN_POSTER_PATH , movie.getPosterPath());
            values.put(PopularMovieContract.MovieEntry.COLUMN_THUMBNAIL_PATH, movie.getThumbnailPath());
            values.put(PopularMovieContract.MovieEntry.COLUMN_SYNOPSIS, movie.getSynopsis());
            values.put(PopularMovieContract.MovieEntry.COLUMN_USER_RATING, movie.getUserRating());
            values.put(PopularMovieContract.MovieEntry.COLUMN_RELEASE_DATE , movie.getReleaseDate().getTime());
            values.put(PopularMovieContract.MovieEntry.COLUMN_FAVORITE, Movie.FAVORITE);
            Uri uri = resolver.insert(PopularMovieContract.CONTENT_URI,values);
            if(uri != null) {
                movie.setIsFavorite(Movie.FAVORITE);
                movieDetailFavoriteBtn.setText(Movie.UNFAVORITE);
            }
        }else{
            int rowCount = resolver.delete(PopularMovieContract.CONTENT_URI,PopularMovieContract.MovieEntry._ID + " = ?",new String[]{String.valueOf(movie.getId())});
            if(rowCount > 0) {
                movie.setIsFavorite(Movie.UNFAVORITE);
                movieDetailFavoriteBtn.setText(Movie.FAVORITE);
            }
        }
    }

    public void onClickVideoButton(View view) {
        Intent intent = new Intent(this,VideosActivity.class);
        intent.putExtra(Constants.MOVIE_EXTRA,movie);
        startActivity(intent);
    }

    public void onClickReviewButton(View view) {
        Intent intent = new Intent(this,ReviewsActivity.class);
        intent.putExtra(Constants.MOVIE_EXTRA,movie);
        startActivity(intent);
    }
}
