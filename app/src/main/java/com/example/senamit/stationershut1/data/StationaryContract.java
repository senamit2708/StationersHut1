package com.example.senamit.stationershut1.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by senamit on 8/11/17.
 */

public class StationaryContract {


    public static final String CONTENT_AUTHORITY = "com.example.senamit.stationershut1.data.StationaryContentProvider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_PRODUCTdESCRIPTIONENTRY = "ProductDescription";

    private StationaryContract() {
    }

    public static final class ProductDesriptionEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTdESCRIPTIONENTRY);

        public static final String TABLE_NAME = "ProductDescription";
        public static final String COLUMN_PRODUCT_NAME = "ProductName";
        public static final String COLUMN_PRODUCT_PRICE = "ProductPrice";
        public static final String COLUMN_PRODUCT_QUANTITY = "ProductQuantity";


    }




}


//main motto of creating this class is to .....give the constant value name to
//first table we r going here to implement ..by creating a subclass of basecolumn