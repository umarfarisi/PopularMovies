package com.example.myapplication.data.database.contract;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public final class PopularMovieContract {

    public static final String PATH_MOVIE = "movie";
    public static final String AUTHORITY = "com.example.myapplication";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY).buildUpon().appendPath(PATH_MOVIE).build();

    public final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "Movie";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_THUMBNAIL_PATH = "thumbnail_path";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_USER_RATING = "user_rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_FAVORITE = "favorite";
    }
}
