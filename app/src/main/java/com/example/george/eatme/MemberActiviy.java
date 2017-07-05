package com.example.george.eatme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Member.MemberFragment;

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
        member = (Member) getIntent().getSerializableExtra("member");
        setToolbar();
        MemberFragment memberFragment = new MemberFragment();
        switchFragment(memberFragment);
        bundle = new Bundle();
        bundle.putSerializable("member",member);
        memberFragment.setArguments(bundle);

    }
    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.order_toolbar);
        toolbar.setTitle("會員資料");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //do whatever
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("member",member);
                intent.putExtras(bundle);
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.memlayout, fragment);
        fragmentTransaction.commit();
    }
}
