package com.example.senamit.stationershut1;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.senamit.stationershut1.data.*;
import com.example.senamit.stationershut1.data.StationaryContract.*;

public class ShundramItemList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String LOG_TAG = ShundramItemList.class.getSimpleName();

    mDbHelper stationaryDbHelper;
    EditText edtProductName;
    EditText edtProductPrice;
    EditText edtProductQuanitity;
    Button btnSubmit;
    Uri currentProductUri;
    private static final int PRODUCT_LOADER = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shundram_item_list);

        edtProductName = (EditText)findViewById(R.id.edt_product_name);
        edtProductPrice = (EditText)findViewById(R.id.edt_product_price);
        edtProductQuanitity = (EditText)findViewById(R.id.edt_product_quantity);

        stationaryDbHelper = new mDbHelper(this);

        Intent intent = getIntent();
      currentProductUri =  intent.getData();

        btnSubmit =(Button)findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShundramItemList.this, "the button is clicked finally", Toast.LENGTH_LONG).show();
                insertProductDescription();
            }

            private void insertProductDescription() {

                SQLiteDatabase database = stationaryDbHelper.getWritableDatabase();



                String productName = edtProductName.getText().toString().trim();
                String productPriceString = edtProductPrice.getText().toString().trim();
                String productQuanitityString = edtProductQuanitity.getText().toString().trim();
                int prodcutPrice  = Integer.parseInt(productPriceString);
                int productQuantity = Integer.parseInt(productQuanitityString);

                ContentValues values = new ContentValues();
                values.put(ProductDesriptionEntry.COLUMN_PRODUCT_NAME, productName);
                values.put(ProductDesriptionEntry.COLUMN_PRODUCT_PRICE, prodcutPrice);
                values.put(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);



                Uri newUri = getContentResolver().insert(ProductDesriptionEntry.CONTENT_URI, values);

                if (newUri == null){

                    Log.i(LOG_TAG,"insertion unsuccessful  "+ newUri);
                }
                else {
                    Log.i(LOG_TAG,"insertion successful  "+ newUri);

                    finish();

                }
            }
        });

        if (currentProductUri!=null) {

            getLoaderManager().initLoader(PRODUCT_LOADER, null, this);

        }

    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {ProductDesriptionEntry._ID, ProductDesriptionEntry.COLUMN_PRODUCT_NAME, ProductDesriptionEntry.COLUMN_PRODUCT_PRICE,
                ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY};

        //cursorLoader will call the contentProvider....one extra parameter here is context
        CursorLoader cursorLoader = new CursorLoader(
                this,
                currentProductUri,
                projection,
                null,
                null,
                null );

        return cursorLoader;



    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor ==null || cursor.getCount()<1){
            return;
        }
        while (cursor.moveToNext()){

            int indextProductName = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_NAME);
            int indexProductPrice = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_PRICE);
            int indexJProuctQuantity = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY);

            String productName = cursor.getString(indextProductName);
            int productPrice = cursor.getInt(indexProductPrice);
            int productQuantity = cursor.getInt(indexJProuctQuantity);
            Log.i(LOG_TAG, "the product name is inside loader "+ productName +" "+productPrice+" "+productQuantity);

            edtProductName.setText(productName);
            edtProductPrice.setText(Integer.toString(productPrice));
            edtProductQuanitity.setText(Integer.toString(productQuantity));


        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        edtProductName.setText("");
        edtProductPrice.setText("");
        edtProductQuanitity.setText("");
    }
}
