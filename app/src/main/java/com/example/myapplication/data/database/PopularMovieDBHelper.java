package com.example.myapplication.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.data.database.contract.PopularMovieContract;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public class PopularMovieDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "PopularMovie.db";
    public static final int DATABASE_VERSION = 1;

    private static PopularMovieDBHelper instance;

    public static PopularMovieDBHelper getInstance(Context context){
        if(instance == null){
            instance = new PopularMovieDBHelper(context);
        }
        return instance;
    }

    private PopularMovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_MOVIE = "CREATE TABLE "+ PopularMovieContract.MovieEntry.TABLE_NAME +"("+
                PopularMovieContract.MovieEntry._ID+" INTEGER(11) PRIMARY KEY,"+
                PopularMovieContract.MovieEntry.COLUMN_TITLE +" TEXT NOT NULL,"+
                PopularMovieContract.MovieEntry.COLUMN_POSTER_PATH +" TEXT NOT NULL,"+
                PopularMovieContract.MovieEntry.COLUMN_THUMBNAIL_PATH +" TEXT NOT NULL,"+
                PopularMovieContract.MovieEntry.COLUMN_SYNOPSIS +" TEXT NOT NULL,"+
                PopularMovieContract.MovieEntry.COLUMN_USER_RATING +" DOUBLE,"+
                PopularMovieContract.MovieEntry.COLUMN_RELEASE_DATE +" LONG,"+
                PopularMovieContract.MovieEntry.COLUMN_FAVORITE + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //nothing
    }
}
