package com.example.myapplication.database.contract;

import android.provider.BaseColumns;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public final class PopularMovieContract {
    public final class MovieContract implements BaseColumns {
        public static final String TABLE_NAME = "Movie";
        public static final String TITLE_COLUMN = "title";
        public static final String POSTER_PATH_COLUMN = "poster_path";
        public static final String BACKDROP_PATH_COLUMN = "backdrop_path";
        public static final String OVERVIEW_COLUMN = "overview";
        public static final String VOTE_AVERAGE_COLUMN = "vote_average";
        public static final String RELEASE_DATE_COLUMN = "release_date";
    }
}
