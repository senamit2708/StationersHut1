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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.senamit.stationershut1.data.*;
import com.example.senamit.stationershut1.data.StationaryContract.*;

public class ShundramItemList extends AppCompatActivity {

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

                insertProductDescription();
            }

            private void insertProductDescription() {

                Uri newUri=null;

                String productName = edtProductName.getText().toString().trim();
                String productPriceString = edtProductPrice.getText().toString();
                String productQuanitityString = edtProductQuanitity.getText().toString();
                if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(productQuanitityString) || TextUtils.isEmpty(productPriceString)
                        ||Integer.parseInt(productPriceString)<0 || Integer.parseInt(productQuanitityString)<0){
                    Toast.makeText(ShundramItemList.this, "invalid data",Toast.LENGTH_LONG).show();
                }
                else {


                int prodcutPrice = Integer.parseInt(productPriceString);
                int productQuantity = Integer.parseInt(productQuanitityString);
                Log.i(LOG_TAG, "the productpricestring is "+productPriceString);

                ContentValues values = new ContentValues();
                values.put(ProductDesriptionEntry.COLUMN_PRODUCT_NAME, productName);
                values.put(ProductDesriptionEntry.COLUMN_PRODUCT_PRICE, prodcutPrice);
                values.put(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);

                    newUri = getContentResolver().insert(ProductDesriptionEntry.CONTENT_URI, values);
                }




                if (newUri == null) {

                    Log.i(LOG_TAG, "insertion unsuccessful  " + newUri);
                } else {
                    Log.i(LOG_TAG, "insertion successful  " + newUri);

                    finish();

                }


            }
        });


    }
}

