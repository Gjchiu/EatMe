package com.example.george.eatme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.george.eatme.Area.AreaFragment;

/**
 * Created by George on 2017/6/20.
 */

public class AreaActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        setToolbar();
        switchFragment(new AreaFragment());
    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.ordermeal_toolbar);
        TextView tvredermeal = (TextView)findViewById(R.id.tvordermealtitle);
        tvredermeal.setText("選擇地區");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ordermal_container, fragment);
        fragmentTransaction.commit();
    }
}
