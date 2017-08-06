package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.listener.BaseListener;
import com.example.myapplication.model.Movie;
import com.example.myapplication.utils.ApiUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
@SuppressWarnings("ALL")
public class MoviesAdapter extends BaseAdapter<Movie,ArrayList<Movie>> {


    public MoviesAdapter(BaseListener<Movie> listener) {
        super(new ArrayList<Movie>(), listener);
    }

    @Override
    public BaseViewHolder<Movie> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies,parent,false),listener);
    }

    class MoviesViewHolder extends BaseViewHolder<Movie>{

        @BindView(R.id.iv_movies_poster)
        ImageView popularMoviePoster;

        public MoviesViewHolder(View itemView, BaseListener<Movie> listener) {
            super(itemView, listener);
        }

        @Override
        public void setData(Movie element) {
            super.setData(element);
            Picasso.with(itemView.getContext()).load(ApiUtils.IMG_BASE_URL+element.getPosterPath()).into(popularMoviePoster);
        }
    }

}
