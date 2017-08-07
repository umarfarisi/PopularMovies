package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.adapter.MoviesAdapter;
import com.example.myapplication.adapter.listener.BaseListener;
import com.example.myapplication.api.core.ApiHelper;
import com.example.myapplication.api.core.ApiRequest;
import com.example.myapplication.api.core.ApiRequestQueue;
import com.example.myapplication.api.response.movies.GettingMoviesResponse;
import com.example.myapplication.api.result.MoviesResult;
import com.example.myapplication.api.result.UnauthorizedResult;
import com.example.myapplication.api.service.MoviesService;
import com.example.myapplication.data.database.contract.PopularMovieContract;
import com.example.myapplication.model.Movie;
import com.example.myapplication.receiver.event.ConnectivityChangedEvent;
import com.example.myapplication.utils.ApiKeyUtils;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.NetworkUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

@SuppressWarnings("ALL")
public class MainActivity extends BaseActivity {

    @BindView(R.id.tb_main_toolbar)
    Toolbar mainToolbarTB;
    @BindView(R.id.rl_main_root)
    RelativeLayout mainRootRL;
    @BindView(R.id.pb_main_progress_sign)
    ProgressBar mainProgressSignPB;
    @BindView(R.id.tv_main_empty_text)
    TextView mainEmptyTextTV;
    @BindView(R.id.rv_main_popular_movies_list)
    RecyclerView mainPopularMoviesListRV;

    private Snackbar networkDisconnectingSignS;

    private Unbinder unbinder;

    private MoviesAdapter adapter;

    private Movie detailMovie;

    private final int SORTED_BY_POPULAR = 0;
    private final int SORTED_BY_TOP = 1;
    private final int SORTED_BY_FAVORITE = 2;

    private int sortedByState;

    private BaseListener<Movie> listener = new BaseListener<Movie>() {
        @Override
        public void onItemClick(Movie movie) {
            detailMovie = movie;
            Intent intent = new Intent(MainActivity.this,MovieDetailActivity.class);
            intent.putExtra(Constants.MOVIE_EXTRA,movie);
            MainActivity.this.startActivity(intent);
        }
    };

    private ArrayList<Integer> favoriteMovieIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mainToolbarTB);

        detailMovie = null;

        networkDisconnectingSignS = Snackbar.make(mainRootRL,R.string.main_network_is_disconnection,Snackbar.LENGTH_INDEFINITE);

        //set up mainPopularListRV
        adapter = new MoviesAdapter(listener);
        mainPopularMoviesListRV.setLayoutManager(new GridLayoutManager(this, Constants.MOVIES_GRID_COLUMN));
        mainPopularMoviesListRV.setAdapter(adapter);

        if(savedInstanceState == null) {

            mainProgressSignPB.setVisibility(View.VISIBLE);

            //request data from server
            ApiRequest<GettingMoviesResponse> apiRequest = new ApiRequest<>(
                    ApiHelper.service(MoviesService.class).getMovies(MoviesService.ORDER_BY_POPULAR,ApiKeyUtils.API_KEY_V3),
                    new MoviesResult.GettingMoviesResult(),
                    true
            );
            ApiRequestQueue.get().addRequestApi(apiRequest);
            ApiRequestQueue.get().requestAllRequestedApi();

            //get favorite id
            favoriteMovieIds = new ArrayList<>();
            Cursor cursor = getContentResolver().query(PopularMovieContract.CONTENT_URI,new String[]{PopularMovieContract.MovieEntry._ID},null,null,null);
            int idIndex = cursor.getColumnIndex(PopularMovieContract.MovieEntry._ID);
            while (cursor.moveToNext())
                favoriteMovieIds.add(cursor.getInt(idIndex));
            cursor.close();

            sortedByState = SORTED_BY_POPULAR;

        }else{
            //noinspection unchecked
            sortedByState = savedInstanceState.getInt(Constants.STATE_SORTED_BY);
            favoriteMovieIds = savedInstanceState.getIntegerArrayList(Constants.STATE_FAVORITE_MOVIE_IDS);
            ArrayList<Movie> movies = savedInstanceState.<Movie>getParcelableArrayList(Constants.STATE_MOVIES);
            markFavoriteMovie(movies);
            adapter.addAll(movies);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(detailMovie != null){
            if(sortedByState == SORTED_BY_FAVORITE){
                adapter.remove(detailMovie);
                renderEmptySign();
            }else{
                Cursor cursor = getContentResolver().query(PopularMovieContract.CONTENT_URI
                        , new String[]{PopularMovieContract.MovieEntry._ID}
                        , PopularMovieContract.MovieEntry._ID + " = ?"
                        , new String[]{String.valueOf(detailMovie.getId())}
                        , null);
                if (cursor.moveToNext()) {
                    favoriteMovieIds.add(detailMovie.getId());
                    detailMovie.setIsFavorite(Movie.FAVORITE);
                }
                cursor.close();

            }
            detailMovie = null;
        }
    }

    private void markFavoriteMovie(ArrayList<Movie> movies){
        for(int favoriteMovieId : favoriteMovieIds){
            Movie dummy = new Movie();
            dummy.setId(favoriteMovieId);
            int indexOfFavoriteMovie = movies.indexOf(dummy);
            if(indexOfFavoriteMovie != -1){
                movies.get(indexOfFavoriteMovie).setIsFavorite(Movie.FAVORITE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(NetworkUtils.isNetworkConnected()) {
            Call<GettingMoviesResponse> service = null;
            switch (item.getItemId()) {
                case R.id.menu_main_sort_by_most_popular:
                    sortedByState = SORTED_BY_POPULAR;
                    service = ApiHelper.service(MoviesService.class).getMovies(MoviesService.ORDER_BY_POPULAR , ApiKeyUtils.API_KEY_V3);
                    break;
                case R.id.menu_main_sort_by_top_rated:
                    sortedByState = SORTED_BY_TOP;
                    service = ApiHelper.service(MoviesService.class).getMovies(MoviesService.ORDER_BY_TOP_RATED , ApiKeyUtils.API_KEY_V3);
                    break;
                case R.id.menu_main_sort_by_favorite:
                    sortedByState = SORTED_BY_FAVORITE;
                    mainProgressSignPB.setVisibility(View.VISIBLE);
                    Cursor cursor = getContentResolver().query(PopularMovieContract.CONTENT_URI,null,null,null,null);
                    ArrayList<Movie> movies = new ArrayList<>();
                    int idIndex = cursor.getColumnIndex(PopularMovieContract.MovieEntry._ID);
                    int titleIndex = cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_TITLE);
                    int posterPathIndex = cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_POSTER_PATH);
                    int thumbnailPathIndex = cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_THUMBNAIL_PATH);
                    int synopsisIndex = cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_SYNOPSIS);
                    int userRatingIndex = cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_USER_RATING);
                    int releaseDateIndex = cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_RELEASE_DATE);
                    while(cursor.moveToNext()){
                        Movie movie = new Movie(cursor.getInt(idIndex)
                                ,cursor.getString(titleIndex)
                                ,cursor.getString(posterPathIndex)
                                ,cursor.getString(thumbnailPathIndex)
                                ,cursor.getString(synopsisIndex)
                                ,cursor.getDouble(userRatingIndex)
                                ,new Date(cursor.getLong(releaseDateIndex)),Movie.FAVORITE);
                        movies.add(movie);
                    }
                    loadPopularMovies(movies);
                    cursor.close();
                    break;

            }
            if(service != null) {
                ApiRequestQueue.get().addRequestApi(new ApiRequest<>(service, new MoviesResult.GettingMoviesResult(), true));
                ApiRequestQueue.get().requestAllRequestedApi();
                mainProgressSignPB.setVisibility(View.VISIBLE);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onLoadPopularMovies(MoviesResult.GettingMoviesResult result){
        if(result.getResponse() != null)
            loadPopularMovies(result.getResponse().getMovies());
    }

    private void loadPopularMovies(ArrayList<Movie> movies){
        adapter.removeAll();

        if(movies != null && !movies.isEmpty()) {
            markFavoriteMovie(movies);
            adapter.addAll(movies);
        }

        renderEmptySign();
        mainEmptyTextTV.setText(getString(R.string.main_empty_text));
        mainProgressSignPB.setVisibility(View.GONE);
    }

    @Subscribe
    public void onConnectivityChange(ConnectivityChangedEvent event) {
        if(event.isConnected()){
            ApiRequestQueue.get().requestAllRequestedApi();
            if(adapter.getItemCount() == 0){
                mainProgressSignPB.setVisibility(View.VISIBLE);
            }
            networkDisconnectingSignS.dismiss();
        }else{
            mainProgressSignPB.setVisibility(View.GONE);
            networkDisconnectingSignS.show();
        }
    }

    @Subscribe
    public void onUnauthorized(UnauthorizedResult result){
        renderEmptySign();
        mainEmptyTextTV.setText(getString(R.string.all_unauthorized_text));
        mainProgressSignPB.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.STATE_MOVIES,  adapter.getElements());
        outState.putIntegerArrayList(Constants.STATE_FAVORITE_MOVIE_IDS,favoriteMovieIds);
        outState.putInt(Constants.STATE_SORTED_BY,sortedByState);
    }

    private void renderEmptySign(){
        if(adapter.getItemCount() == 0)mainEmptyTextTV.setVisibility(View.VISIBLE);
        else mainEmptyTextTV.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
