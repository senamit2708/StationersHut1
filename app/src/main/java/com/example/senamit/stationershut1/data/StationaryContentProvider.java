package com.example.senamit.stationershut1.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by senamit on 11/11/17.
 */

public class StationaryContentProvider extends ContentProvider{

    private mDbHelper stationaryDbHelper;
    //creating constant value for the tag of uri
    private static final int PRODUCTDESCRIPTION = 100;
    private static final int PRODUCTDESCRIPTION_ID= 101;

    //creating UriMatcher
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(StationaryContract.CONTENT_AUTHORITY, StationaryContract.PATH_PRODUCTdESCRIPTIONENTRY, PRODUCTDESCRIPTION);
        sUriMatcher.addURI(StationaryContract.CONTENT_AUTHORITY, StationaryContract.PATH_PRODUCTdESCRIPTIONENTRY+"/#", PRODUCTDESCRIPTION_ID);
    }

    @Override
    public boolean onCreate() {

        stationaryDbHelper = new mDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
