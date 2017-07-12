package com.example.george.eatme;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.george.eatme.Order.*;
import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Order.Store_Order;
import com.example.george.eatme.Order.TakeOrderRecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by George on 2017/7/12.
 */

public class TakeOrderActivity extends AppCompatActivity {
    private final static String TAG = "TakeOrderActivity";
    private RecyclerView rvtakeorder;
    private Member member;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeorder);
        loadPreferences();
        setToolbar();
        rvtakeorder = (RecyclerView) findViewById(R.id.rvtakeorder);
        rvtakeorder.setLayoutManager(new LinearLayoutManager(this));
        getorders();
    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.takeorder_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getorders(){
        if (Common.networkConnected(this)) {
            String url = Common.URL + "Store_OrderServlet1";
            List<Store_Order> orderList = null;

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                orderList =new OrderGetByTakeTask().execute(url,member.getMem_id()).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (orderList == null || orderList.isEmpty()) {
                Common.showToast(this, "無訂單");
            } else {
                TakeOrderRecyclerViewAdapter takeOrderRecyclerViewAdapter =  new TakeOrderRecyclerViewAdapter(this, orderList);
                rvtakeorder.setAdapter(takeOrderRecyclerViewAdapter);
            }
            progressDialog.cancel();

        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
    }
    private void loadPreferences() {
        SharedPreferences preferences
                = getSharedPreferences("Login",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("member", "");
        member = gson.fromJson(json, Member.class);
        Log.d("member111",member.getMem_id());
    }
}
