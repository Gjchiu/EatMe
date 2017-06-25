package com.example.george.eatme;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.example.george.eatme.Area.AreaActivity;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ViewPager viewpager;
    List<Integer> list;
    Timer timer;
    PageIndicator pageIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.options_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.set) {
                    Toast.makeText(MainActivity.this , "SET" , Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.back) {
                    Toast.makeText(MainActivity.this, "Back", Toast.LENGTH_SHORT).show();
                }
                    return true;
            }
        });

        viewpager = (ViewPager)findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,getList());
        viewpager.setAdapter(viewPagerAdapter);

        pageIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        pageIndicator.setViewPager(viewpager);




        timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(),2000,3000);


    }

    public void covertArea(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, AreaActivity.class);
        startActivity(intent);
    }

    public void convertToOrder(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, OrderActivity.class);
        startActivity(intent);
    }

    public class MyTimeTask extends TimerTask{
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewpager.getCurrentItem()==0){
                        viewpager.setCurrentItem(1);
                    }
                    else if(viewpager.getCurrentItem()==(1)){
                        viewpager.setCurrentItem(2);
                    }
                    else viewpager.setCurrentItem(0);
                }
            });
        }
    }



    public List<Integer> getList(){
        list = new ArrayList<Integer>();
        list.add(R.drawable.p1);
        list.add(R.drawable.p2);
        list.add(R.drawable.p3);
        return list;
    }
}
