package com.example.senamit.stationershut1;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senamit.stationershut1.data.StationaryContract;

public class ProductDesription extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri currentProductUri;
    private static final int PRODUCT_LOADER = 0;
    private TextView txtProductName;
    private TextView txtProductPrice;
    private TextView txtProductQuantity;
    private Button btnMoreQuantity;
    private Button btnLessQuantity;
    private Button btnOrderMore;
    private Button btnDeleteProduct;
    private EditText edtProductQuantity;
    private int originalproductQuantity;
    private ImageView imgProductImage;
    private static final String LOG_TAG = ProductDesription.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_desription);

        Intent intent = getIntent();
        currentProductUri = intent.getData();
        txtProductName = (TextView) findViewById(R.id.txt_product_name);
        txtProductPrice = (TextView) findViewById(R.id.txt_product_price);
        txtProductQuantity = (TextView) findViewById(R.id.txt_product_quantity);
        edtProductQuantity = (EditText) findViewById(R.id.edt_product_quantity);
        btnMoreQuantity = (Button) findViewById(R.id.btn_moreQuantity);
        btnLessQuantity = (Button) findViewById(R.id.btn_lessQuantity);
        btnOrderMore = (Button) findViewById(R.id.btn_order_more);
        btnDeleteProduct = (Button) findViewById(R.id.btn_delete_product);
        imgProductImage = (ImageView) findViewById(R.id.img_product_image);

        btnLessQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productQuanitityString = edtProductQuantity.getText().toString();
                if (TextUtils.isEmpty(productQuanitityString) || Integer.parseInt(productQuanitityString) < 0) {
                    Toast.makeText(ProductDesription.this, R.string.enterCorrectQuantity, Toast.LENGTH_LONG).show();
                } else {
                    int editProductQuantity = Integer.parseInt(productQuanitityString);
                    reduceProductQuantity(editProductQuantity);
                }
            }
        });

        btnMoreQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productQuanitityString = edtProductQuantity.getText().toString();
                if (TextUtils.isEmpty(productQuanitityString) || Integer.parseInt(productQuanitityString) < 0) {
                    Toast.makeText(ProductDesription.this, R.string.enterCorrectQuantity, Toast.LENGTH_LONG).show();
                } else {
                    int editProductQuantity = Integer.parseInt(productQuanitityString);
                    addProductQuantity(editProductQuantity);
                }
            }
        });

        btnOrderMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
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

        if (currentProductUri != null) {
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
                        deleteProduct();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteProduct() {

        if (currentProductUri != null) {
            int rowDeleted = getContentResolver().delete(currentProductUri, null, null);
            if (rowDeleted > 0) {
                finish();
            } else {
                Toast.makeText(ProductDesription.this, R.string.productNotDeleted, Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {StationaryContract.ProductDesriptionEntry._ID, StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_NAME, StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_PRICE,
                StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY, StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_IMAGE};

        CursorLoader cursorLoader = new CursorLoader(
                this,
                currentProductUri,
                projection,
                null,
                null,
                null);
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
            int indexProductImage = cursor.getColumnIndex(StationaryContract.ProductDesriptionEntry.COLUMN_PRODUCT_IMAGE);

            String productName = cursor.getString(indextProductName);
            int productPrice = cursor.getInt(indexProductPrice);
            originalproductQuantity = cursor.getInt(indexJProuctQuantity);
            byte[] blob = cursor.getBlob(indexProductImage);

            Bitmap bitmapImage = BitmapFactory.decodeByteArray(blob, 0, blob.length);

            txtProductName.setText(productName);
            txtProductPrice.setText(Integer.toString(productPrice));
            txtProductQuantity.setText(Integer.toString(originalproductQuantity));
            imgProductImage.setImageBitmap(bitmapImage);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        txtProductName.setText("");
        txtProductPrice.setText("");
        txtProductQuantity.setText("");
        edtProductQuantity.setText("");
    }

    private void reduceProductQuantity(int editProductQuantity) {

        if (editProductQuantity <= originalproductQuantity) {
            originalproductQuantity = originalproductQuantity - editProductQuantity;
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

    private void addProductQuantity(int editProductQuantity) {

        originalproductQuantity = originalproductQuantity + editProductQuantity;
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