package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.listener.BaseListener;
import com.example.myapplication.model.Video;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public class VideosAdapter extends BaseAdapter<Video,ArrayList<Video>> {

    public VideosAdapter(BaseListener<Video> listener) {
        super(new ArrayList<Video>(), listener);
    }

    @Override
    public BaseViewHolder<Video> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideosViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videos,parent,false),listener);
    }

    class VideosViewHolder extends BaseViewHolder<Video>{

        @BindView(R.id.tv_videos_title)
        TextView videosTitleTV;

        public VideosViewHolder(View itemView, BaseListener<Video> listener) {
            super(itemView, listener);
        }

        @Override
        public void setData(Video video) {
            super.setData(video);
            videosTitleTV.setText(video.getName());
        }
    }
}
