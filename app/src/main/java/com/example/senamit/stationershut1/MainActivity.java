package com.example.senamit.stationershut1;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.senamit.stationershut1.data.StationaryContract.*;
import com.example.senamit.stationershut1.data.*;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    String LOG_TAG = MainActivity.class.getSimpleName();
    Toolbar toolbar_bottom;
    mDbHelper stationaryDbHelper;
    Cursor cursor;
    ListView listViewProduct;
    private static int PRODUCT_LOADER=0;
    ProductCursorAdapter productCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar_bottom = (Toolbar)findViewById(R.id.toolbar_bottom);
        setSupportActionBar(toolbar_bottom);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       setupEvenlyDistributedToolbar();

        stationaryDbHelper = new mDbHelper(MainActivity.this);

        FloatingActionButton fabButton = (FloatingActionButton)findViewById(R.id.fab_button);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG_TAG, "intent created");
                Intent intent = new Intent(MainActivity.this, ShundramItemList.class);
                startActivity(intent);
            }
        });

        listViewProduct = (ListView)findViewById(R.id.list_view_product);
        productCursorAdapter = new ProductCursorAdapter(this, cursor);
        listViewProduct.setAdapter(productCursorAdapter);
        getLoaderManager().initLoader(PRODUCT_LOADER,null, this );
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//       readProducteDesctiption();
//
//    }

//    private void readProducteDesctiption() {

//        String[] projection = {ProductDesriptionEntry._ID, ProductDesriptionEntry.COLUMN_PRODUCT_NAME, ProductDesriptionEntry.COLUMN_PRODUCT_PRICE,
//        ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY};
//
//        cursor = getContentResolver().query(
//                ProductDesriptionEntry.CONTENT_URI,
//                projection,
//                null,
//                null,
//                null );


//
//    }

    private void setupEvenlyDistributedToolbar() {

        // Use Display metrics to get Screen Dimensions
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        // Toolbar
//        mToolbar = (Toolbar) findViewById(R.id.navigationToolbar);
        // Inflate your menu
        toolbar_bottom.inflateMenu(R.menu.menu_buttom);

        // Add 10 spacing on either side of the toolbar
        toolbar_bottom.setContentInsetsAbsolute(10, 10);

        // Get the ChildCount of your Toolbar, this should only be 1
        int childCount = toolbar_bottom.getChildCount();
        // Get the Screen Width in pixels
        int screenWidth = metrics.widthPixels;

        // Create the Toolbar Params based on the screenWidth
        Toolbar.LayoutParams toolbarParams = new Toolbar.LayoutParams(screenWidth, Toolbar.LayoutParams.WRAP_CONTENT);

        // Loop through the child Items
        for(int i = 0; i < childCount; i++){
            // Get the item at the current index
            View childView = toolbar_bottom.getChildAt(i);
            // If its a ViewGroup
            if(childView instanceof ViewGroup){
                // Set its layout params
                childView.setLayoutParams(toolbarParams);
                // Get the child count of this view group, and compute the item widths based on this count & screen size
                int innerChildCount = ((ViewGroup) childView).getChildCount();
                int itemWidth  = (screenWidth / innerChildCount);
                // Create layout params for the ActionMenuView
                ActionMenuView.LayoutParams params = new ActionMenuView.LayoutParams(itemWidth, Toolbar.LayoutParams.WRAP_CONTENT);
                // Loop through the children
                for(int j = 0; j < innerChildCount; j++){
                    View grandChild = ((ViewGroup) childView).getChildAt(j);
                    if(grandChild instanceof ActionMenuItemView){
                        // set the layout parameters on each View
                        grandChild.setLayoutParams(params);
                    }
                }
            }
        }
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
