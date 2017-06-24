package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
import com.example.myapplication.receiver.event.ConnectivityChangedEvent;
import com.example.myapplication.utils.ApiKeyUtils;
import com.example.myapplication.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pb_main_progress_sign)
    ProgressBar mainProgressSignPB;
    @BindView(R.id.tv_main_empty_text)
    TextView mainEmptyTextTV;
    @BindView(R.id.rv_main_popular_movies_list)
    RecyclerView mainPopularMoviesListRV;

    Unbinder unbinder;

    PopularMoviesAdapter adapter;

    PopularMoviesListener listener = new PopularMoviesListener() {
        @Override
        public void onPopularMovieClick(Movie movie) {
            //TODO
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        //set up mainPopularListRV
        adapter = new PopularMoviesAdapter(listener);
        mainPopularMoviesListRV.setLayoutManager(new GridLayoutManager(this, Constants.POPULAR_MOVIES_GRID_COLUMN));
        mainPopularMoviesListRV.setAdapter(adapter);

        if(savedInstanceState == null) {
            mainProgressSignPB.setVisibility(View.VISIBLE);

            //request data from server
            ApiRequest<PopularMoviesResponse> apiRequest = new ApiRequest<>(
                    ApiHelper.service(PopularMoviesService.class).getAllPopularMovie(ApiKeyUtils.API_KEY_V3),
                    new PopularMoviesResult(true, 0)
            );
            ApiRequestQueue.get().addRequestApi(apiRequest);
            ApiRequestQueue.get().requestAllRequestedApi();

        }else{
            adapter.addAllMovie((List<Movie>) savedInstanceState.getSerializable(Constants.POPULAR_MOVIES_DATA));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onLoadPopularMovies(LoadingPopularMoviesEvent event){
        adapter.addAllMovie(event.getMovies());
        if(adapter.getItemCount() == 0)mainEmptyTextTV.setVisibility(View.VISIBLE);
        else mainEmptyTextTV.setVisibility(View.GONE);
        mainProgressSignPB.setVisibility(View.GONE);
    }

    @Subscribe
    public void onConnectivityChange(ConnectivityChangedEvent event) {
        if(event.isConnected()){
            ApiRequestQueue.get().requestAllRequestedApi();
            if(adapter.getItemCount() == 0)mainProgressSignPB.setVisibility(View.VISIBLE);
        }else{
            if(mainProgressSignPB.getVisibility() == View.VISIBLE)mainProgressSignPB.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.POPULAR_MOVIES_DATA, (Serializable) adapter.getAllMovie());
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
