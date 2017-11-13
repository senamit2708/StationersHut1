package com.example.senamit.stationershut1;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senamit.stationershut1.data.StationaryContract;

public class ProductDesription extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    Uri currentProductUri;
    private static final int PRODUCT_LOADER = 0;
    TextView txtProductName;
    TextView txtProductPrice;
    TextView txtProductQuantity;
    Button btnMoreQuantity;
    Button btnLessQuantity;
    EditText edtProductQuantity;
    int originalproductQuantity;
    public static final String LOG_TAG = ProductDesription.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desription);

        Intent intent = getIntent();
        currentProductUri = intent.getData();
        txtProductName = (TextView)findViewById(R.id.txt_product_name);
        txtProductPrice = (TextView)findViewById(R.id.txt_product_price);
        txtProductQuantity = (TextView)findViewById(R.id.txt_product_quantity);
        edtProductQuantity = (EditText)findViewById(R.id.edt_product_quantity);
        btnMoreQuantity = (Button) findViewById(R.id.btn_moreQuantity);
        btnLessQuantity=(Button)findViewById(R.id.btn_lessQuantity);

        btnMoreQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProductDesription.this, "the button is clicked finally", Toast.LENGTH_LONG).show();
                insertProductQuantity();
            }

        });

        btnLessQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProductDesription.this, "the button is clicked finally", Toast.LENGTH_LONG).show();
                reduceProductQuantity();
            }
        });

        if (currentProductUri!=null) {

            getLoaderManager().initLoader(PRODUCT_LOADER, null, this);

        }
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {StationaryContract.ProductDesriptionEntry._ID, StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_NAME, StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_PRICE,
                StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY};

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

        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        while (cursor.moveToNext()) {

            int indextProductName = cursor.getColumnIndex(StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_NAME);
            int indexProductPrice = cursor.getColumnIndex(StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_PRICE);
            int indexJProuctQuantity = cursor.getColumnIndex(StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY);

            String productName = cursor.getString(indextProductName);
            int productPrice = cursor.getInt(indexProductPrice);
            originalproductQuantity = cursor.getInt(indexJProuctQuantity);
            Log.i(LOG_TAG, "the product name is inside loader " + productName + " " + productPrice + " " + originalproductQuantity);

            txtProductName.setText(productName);
            txtProductPrice.setText(Integer.toString(productPrice));
            txtProductQuantity.setText(Integer.toString(originalproductQuantity));

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        txtProductName.setText("");
        txtProductPrice.setText("");
        txtProductQuantity.setText("");
        edtProductQuantity.setText("");

    }



    private void insertProductQuantity() {
        String productQuanitityString = edtProductQuantity.getText().toString().trim();
        int editProductQuantity = Integer.parseInt(productQuanitityString);

        if (editProductQuantity <= originalproductQuantity) {

            originalproductQuantity = originalproductQuantity-editProductQuantity;
            ContentValues values = new ContentValues();
            values.put(StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY, originalproductQuantity);

            int rowaffected = getContentResolver().update(currentProductUri, values, null, null);
            edtProductQuantity.setText("");
            if (rowaffected == 0) {
                Log.i(LOG_TAG, "row is not updated");
            } else {
                Log.i(LOG_TAG, "The row is updated");
            }

        }
    }

    private void reduceProductQuantity() {
        String productQuanitityString = edtProductQuantity.getText().toString().trim();
        int editProductQuantity = Integer.parseInt(productQuanitityString);



            originalproductQuantity = originalproductQuantity+editProductQuantity;
            ContentValues values = new ContentValues();
            values.put(StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY, originalproductQuantity);

            int rowaffected = getContentResolver().update(currentProductUri, values, null, null);
            edtProductQuantity.setText("");
            if (rowaffected == 0) {
                Log.i(LOG_TAG, "row is not updated");
            } else {
                Log.i(LOG_TAG, "The row is updated");
            }




    }

}