package com.example.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.listener.PopularMoviesListener;
import com.example.myapplication.model.Movie;
import com.example.myapplication.utils.ApiUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Muhammad Umar Farisi
 * @created 24/06/2017
 */
@SuppressWarnings("ALL")
public class MoviesAdapter extends BaseAdapter<Movie,ArrayList<Movie>,MoviesAdapter.MoviesViewHolder> {

    private PopularMoviesListener listener;

    public MoviesAdapter(ArrayList<Movie> movies, PopularMoviesListener listener) {
        super(movies);
        this.listener = listener;
    }

    public MoviesAdapter(PopularMoviesListener listener){
        this(new ArrayList<Movie>(),listener);
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_movies,parent,false));
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.setData(elements.get(position));
        holder.setListener(listener);
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_popular_movie_poster)
        ImageView popularMoviePoster;

        Movie movie;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(Movie movie){
            this.movie = movie;
            Picasso.with(itemView.getContext()).load(ApiUtils.IMG_BASE_URL+movie.getPosterPath()).into(popularMoviePoster);
        }

        public void setListener(final PopularMoviesListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPopularMovieClick(movie);
                }
            });
        }

    }

}
