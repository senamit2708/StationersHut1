package com.example.senamit.stationershut1;

import android.content.ContentValues;
import android.content.Intent;
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

public class ShundramItemList extends AppCompatActivity {

    public static final String LOG_TAG = ShundramItemList.class.getSimpleName();

    mDbHelper stationaryDbHelper;
    EditText edtProductName;
    EditText edtProductPrice;
    EditText edtProductQuanitity;
    Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shundram_item_list);

        stationaryDbHelper = new mDbHelper(this);

        btnSubmit =(Button)findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShundramItemList.this, "the button is clicked finally", Toast.LENGTH_LONG).show();
                insertProductDescription();
            }

            private void insertProductDescription() {

                SQLiteDatabase database = stationaryDbHelper.getWritableDatabase();

                edtProductName = (EditText)findViewById(R.id.edt_product_name);
                edtProductPrice = (EditText)findViewById(R.id.edt_product_price);
                edtProductQuanitity = (EditText)findViewById(R.id.edt_product_quantity);

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



    }




}
