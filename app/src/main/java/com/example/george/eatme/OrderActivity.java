package com.example.george.eatme;

import android.Manifest;
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
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.george.eatme.Order.OrderFragment;

import java.util.HashSet;
import java.util.Set;


public class OrderActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setToolbar();
        setFragmentTabHost();
//        Fragment fragment = new OrderFragment();
//        switchFragment(fragment);

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
                        .setIndicator("全部訂單")
                ,OrderFragment.class,null);

        //同上方Tab設定，不同處為帶入參數的差異
        mTabHost.addTab(mTabHost.newTabSpec("two")
                        .setIndicator("未取餐")
                ,OrderFragment.class,null);

        //同上方Tab設定，不同處為帶入參數的差異
        mTabHost.addTab(mTabHost.newTabSpec("three")
                        .setIndicator("已取餐")
                ,OrderFragment.class, null);
    }
    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.order_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
