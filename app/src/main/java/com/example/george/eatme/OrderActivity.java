package com.example.george.eatme;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Order.OrderFragment;
import com.google.gson.Gson;

public class OrderActivity extends AppCompatActivity {
    Bundle bundle1,bundle2,bundle3,bundle4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        newbundle();
        setToolbar();
        setFragmentTabHost();

    }
    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public  void setFragmentTabHost(){

        //獲取TabHost控制元件
        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //設定Tab頁面的顯示區域，帶入Context、FragmentManager、Container ID
        mTabHost.setup(this, getSupportFragmentManager(), R.id.container);

        /**
         新增Tab結構說明 :
         首先帶入Tab分頁標籤的Tag資訊並可設定Tab標籤上顯示的文字與圖片，
         再來帶入Tab頁面要顯示連結的Fragment Class，最後可帶入Bundle資訊。
         **/

        //小黑人建立一個Tab，這個Tab的Tag設定為one，
        //並設定Tab上顯示的文字為第一堂課與icon圖片，Tab連結切換至
        //LessonOneFragment class，無夾帶Bundle資訊。
        mTabHost.addTab(mTabHost.newTabSpec("one")
                        .setIndicator("未確認")
                ,OrderFragment.class, bundle1);

        //同上方Tab設定，不同處為帶入參數的差異
        mTabHost.addTab(mTabHost.newTabSpec("two")
                        .setIndicator("已確認")
                ,OrderFragment.class,bundle2);

        //同上方Tab設定，不同處為帶入參數的差異
        mTabHost.addTab(mTabHost.newTabSpec("three")
                        .setIndicator("待取餐")
                ,OrderFragment.class, bundle3);

        mTabHost.addTab(mTabHost.newTabSpec("four")
                        .setIndicator("已取餐")
                ,OrderFragment.class, bundle4);

    }
    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.order_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void newbundle(){
        bundle1 = new Bundle();
        bundle1.putString("state","未確認");
        bundle2 = new Bundle();
        bundle2.putString("state","已確認");
        bundle3 = new Bundle();
        bundle3.putString("state","待取餐");
        bundle4 = new Bundle();
        bundle4.putString("state","已取餐");
    }

}
