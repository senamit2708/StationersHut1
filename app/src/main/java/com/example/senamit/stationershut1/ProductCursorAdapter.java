package com.example.senamit.stationershut1;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.senamit.stationershut1.data.StationaryContract;
import com.example.senamit.stationershut1.data.StationaryContract.*;

import java.util.zip.Inflater;

/**
 * Created by senamit on 12/11/17.
 */

public class ProductCursorAdapter  extends CursorAdapter{

    public static final String LOG_TAG = ProductCursorAdapter.class.getSimpleName();

    public ProductCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
        Log.i(LOG_TAG, "inside the constructor");

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        Log.i(LOG_TAG, "inside newView ");
        return LayoutInflater.from(context).inflate(R.layout.activity_list_view_product_items, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Log.i(LOG_TAG, "inside bindview");

        TextView txtProductName  = (TextView)view.findViewById(R.id.txt_product_name);
        TextView txtProductPrice = (TextView)view.findViewById(R.id.txt_product_price);
        TextView txtProductQuantity = (TextView)view.findViewById(R.id.txt_product_quantity);

        //finding out the index  from which we will retrive data
        int productNameColumnIndex = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_NAME);
        int productPriceColumnIndex = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_PRICE);
        int productQuantityColumnIndex = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY);

        Log.i(LOG_TAG, "the product column index is "+productNameColumnIndex);
        //retriving data from the index of cursor
        String productName = cursor.getString(productNameColumnIndex);
        int productPrice = cursor.getInt(productPriceColumnIndex);
        int productQuantity = cursor.getInt(productQuantityColumnIndex);

        Log.i(LOG_TAG, "product name in cursor is "+productName);

        //updating the textview with their attributes
        txtProductName.setText(productName);
        txtProductPrice.setText(String.valueOf(productPrice));
        txtProductQuantity.setText(String.valueOf(productQuantity));

        Log.i(LOG_TAG, "product name is "+ productName );



    }
}
