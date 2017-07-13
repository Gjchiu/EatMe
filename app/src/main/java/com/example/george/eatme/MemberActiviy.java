package com.example.george.eatme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Member.MemberFragment;
import com.google.gson.Gson;

/**
 * Created by Java on 2017/6/30.
 */

public class MemberActiviy extends AppCompatActivity{
    Member member;
    Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        loadPreferences();
        setToolbar();
        MemberFragment memberFragment = new MemberFragment();
        switchFragment(memberFragment);
        bundle = new Bundle();
        bundle.putSerializable("member",member);
        memberFragment.setArguments(bundle);

    }
    public void setToolbar(){
            Toolbar toolbar = (Toolbar) findViewById(R.id.member_toolbar);
            toolbar.setTitle("會員資料");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.memlayout, fragment);
        fragmentTransaction.commit();
    }
    private void loadPreferences() {
        SharedPreferences preferences
                = getSharedPreferences("Login",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("member", "");
        member = gson.fromJson(json, Member.class);
    }
}
