package com.example.george.eatme.Store;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;


import com.example.george.eatme.Common;
import com.example.george.eatme.R;
import com.example.george.eatme.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by George on 2017/7/16.
 */

public class StoreActivity extends AppCompatActivity {
    private List<StoreClass> list;
    private List<android.support.v4.app.Fragment> fragmentList ;
    private SlidingTabLayout slideTab;
    private ViewPager viewpager;
    private MyAdapter adapter;
    private String area;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        setToolbar();
        getStoreclass();
        init();
        adapter = new MyAdapter(getSupportFragmentManager(),list,fragmentList);
        viewpager.setAdapter(adapter);
        slideTab.setViewPager(viewpager);
    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.store_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void init(){
        slideTab = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        viewpager = (ViewPager)findViewById(R.id.vpstore);
        area = this.getIntent().getStringExtra("area");
        fragmentList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            fragmentList.add(new StoreFragment(list.get(i).getSc_name(),area));
        }
    }



    private void getStoreclass(){
        if (Common.networkConnected(this)) {
            String url = Common.URL + "StoreClassServlet";

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                list = new StoreClassGetAllTask().execute(url).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            progressDialog.cancel();

        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
    }
    public class MyAdapter extends FragmentPagerAdapter {

        private List<StoreClass> titleList;
        private List<android.support.v4.app.Fragment> fragmentList;

        public MyAdapter(FragmentManager fm, List<StoreClass> titleList, List<android.support.v4.app.Fragment> fragmentList) {
            super(fm);
            this.titleList = titleList;
            this.fragmentList = fragmentList;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position).getSc_name();
        }
    }

}
