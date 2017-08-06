package com.example.myapplication.data.database.contract;

import android.provider.BaseColumns;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public final class PopularMovieContract {
    public final class MovieContract implements BaseColumns {
        public static final String TABLE_NAME = "Movie";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_RELEASE_DATE = "release_date";
    }
}
