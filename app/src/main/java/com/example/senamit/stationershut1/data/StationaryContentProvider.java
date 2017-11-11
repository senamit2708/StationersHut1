package com.example.senamit.stationershut1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Switch;

import com.example.senamit.stationershut1.data.StationaryContract.*;
/**
 * Created by senamit on 11/11/17.
 */

public class StationaryContentProvider extends ContentProvider{

    public static final String LOG_TAG  = StationaryContentProvider.class.getSimpleName();

    private mDbHelper stationaryDbHelper;
    Cursor cursor;
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
    public Cursor query( Uri uri,  String[] projection,  String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = stationaryDbHelper.getWritableDatabase();
      int match = sUriMatcher.match(uri);

        switch (match){

            case PRODUCTDESCRIPTION:

                //here we dont have any where clause so..no id is here...we have to retrive data from the complete table.

                cursor = database.query(StationaryContract.ProductDesriptionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                Log.i(LOG_TAG, "inside complete table");

                break;
            case PRODUCTDESCRIPTION_ID:

                //here we have where claue ..so lets set the selection and selectionArgs..
                selection = ProductDesriptionEntry._ID+ "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(StationaryContract.ProductDesriptionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                Log.i(LOG_TAG, "Inside speical condition of query table");

                break;

            default:

                throw new IllegalArgumentException("bad uri ...."+  uri);

        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert( Uri uri,  ContentValues contentValues) {
        Uri newUri;

        String productName = contentValues.getAsString(ProductDesriptionEntry.COLUMN_PRODUCT_NAME);
        if (TextUtils.isEmpty(productName)){
            throw new IllegalArgumentException("product name is empty");
        }
        Integer productPrice = contentValues.getAsInteger(ProductDesriptionEntry.COLUMN_PRODUCT_PRICE);
        //when i used int here...null value condition its not accepting
        if (productPrice<0 || productPrice==null){
            throw new IllegalArgumentException("Product price is empty");
        }
        Integer productQuantity = contentValues.getAsInteger(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY);
        if (productQuantity<0 || productQuantity==null){
            throw new IllegalArgumentException("Product quantity is empty");
        }

        int match = sUriMatcher.match(uri);
        switch (match){

            case PRODUCTDESCRIPTION:
             newUri =   insertProductDescription(uri, contentValues);
                break;
            default:
                throw new IllegalArgumentException("the uri used to insert is bad "+ uri);


        }


        return newUri;
    }

    private Uri insertProductDescription(Uri uri, ContentValues contentValues) {
        SQLiteDatabase database = stationaryDbHelper.getWritableDatabase();
     long id=    database.insert(ProductDesriptionEntry.TABLE_NAME, null, contentValues);
        if(id== -1){
            Log.i(LOG_TAG, "unsuceesful insertion of new product");
            return null;
        }
        else {
            return ContentUris.withAppendedId(uri, id);
        }

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
