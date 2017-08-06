package com.example.myapplication.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.data.database.contract.PopularMovieContract;
import com.example.myapplication.utils.GlobalVariable;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public class PopularMovieDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "PopularMovie.db";
    public static final int DATABASE_VERSION = 1;

    private static PopularMovieDBHelper instance;

    public static PopularMovieDBHelper getInstance(){
        if(instance == null){
            instance = new PopularMovieDBHelper(GlobalVariable.APP_CONTEXT);
        }
        return instance;
    }

    public PopularMovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_MOVIE = "CREATE TABLE "+ PopularMovieContract.MovieContract.TABLE_NAME +"("+
                PopularMovieContract.MovieContract._ID+" INTEGER(11) PRIMARY KEY,"+
                PopularMovieContract.MovieContract.COLUMN_TITLE +" TEXT NOT NULL,"+
                PopularMovieContract.MovieContract.COLUMN_POSTER_PATH +" TEXT NOT NULL,"+
                PopularMovieContract.MovieContract.COLUMN_BACKDROP_PATH +" TEXT NOT NULL,"+
                PopularMovieContract.MovieContract.COLUMN_OVERVIEW +" TEXT NOT NULL,"+
                PopularMovieContract.MovieContract.COLUMN_VOTE_AVERAGE +" TEXT NOT NULL,"+
                PopularMovieContract.MovieContract.COLUMN_RELEASE_DATE +" TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //nothing
    }
}
