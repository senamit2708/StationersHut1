package com.example.senamit.stationershut1;

import android.content.ContentValues;
import android.content.Intent;
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

    private static final String LOG_TAG = ShundramItemList.class.getSimpleName();
    private mDbHelper stationaryDbHelper;
    private EditText edtProductName;
    private EditText edtProductPrice;
    private EditText edtProductQuanitity;
    private Button btnSubmit;
    private Button btnLoadImage;
    private Uri currentProductUri;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shundram_item_list);

        edtProductName = (EditText) findViewById(R.id.edt_product_name);
        edtProductPrice = (EditText) findViewById(R.id.edt_product_price);
        edtProductQuanitity = (EditText) findViewById(R.id.edt_product_quantity);
        stationaryDbHelper = new mDbHelper(this);

        Intent intent = getIntent();
        currentProductUri = intent.getData();

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnLoadImage = (Button) findViewById(R.id.btn_load_image);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertProductDescription();
            }

            private void insertProductDescription() {

                Uri newUri = null;

                String productName = edtProductName.getText().toString().trim();
                String productPriceString = edtProductPrice.getText().toString();
                String productQuanitityString = edtProductQuanitity.getText().toString();
                if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(productQuanitityString) || TextUtils.isEmpty(productPriceString)
                        || Integer.parseInt(productPriceString) < 0 || Integer.parseInt(productQuanitityString) < 0) {
                    Toast.makeText(ShundramItemList.this, "invalid data", Toast.LENGTH_LONG).show();
                } else {


                    int prodcutPrice = Integer.parseInt(productPriceString);
                    int productQuantity = Integer.parseInt(productQuanitityString);
                    Log.i(LOG_TAG, "the productpricestring is " + productPriceString);

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

        btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Log.i(LOG_TAG, "inside image load");
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                //I dont know what to do next from here...
            }
        });

    }
}

