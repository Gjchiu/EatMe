package com.example.george.eatme.Product;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.example.george.eatme.R;
import com.example.george.eatme.Store.Store;

/**
 * Created by George on 2017/7/18.
 */

public class ProductActivity extends AppCompatActivity {
    private Store store;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setToolbar();
        store = (Store)getIntent().getExtras().getSerializable("store");
        switchFragment(new ProductFragment(store));
    }
    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.porduct_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flproduct, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

}
