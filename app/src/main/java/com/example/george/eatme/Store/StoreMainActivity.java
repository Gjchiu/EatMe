package com.example.george.eatme.Store;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.george.eatme.Common;
import com.example.george.eatme.Login2Activity;
import com.example.george.eatme.Order.OrderUpdateTask;

import com.example.george.eatme.OrderActivity;
import com.example.george.eatme.R;
import com.google.gson.Gson;

import static android.content.ContentValues.TAG;

public class StoreMainActivity extends AppCompatActivity {
    private static final String PACKAGE = "com.google.zxing.client.android";
    private static final int REQUEST_BARCODE_SCAN = 0;
    private Button btnScan,btnorder;
    private Store sotre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acyivity_storemain);

        loadPreferences();
        findViews();

    }

    private void findViews() {
        btnScan = (Button) findViewById(R.id.btnScan);

        btnScan.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(
                        "com.google.zxing.client.android.SCAN");
                try {
                    startActivityForResult(intent, REQUEST_BARCODE_SCAN);
                }
                // 如果沒有安裝Barcode Scanner，就跳出對話視窗請user安裝
                catch (ActivityNotFoundException e) {
                    showDownloadDialog();
                }
            }

        });

        btnorder = (Button) findViewById(R.id.btnorder);
        btnorder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                bundle.putSerializable("sotre",sotre);
                intent.putExtras(bundle);
                intent.setClass(StoreMainActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_BARCODE_SCAN) {
            String message = "";
            String contents = null;
            if (resultCode == RESULT_OK) {
                contents = intent.getStringExtra("SCAN_RESULT");
                String resultFormat = intent.getStringExtra("SCAN_RESULT_FORMAT");
                message = ("Content: " + contents + "\nResult format: " + resultFormat);
            } else if (resultCode == RESULT_CANCELED) {
                message = "Scan was Cancelled!";
            }
            Boolean bool = getresult(contents);
            if(bool==true){
                DialogEvent();
            }
        }
    }

    private void showDownloadDialog() {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(this);
        downloadDialog.setTitle("No Barcode Scanner Found");
        downloadDialog
                .setMessage("Please download and install Barcode Scanner!");
        downloadDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("market://search?q=pname:"
                                + PACKAGE);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException ex) {
                            Log.e(ex.toString(),
                                    "Play Store is not installed; cannot install Barcode Scanner");
                        }
                    }
                });
        downloadDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        downloadDialog.show();
    }

    public void logout(View view) {
        normalDialogEvent();
    }
    private void normalDialogEvent(){
        new android.support.v7.app.AlertDialog.Builder(this)
                .setTitle("登出")
                .setMessage("確定要登出")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setNeutralButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removePreferences();
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(),Login2Activity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }
    private void removePreferences() {
        SharedPreferences preferences
                =getSharedPreferences("Login",MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.clear();
        preferencesEditor.commit();

    }

    private void loadPreferences() {
        SharedPreferences preferences
                = getSharedPreferences("Login",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("store", "");
        sotre = gson.fromJson(json, Store.class);
    }

    private Boolean getresult(String message) {
        Boolean bool = false;
        if (Common.networkConnected(this)) {
            String url = Common.URL + "Store_OrderServlet";
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {

                bool = new OrderUpdateTask().execute(url,message,sotre.getStore_id()).get();

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            progressDialog.cancel();

        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
        return bool;
    }
    private void DialogEvent(){
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setTitle("訂單");
        dialog.setMessage("訂單狀態UPDATE成功!");
        dialog.setPositiveButton("確定",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialoginterface, int i){
                    }
                });
        dialog.show();
    }
}
