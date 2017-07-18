package com.example.george.eatme.Product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.george.eatme.R;

/**
 * Created by George on 2017/7/18.
 */

public class ProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setToolbar();
    }
    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.porduct_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
