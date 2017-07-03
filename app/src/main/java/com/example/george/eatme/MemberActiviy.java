package com.example.george.eatme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Member.MemberPassFrament;

import java.util.List;


/**
 * Created by Java on 2017/6/30.
 */

public class MemberActiviy extends AppCompatActivity{
    Member member;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        member = (Member) getIntent().getSerializableExtra("member");
        setToolbar();
        ListView listview =(ListView)findViewById(R.id.lvmember);
        listview.setAdapter(new MemberAdapter(this,member));
        setTextView();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switchFragment(new MemberPassFrament());
            }
        });
    }
    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.order_toolbar);
        toolbar.setTitle("訂單查詢");
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
    public void setTextView(){
//        TextView tvmemname =(TextView)findViewById(R.id.tvmemname);
//        TextView tvmemmail =(TextView)findViewById(R.id.tvmemmail);
//        TextView tvmemphone =(TextView)findViewById(R.id.tvmemphone);
//        TextView tvmemstate =(TextView)findViewById(R.id.tvmemstate);

//        tvmemname.setText(member.getMem_name());
//        tvmemmail.setText(member.getMem_mail());
//        tvmemphone.setText(member.getMem_phone());
//        tvmemstate.setText(member.getMem_state());
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.memlayout, fragment);
        fragmentTransaction.commit();
    }

    private class MemberAdapter extends BaseAdapter {
        Context context;
        Member member;

        MemberAdapter(Context context,Member member){
            this.context = context;
            this.member =member;
            Log.d("member111",this.member.getMem_name());
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return member;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                view = layoutInflater.inflate(R.layout.view_item_member,viewGroup,false);
            }
            TextView tvmempassword =(TextView)findViewById(R.id.tvmempass);
            return view;
        }
    }
}
