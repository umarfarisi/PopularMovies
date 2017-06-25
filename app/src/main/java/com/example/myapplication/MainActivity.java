package com.example.myapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.adapter.PopularMoviesAdapter;
import com.example.myapplication.adapter.listener.PopularMoviesListener;
import com.example.myapplication.api.core.ApiHelper;
import com.example.myapplication.api.core.ApiRequest;
import com.example.myapplication.api.core.ApiRequestQueue;
import com.example.myapplication.api.event.LoadingPopularMoviesEvent;
import com.example.myapplication.api.response.PopularMoviesResponse;
import com.example.myapplication.api.result.PopularMoviesResult;
import com.example.myapplication.api.service.PopularMoviesService;
import com.example.myapplication.model.Movie;
import com.example.myapplication.receiver.ConnectivityChangeReceiver;
import com.example.myapplication.receiver.event.ConnectivityChangedEvent;
import com.example.myapplication.utils.ApiKeyUtils;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

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

    private PopularMoviesAdapter adapter;

    private ConnectivityChangeReceiver connectivityChangeReceiver;

    private PopularMoviesListener listener = new PopularMoviesListener() {
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

        networkDisconnectingSignS = Snackbar.make(mainRootRL,R.string.main_network_is_disconnection,Snackbar.LENGTH_INDEFINITE);

        connectivityChangeReceiver = new ConnectivityChangeReceiver();

        //set up mainPopularListRV
        adapter = new PopularMoviesAdapter(listener);
        mainPopularMoviesListRV.setLayoutManager(new GridLayoutManager(this, Constants.MOVIES_GRID_COLUMN));
        mainPopularMoviesListRV.setAdapter(adapter);

        if(savedInstanceState == null) {

            mainProgressSignPB.setVisibility(View.VISIBLE);

            //request data from server
            ApiRequest<PopularMoviesResponse> apiRequest = new ApiRequest<>(
                    ApiHelper.service(PopularMoviesService.class).getMovies(PopularMoviesService.ORDER_BY_POPULAR,ApiKeyUtils.API_KEY_V3),
                    new PopularMoviesResult(true, 0)
            );
            ApiRequestQueue.get().addRequestApi(apiRequest);
            ApiRequestQueue.get().requestAllRequestedApi();

        }else{
            //noinspection unchecked
            adapter.addAllMovie(savedInstanceState.<Movie>getParcelableArrayList(Constants.MOVIES_DATA));
            mainPopularMoviesListRV.setVerticalScrollbarPosition(savedInstanceState.getInt(Constants.MOVIES_LIST_VERTICAL_SCROLLBAR_POSITION));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(connectivityChangeReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(NetworkUtils.isNetworkConnected()) {
            ApiRequest<PopularMoviesResponse> apiRequest = null;
            switch (item.getItemId()) {
                case R.id.menu_main_sort_by_most_popular:
                    apiRequest = new ApiRequest<>(
                            ApiHelper.service(PopularMoviesService.class).getMovies(PopularMoviesService.ORDER_BY_POPULAR , ApiKeyUtils.API_KEY_V3),
                            new PopularMoviesResult(true, 0)
                    );
                    break;
                case R.id.menu_main_sort_by_top_rated:
                    apiRequest = new ApiRequest<>(
                            ApiHelper.service(PopularMoviesService.class).getMovies(PopularMoviesService.ORDER_BY_TOP_RATED , ApiKeyUtils.API_KEY_V3),
                            new PopularMoviesResult(true, 0)
                    );
                    break;
            }
            if (apiRequest != null) {
                ApiRequestQueue.get().addRequestApi(apiRequest);
                ApiRequestQueue.get().requestAllRequestedApi();
                mainProgressSignPB.setVisibility(View.VISIBLE);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onLoadPopularMovies(LoadingPopularMoviesEvent event){
        adapter.removeAllMovie();
        adapter.addAllMovie(event.getMovies());
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
    protected void onStop() {
        unregisterReceiver(connectivityChangeReceiver);
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.MOVIES_DATA,  adapter.getAllMovie());
        outState.putInt(Constants.MOVIES_LIST_VERTICAL_SCROLLBAR_POSITION,mainPopularMoviesListRV.getVerticalScrollbarPosition());
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
