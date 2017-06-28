package com.example.george.eatme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.george.eatme.Area.AreaAdapter;

/**
 * Created by George on 2017/6/20.
 */

public class OedrMealActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermeal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.area_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView areaview = (ListView)findViewById(R.id.areaview);


        areaview.setAdapter(new AreaAdapter(this));
//        areaview.setOnClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
    }


}
