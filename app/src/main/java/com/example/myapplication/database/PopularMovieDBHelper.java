package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.database.contract.PopularMovieContract;
import com.example.myapplication.utils.GlobalVariable;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public class PopularMovieDBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "PopularMovie.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE_MOVIE = "CREATE TABLE "+ PopularMovieContract.MovieContract.TABLE_NAME +"("+
            PopularMovieContract.MovieContract._ID+" INTEGER(11),"+
            PopularMovieContract.MovieContract.TITLE_COLUMN+" TEXT,"+
            PopularMovieContract.MovieContract.POSTER_PATH_COLUMN+" TEXT,"+
            PopularMovieContract.MovieContract.BACKDROP_PATH_COLUMN+" TEXT,"+
            PopularMovieContract.MovieContract.OVERVIEW_COLUMN+" TEXT,"+
            PopularMovieContract.MovieContract.VOTE_AVERAGE_COLUMN+" TEXT,"+
            PopularMovieContract.MovieContract.RELEASE_DATE_COLUMN+" TEXT);";

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
        db.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //nothing
    }
}
