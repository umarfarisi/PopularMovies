package com.example.myapplication.data.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.myapplication.data.database.PopularMovieDBHelper;
import com.example.myapplication.data.database.contract.PopularMovieContract;

/**
 * @author Muhammad Umar Farisi
 * @created 07/08/2017
 */

public class PopularMovieContentProvider extends ContentProvider{

    private static final int CODE_MOVIE = 100;

    private PopularMovieDBHelper dbHelper;
    private UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        dbHelper = PopularMovieDBHelper.getInstance(getContext());
        uriMatcher = buildUriMatcher();
        return true;
    }

    private UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PopularMovieContract.AUTHORITY,PopularMovieContract.PATH_MOVIE, CODE_MOVIE);
        return uriMatcher;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int code = uriMatcher.match(uri);
        switch (code){
            case CODE_MOVIE:
                return dbHelper.getReadableDatabase().query(PopularMovieContract.MovieEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri.toString());
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int code = uriMatcher.match(uri);
        switch (code){
            case CODE_MOVIE:
                long row = dbHelper.getWritableDatabase().insert(PopularMovieContract.MovieEntry.TABLE_NAME,null,values);
                if(row >= 0){
                    return uri;
                }
                return null;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri.toString());
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        switch (code){
            case CODE_MOVIE:
                int rowCount = dbHelper.getWritableDatabase().delete(PopularMovieContract.MovieEntry.TABLE_NAME,selection,selectionArgs);
                return rowCount;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri.toString());
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        switch (code){
            case CODE_MOVIE:
                return dbHelper.getWritableDatabase().update(PopularMovieContract.MovieEntry.TABLE_NAME,values,selection,selectionArgs);
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri.toString());
        }
    }
}
