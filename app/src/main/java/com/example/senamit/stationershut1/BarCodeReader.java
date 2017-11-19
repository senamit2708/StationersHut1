package com.example.senamit.stationershut1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

//this is about how to add the barcode in your android project
//I am going to add here the zxing library
//lets start

public class BarCodeReader extends AppCompatActivity {

    public static final int REQUEST_CAMERA=1;
    ZXingScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_reader);
        scanCode();
    }

   public void scanCode(){
        //so what we have done here....
       //first we have created a object of ZxingScannerView
       //then we create a object of implemented class...and call it through useing setResultHandler
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZxingScannerResultHandler());
        setContentView(scannerView);
        scannerView.startCamera();
   }


    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    class ZxingScannerResultHandler implements ZXingScannerView.ResultHandler {


       @Override
       public void handleResult(Result result) {

           String resultCode = result.getText();
           Toast.makeText(BarCodeReader.this, "The result is "+resultCode, Toast.LENGTH_LONG).show();
           setContentView(R.layout.activity_bar_code_reader);
           scannerView.stopCamera();

       }
   }

}
