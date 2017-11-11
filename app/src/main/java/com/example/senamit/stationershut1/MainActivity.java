package com.example.senamit.stationershut1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;

import com.example.senamit.stationershut1.data.StationaryContract.*;
import com.example.senamit.stationershut1.data.*;

public class MainActivity extends AppCompatActivity {
    String LOG_TAG = MainActivity.class.getSimpleName();
    Toolbar toolbar_bottom;
    mDbHelper stationaryDbHelper;
    Button btnAddProduct;
    TextView txtProductDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar_bottom = (Toolbar)findViewById(R.id.toolbar_bottom);
        setSupportActionBar(toolbar_bottom);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       setupEvenlyDistributedToolbar();


        txtProductDetails = (TextView)findViewById(R.id.txt_product_description);
        btnAddProduct = (Button) findViewById(R.id.btn_add_product);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(LOG_TAG, "intent created");
                Intent intent = new Intent(MainActivity.this, ShundramItemList.class);
                startActivity(intent);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
     Cursor cursor =    readProducteDesctiption();
        DisplayProdcutDetails(cursor);

    }

    private Cursor readProducteDesctiption() {
        Cursor cursor;

        stationaryDbHelper = new mDbHelper(MainActivity.this);

        SQLiteDatabase database  = stationaryDbHelper.getReadableDatabase();
//        cursor = database.rawQuery("SELECT * FROM " + ProductDesriptionEntry.TABLE_NAME, null);
//        int num =  cursor.getCount();
//        productCount.setText("The total num is" + num);

        String[] projection = {ProductDesriptionEntry._ID, ProductDesriptionEntry.COLUMN_PRODUCT_NAME, ProductDesriptionEntry.COLUMN_PRODUCT_PRICE,
        ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY};
       cursor = database.query(ProductDesriptionEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);


        return cursor;
    }

    private  void DisplayProdcutDetails(Cursor cursor) {

        try {
            txtProductDetails.setText("the total number of product is " + cursor.getCount() );

            int indexProductId = cursor.getColumnIndex(ProductDesriptionEntry._ID);
            int indexProductName = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_NAME);
            int indexProductPrice = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_PRICE);
            int indexProductQuantity = cursor.getColumnIndex(ProductDesriptionEntry.COLUMN_PRODUCT_QUANTITY);
            Log.i(LOG_TAG, "before if statement" + indexProductId + "   product name  " + indexProductName);

            if (cursor != null ){


                Log.i(LOG_TAG,"the total numbr of rows is  "+cursor.getCount());
           Log.i(LOG_TAG, "index number of product id is  " + indexProductId + "   product name  " + indexProductName);
            while (cursor.moveToNext()) {

                int productId = cursor.getInt(indexProductId);
                String productName = cursor.getString(indexProductName);
                int productPrice = cursor.getInt(indexProductPrice);
                int productQuantity = cursor.getInt(indexProductQuantity);

                txtProductDetails.append("\n" + productId + " - " + productName + " - " + productPrice + " - " + productQuantity);
            }
            }


        }finally {
            cursor.close();
        }
    }

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
}
