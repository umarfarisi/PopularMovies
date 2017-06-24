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
public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.PopularMovieViewHolder> {

    private List<Movie> movies;
    private PopularMoviesListener listener;

    public PopularMoviesAdapter(PopularMoviesListener listener) {
        this.movies = new ArrayList<>();
        this.listener = listener;
    }

    public void addAllMovie(List<Movie> newMovies){
        movies.addAll(newMovies);
        notifyDataSetChanged();
    }

    public void addMovie(Movie movie){
        movies.add(movie);
        notifyDataSetChanged();
    }

    public void removeMovie(Movie movie){
        movies.remove(movie);
        notifyDataSetChanged();
    }

    public void removeAllMovie(){
        movies.clear();
        notifyDataSetChanged();
    }

    public void updateMovie(Movie oldMovie, Movie newMovie){
        Movie movie = movies.get(movies.indexOf(oldMovie));
        movie.setPosterPath(newMovie.getPosterPath());
        movie.setReleaseDate(newMovie.getReleaseDate());
        movie.setSynopsis(newMovie.getSynopsis());
        movie.setThumbnailPath(newMovie.getThumbnailPath());
        movie.setTitle(newMovie.getTitle());
        movie.setUserRating(newMovie.getUserRating());
    }

    public List<Movie> getAllMovie() {
        return movies;
    }

    @Override
    public PopularMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PopularMovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_movies,parent,false));
    }

    @Override
    public void onBindViewHolder(PopularMovieViewHolder holder, int position) {
        holder.setData(movies.get(position));
        holder.setListener(listener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class PopularMovieViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_popular_movie_poster)
        ImageView popularMoviePoster;

        Movie movie;

        public PopularMovieViewHolder(View itemView) {
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
