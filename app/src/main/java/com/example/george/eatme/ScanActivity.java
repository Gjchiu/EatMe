package com.example.george.eatme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);  
        setContentView(mScannerView);               
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); 
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           
    }

    @Override
    public void handleResult(Result rawResult) {
   
    }
}
