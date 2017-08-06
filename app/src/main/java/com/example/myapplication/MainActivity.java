package com.example.myapplication;

import android.content.Intent;
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
import com.example.myapplication.adapter.listener.MoviesListener;
import com.example.myapplication.api.core.ApiHelper;
import com.example.myapplication.api.core.ApiRequest;
import com.example.myapplication.api.core.ApiRequestQueue;
import com.example.myapplication.api.response.movies.GettingMoviesResponse;
import com.example.myapplication.api.result.MoviesResult;
import com.example.myapplication.api.service.MoviesService;
import com.example.myapplication.model.Movie;
import com.example.myapplication.receiver.event.ConnectivityChangedEvent;
import com.example.myapplication.utils.ApiKeyUtils;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.NetworkUtils;

import org.greenrobot.eventbus.Subscribe;

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

    private MoviesListener listener = new MoviesListener() {
        @Override
        public void onPopularMovieClick(Movie movie) {
            Intent intent = new Intent(MainActivity.this,MovieDetailActivity.class);
            intent.putExtra(Constants.MOVIE,movie);
            MainActivity.this.startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(mainToolbarTB);

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

        }else{
            //noinspection unchecked
            adapter.addAll(savedInstanceState.<Movie>getParcelableArrayList(Constants.MOVIES_DATA));
            mainPopularMoviesListRV.setVerticalScrollbarPosition(savedInstanceState.getInt(Constants.MOVIES_LIST_VERTICAL_SCROLLBAR_POSITION));
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
                    service = ApiHelper.service(MoviesService.class).getMovies(MoviesService.ORDER_BY_POPULAR , ApiKeyUtils.API_KEY_V3);
                    break;
                case R.id.menu_main_sort_by_top_rated:
                    service = ApiHelper.service(MoviesService.class).getMovies(MoviesService.ORDER_BY_TOP_RATED , ApiKeyUtils.API_KEY_V3);
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
        if(result.getResponse() != null) {
            adapter.removeAll();
            adapter.addAll(result.getResponse().getMovies());
        }
        if(adapter.getItemCount() == 0)mainEmptyTextTV.setVisibility(View.VISIBLE);
        else mainEmptyTextTV.setVisibility(View.GONE);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.MOVIES_DATA,  adapter.getElements());
        outState.putInt(Constants.MOVIES_LIST_VERTICAL_SCROLLBAR_POSITION,mainPopularMoviesListRV.getVerticalScrollbarPosition());
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
