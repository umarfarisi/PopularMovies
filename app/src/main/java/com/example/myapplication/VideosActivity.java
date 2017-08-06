package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.myapplication.adapter.VideosAdapter;
import com.example.myapplication.adapter.listener.BaseListener;
import com.example.myapplication.api.core.ApiHelper;
import com.example.myapplication.api.core.ApiRequest;
import com.example.myapplication.api.core.ApiRequestQueue;
import com.example.myapplication.api.result.MovieDetailResult;
import com.example.myapplication.api.service.MovieDetailService;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.Video;
import com.example.myapplication.utils.ApiKeyUtils;
import com.example.myapplication.utils.ApiUtils;
import com.example.myapplication.utils.Constants;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideosActivity extends BaseActivity {

    @BindView(R.id.tb_videos_toolbar)
    Toolbar videosToolbarTB;
    @BindView(R.id.pb_videos_progress_sign)
    ProgressBar videosProgressSignPB;
    @BindView(R.id.rv_videos_content)
    RecyclerView videosContentRV;

    private VideosAdapter videosAdapter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(videosToolbarTB);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getString(R.string.videos_title));

        videosAdapter = new VideosAdapter(new BaseListener<Video>() {
            @Override
            public void onItemClick(Video video) {
                String videoURL = ApiUtils.VIDEO_BASE_URL+video.getKey();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(videoURL));
                startActivity(browserIntent);
            }
        });

        videosContentRV.setLayoutManager(new LinearLayoutManager(this));
        videosContentRV.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        videosContentRV.setAdapter(videosAdapter);

        Movie movie = getIntent().getParcelableExtra(Constants.MOVIE_EXTRA);

        if(movie != null){
            getSupportActionBar().setSubtitle(movie.getTitle());
            if(savedInstanceState == null){
                videosProgressSignPB.setVisibility(View.VISIBLE);
                ApiRequestQueue.get().addRequestApi(new ApiRequest<>(
                        ApiHelper.service(MovieDetailService.class).getVideos(movie.getId(), ApiKeyUtils.API_KEY_V3),
                        new MovieDetailResult.GettingVideosResult(), true
                ));
                ApiRequestQueue.get().requestAllRequestedApi();
            }else{
                videosAdapter.addAll(savedInstanceState.<Video>getParcelableArrayList(Constants.VIDEOS_DATA));
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
    public void onLoadVideos(MovieDetailResult.GettingVideosResult result){
        videosProgressSignPB.setVisibility(View.GONE);
        videosAdapter.addAll(result.getResponse().getResults());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.VIDEOS_DATA,videosAdapter.getElements());
    }

}
