package com.example.senamit.stationershut1;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.senamit.stationershut1.data.StationaryContract.*;
import com.example.senamit.stationershut1.data.*;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private String LOG_TAG = MainActivity.class.getSimpleName();
    private mDbHelper stationaryDbHelper;
    Cursor cursor;
    ListView listViewProduct;
    private static int PRODUCT_LOADER = 0;
    private ProductCursorAdapter productCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stationaryDbHelper = new mDbHelper(MainActivity.this);
        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab_button);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabButtonAddProduct();
            }
        });
        listViewProduct = (ListView) findViewById(R.id.list_view_product);
        View emptyView = findViewById(R.id.empty_view);
        listViewProduct.setEmptyView(emptyView);
        productCursorAdapter = new ProductCursorAdapter(this, cursor);
        listViewProduct.setAdapter(productCursorAdapter);

        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {

                Intent intent = new Intent(MainActivity.this, ProductDesription.class);
                Uri currentProductUri = ContentUris.withAppendedId(ProductDesriptionEntry.CONTENT_URI, id);
                intent.setData(currentProductUri);
                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);
    }

    private void fabButtonAddProduct() {
        Intent intent = new Intent(MainActivity.this, ShundramItemList.class);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {ProductDesriptionEntry._ID, ProductDesriptionEntry.COLUMN_PRODUCT_NAME, ProductDesriptionEntry.COLUMN_PRODUCT_PRICE,
                ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY};
        CursorLoader cursorLoader = new CursorLoader(
                this,
                ProductDesriptionEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        productCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productCursorAdapter.swapCursor(null);

    }
}
