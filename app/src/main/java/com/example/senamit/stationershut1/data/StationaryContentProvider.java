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

public class StationaryContentProvider extends ContentProvider {

    private static final String LOG_TAG = StationaryContentProvider.class.getSimpleName();
    private mDbHelper stationaryDbHelper;
    Cursor cursor;
    private static final int PRODUCTDESCRIPTION = 100;
    private static final int PRODUCTDESCRIPTION_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(StationaryContract.CONTENT_AUTHORITY, StationaryContract.PATH_PRODUCTdESCRIPTIONENTRY, PRODUCTDESCRIPTION);
        sUriMatcher.addURI(StationaryContract.CONTENT_AUTHORITY, StationaryContract.PATH_PRODUCTdESCRIPTIONENTRY + "/#", PRODUCTDESCRIPTION_ID);
    }

    @Override
    public boolean onCreate() {
        stationaryDbHelper = new mDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = stationaryDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTDESCRIPTION:
                cursor = database.query(StationaryContract.ProductDesriptionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case PRODUCTDESCRIPTION_ID:
                selection = ProductDesriptionEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(StationaryContract.ProductDesriptionEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("the given uri is not correct" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri newUri;
        String productName = contentValues.getAsString(ProductDesriptionEntry.COLUMN_PRODUCT_NAME);
        if (TextUtils.isEmpty(productName)) {
            throw new IllegalArgumentException("product name is empty");
        }
        Integer productPrice = contentValues.getAsInteger(ProductDesriptionEntry.COLUMN_PRODUCT_PRICE);
        if (productPrice < 0 || productPrice == null) {
            throw new IllegalArgumentException("Product price is empty");
        }
        Integer productQuantity = contentValues.getAsInteger(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY);
        if (productQuantity < 0 || productQuantity == null) {
            throw new IllegalArgumentException("Product quantity is empty");
        }
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTDESCRIPTION:
                newUri = insertProductDescription(uri, contentValues);
                break;
            default:
                throw new IllegalArgumentException("the uri used to insert is bad " + uri);
        }
        return newUri;
    }

    private Uri insertProductDescription(Uri uri, ContentValues contentValues) {
        SQLiteDatabase database = stationaryDbHelper.getWritableDatabase();
        long id = database.insert(ProductDesriptionEntry.TABLE_NAME, null, contentValues);
        if (id == -1) {
            Log.i(LOG_TAG, "unsuceesful insertion of new product");
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = stationaryDbHelper.getWritableDatabase();
        getContext().getContentResolver().notifyChange(uri, null);
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTDESCRIPTION:
                return database.delete(ProductDesriptionEntry.TABLE_NAME, selection, selectionArgs);
            case PRODUCTDESCRIPTION_ID:
                selection = ProductDesriptionEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(ProductDesriptionEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("the uri used to delete is bad " + uri);
        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTDESCRIPTION:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case PRODUCTDESCRIPTION_ID:
                selection = ProductDesriptionEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateProduct(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("the uri used to insert is bad " + uri);
        }
    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (values.containsKey(ProductDesriptionEntry.COLUMN_PRODUCT_NAME)) {
            String productName = values.getAsString(ProductDesriptionEntry.COLUMN_PRODUCT_NAME);
            if (productName == null) {
                throw new IllegalArgumentException("the uri used to insert is bad " + uri);
            }
        }
        if (values.containsKey(ProductDesriptionEntry.COLUMN_PRODUCT_PRICE)) {
            String productPrice = values.getAsString(ProductDesriptionEntry.COLUMN_PRODUCT_PRICE);
            if (productPrice == null) {
                throw new IllegalArgumentException("the uri used to insert is bad " + uri);
            }
        }
        if (values.containsKey(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY)) {
            String productQuantity = values.getAsString(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY);
            if (productQuantity == null) {
                throw new IllegalArgumentException("the uri used to insert is bad " + uri);
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = stationaryDbHelper.getWritableDatabase();
        getContext().getContentResolver().notifyChange(uri, null);
        return database.update(ProductDesriptionEntry.TABLE_NAME, values, selection, selectionArgs);
    }
}
