package com.example.senamit.stationershut1.data;

import android.provider.BaseColumns;

/**
 * Created by senamit on 8/11/17.
 */

public class StationaryContract {

    private StationaryContract() {
    }

    public class ProductDesriptionEntry implements BaseColumns {

        public static final String TABLE_NAME = "ProductDescription";
        public static final String COLUMN_PRODUCT_NAME = "ProductName";
        public static final String COLUMN_PRODUCT_PRICE = "ProductPrice";
        public static final String COLUMN_PRODUCT_QUANTITY = "ProductQuantity";


    }




}


//main motto of creating this class is to .....give the constant value name to
//first table we r going here to implement ..by creating a subclass of basecolumn