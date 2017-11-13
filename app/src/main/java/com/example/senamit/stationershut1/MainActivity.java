package com.example.senamit.stationershut1;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.senamit.stationershut1.data.StationaryContract.*;
import com.example.senamit.stationershut1.data.*;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    String LOG_TAG = MainActivity.class.getSimpleName();

    mDbHelper stationaryDbHelper;
    Cursor cursor;
    ListView listViewProduct;
    private static int PRODUCT_LOADER=0;
    ProductCursorAdapter productCursorAdapter;




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
        productCursorAdapter = new ProductCursorAdapter(this, cursor);
        listViewProduct.setAdapter(productCursorAdapter);


        Log.i(LOG_TAG, "ouside onitemclicklistener");



        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
     @Override
     public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
//
//         long viewId = view.getId();
//
//         Log.i(LOG_TAG, "inside onitemclicklistener "+viewId);
//
//         if (viewId == R.id.btn_sale){
//             Toast.makeText(MainActivity.this, "inside button click ", Toast.LENGTH_LONG).show();
//             Log.i(LOG_TAG, "inside button click event");
//         }

         Intent intent = new Intent(MainActivity.this, ProductDesription.class);
         Uri currentProductUri = ContentUris.withAppendedId(ProductDesriptionEntry.CONTENT_URI, id);
         intent.setData(currentProductUri);
         startActivity(intent);


     }
 });
//
//        Intent intent = getIntent();
//        currentProductUri =  intent.getData();

        getLoaderManager().initLoader(PRODUCT_LOADER, null, this);

    }



    private void fabButtonAddProduct() {
        Log.i(LOG_TAG, "intent created");
        Intent intent = new Intent(MainActivity.this, ShundramItemList.class);
        startActivity(intent);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_buttom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){

            case R.id.shundaram:
               intent = new Intent(MainActivity.this, ShundramItemList.class);
                startActivity(intent);
                return true;
            case R.id.lexi:
                intent = new Intent(MainActivity.this, LexiItemList.class);
                startActivity(intent);
                return true;
            case R.id.other:
                intent= new Intent(MainActivity.this, OthersItemList.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {ProductDesriptionEntry._ID, ProductDesriptionEntry.COLUMN_PRODUCT_NAME, ProductDesriptionEntry.COLUMN_PRODUCT_PRICE,
                ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY};

        //cursorLoader will call the contentProvider....one extra parameter here is context
        CursorLoader cursorLoader = new CursorLoader(
                this,
                ProductDesriptionEntry.CONTENT_URI,
                projection,
                null,
                null,
                null );

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
