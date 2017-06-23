package com.example.george.eatme;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.george.eatme.Area.AreaActivity;
import com.example.george.eatme.Order.OrderFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ViewPager viewpager;
    List<Integer> list;
    Timer timer;
    LinearLayout mLinearLayout;
    View mView;
    int diatance;
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

        mView = findViewById(R.id.v_redpoint);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_points);
        initData();
        initEvent();


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



    private void initData(){
        for (int i = 0;i < list.size();i ++){
            //添加底部灰点
            View view = new View(getApplicationContext());
            view.setBackgroundResource(R.drawable.gray_circle);
            //指定其大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
            if (i != 0)
                params.leftMargin = 20;
            view.setLayoutParams(params);
            mLinearLayout.addView(view);
        }
    }
    private void initEvent() {
        /**
         * 当底部红色小圆点加载完成时测出两个小灰点的距离，便于计算后面小红点动态移动的距离
         */
        mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                diatance =  mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
                Log.d("两点间距",diatance + "测出来了");
            }
        });

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //测出页面滚动时小红点移动的距离，并通过setLayoutParams(params)不断更新其位置
                position = position % list.size();
                float leftMargin = diatance * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mView.getLayoutParams();
                params.leftMargin = Math.round(leftMargin+470);
                mView.setLayoutParams(params);
                Log.d("红点在这",leftMargin + "");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainbody, fragment);
        fragmentTransaction.commit();
    }

    public List<Integer> getList(){
        list = new ArrayList<Integer>();
        list.add(R.drawable.p1);
        list.add(R.drawable.p2);
        list.add(R.drawable.p3);
        return list;
    }
}
