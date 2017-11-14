package com.example.senamit.stationershut1;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
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
    Button btnOrderMore;
    Button btnDeleteProduct;
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
        btnOrderMore = (Button)findViewById(R.id.btn_order_more);
        btnDeleteProduct = (Button)findViewById(R.id.btn_delete_product);

        btnLessQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProductDesription.this, "the button is clicked finally", Toast.LENGTH_LONG).show();
                insertProductQuantity();
            }

        });

        btnMoreQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProductDesription.this, "the button is clicked finally", Toast.LENGTH_LONG).show();
                reduceProductQuantity();
            }
        });

        btnOrderMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//                intent.putExtra(Intent.EXTRA_EMAIL, "amit");
                intent.putExtra(Intent.EXTRA_SUBJECT, "order items");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();

            }
        });


        if (currentProductUri!=null) {

            getLoaderManager().initLoader(PRODUCT_LOADER, null, this);

        }
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titleDelete)
                .setMessage(R.string.deleteMessage)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(LOG_TAG, "inside prompt Ok msg");
                        deletePet();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deletePet() {

        if(currentProductUri !=null){


            int rowDeleted = getContentResolver().delete(currentProductUri, null, null);

            if (rowDeleted>0){
                finish();
            }
            else {
                Toast.makeText(ProductDesription.this, "the product is not deleted", Toast.LENGTH_LONG);
            }



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