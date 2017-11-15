package com.example.senamit.stationershut1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.senamit.stationershut1.data.StationaryContract.*;


public class mDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Stationary";
    private static final int DATABASE_VERSION = 10;

    public mDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_PRODUCT_DESCRIPTION_ENTRIES = "CREATE TABLE " + ProductDesriptionEntry.TABLE_NAME +
            "(" + ProductDesriptionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ProductDesriptionEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL," +
            ProductDesriptionEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL," +
            ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL," +
            ProductDesriptionEntry.COLUMN_PRODUCT_IMAGE + " BLOB )";

    private static final String SQL_DELETE_PRODUCT_DESCRIPTION_ENTRIES = "DROP TABLE IF EXISTS " + ProductDesriptionEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCT_DESCRIPTION_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_PRODUCT_DESCRIPTION_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
