package com.example.senamit.stationershut1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.senamit.stationershut1.data.*;
import com.example.senamit.stationershut1.data.StationaryContract.*;

public class ShundramItemList extends AppCompatActivity {

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

                long num  = database.insert(ProductDesriptionEntry.TABLE_NAME,null,values);

                if (num==-1){
                    Toast.makeText(ShundramItemList.this, "Data Not inserted Successfully", Toast.LENGTH_SHORT);
                }
                else {
                    Toast.makeText(ShundramItemList.this, "Data inserted Successfully", Toast.LENGTH_SHORT);
                    finish();

                }
            }
        });



    }

//    private void insertProductDescription(){
//
//        stationaryDbHelper = new mDbHelper(this);
//        SQLiteDatabase database = stationaryDbHelper.getWritableDatabase();
//
//        edtProductName = (EditText)findViewById(R.id.edt_product_name);
//        edtProductPrice = (EditText)findViewById(R.id.edt_product_price);
//        edtProductQuanitity = (EditText)findViewById(R.id.edt_product_quantity);
//
//        String productName = edtProductName.toString().trim();
//        String productPrice = edtProductPrice.toString().trim();
//        String productQuanitity = edtProductQuanitity.toString().trim();
//
//        ContentValues values = new ContentValues();
//        values.put(ProductDesriptionEntry.COLUMN_PRODUCT_NAME, productName);
//        values.put(ProductDesriptionEntry.COLUMN_PRODUCT_PRICE, productPrice);
//        values.put(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY, productQuanitity);
//
//        long num  = database.insert(ProductDesriptionEntry.TABLE_NAME,null,values);
//
//        if (num==-1){
//            Toast.makeText(ShundramItemList.this, "Data Not inserted Successfully", Toast.LENGTH_SHORT);
//        }
//        else {
//            Toast.makeText(ShundramItemList.this, "Data inserted Successfully", Toast.LENGTH_SHORT);
////            Intent intent = new Intent(ShundramItemList.this, MainActivity.class);
////            startActivity(intent);
//        }
//
//
//    }


}
