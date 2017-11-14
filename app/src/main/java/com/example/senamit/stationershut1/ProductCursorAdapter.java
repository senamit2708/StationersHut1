package com.example.senamit.stationershut1;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.senamit.stationershut1.data.StationaryContract.*;

public class ProductCursorAdapter extends CursorAdapter {

    private static final String LOG_TAG = ProductCursorAdapter.class.getSimpleName();

    public ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        Log.i(LOG_TAG, "inside the constructor");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        Log.i(LOG_TAG, "inside newView ");
        return LayoutInflater.from(context).inflate(R.layout.activity_list_view_product_items, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        TextView txtProductName = (TextView) view.findViewById(R.id.txt_product_name);
        TextView txtProductPrice = (TextView) view.findViewById(R.id.txt_product_price);
        TextView txtProductQuantity = (TextView) view.findViewById(R.id.txt_product_quantity);
        Button btnSale = (Button) view.findViewById(R.id.btn_sale);

        int productNameColumnIndex = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_NAME);
        int productPriceColumnIndex = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_PRICE);
        final int productQuantityColumnIndex = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY);
        final int productId = cursor.getColumnIndex(ProductDesriptionEntry._ID);

        String productName = cursor.getString(productNameColumnIndex);
        int productPrice = cursor.getInt(productPriceColumnIndex);
        int productQuantity = cursor.getInt(productQuantityColumnIndex);

        txtProductName.setText(productName);
        txtProductPrice.setText(String.valueOf(productPrice));
        txtProductQuantity.setText(String.valueOf(productQuantity));

        final int position = cursor.getPosition();

        btnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues incrementValue = new ContentValues();
                cursor.moveToPosition(position);
                int productQuantity = cursor.getInt(productQuantityColumnIndex);

                if (productQuantity > 0) {
                    int newquantity = productQuantity - 1;
                    incrementValue.put(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY, newquantity);
                    int id = cursor.getInt(productId);
                    Uri currentProductUri = ContentUris.withAppendedId(ProductDesriptionEntry.CONTENT_URI, id);
                    int rowaffected = context.getContentResolver().update(currentProductUri, incrementValue, null, null);
                    if (rowaffected == 0) {
                        Log.i(LOG_TAG, "row is not updated");
                    } else {
                        Log.i(LOG_TAG, "The row is updated");
                    }
                }
            }
        });
    }
}
