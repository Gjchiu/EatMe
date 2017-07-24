package com.example.george.eatme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Member.MemeberGetByMemIdTask;
import com.example.george.eatme.MemberLoginFragment;
import com.example.george.eatme.R;
import com.example.george.eatme.Store.Store;
import com.example.george.eatme.Store.StoreGetByStoreaccTask;
import com.example.george.eatme.Store.StoreMainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Java on 2017/7/20.
 */

public class Login2Activity extends AppCompatActivity {
    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    private Class fragmentArray[] = {MemberLoginFragment.class, MemberLoginFragment.class};
    private String TagArray[] = {"one", "two"};
    private String textViewArray[] = {"會員登入", "商家登入"};
    private final static String TAG = "LoginActivty";
    private List<Fragment> list = new ArrayList<Fragment>();
    private ViewPager vp;
    SharedPreferences preferences;
    private final static String PREFERENCES_NAME = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        checkisAutoLogin();
        setFragmentTabHost();
    }

    public void setFragmentTabHost() {

        //獲取TabHost控制元件
        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //設定Tab頁面的顯示區域，帶入Context、FragmentManager、Container ID
        mTabHost.setup(this, getSupportFragmentManager(), R.id.container);

        /**
         新增Tab結構說明 :
         首先帶入Tab分頁標籤的Tag資訊並可設定Tab標籤上顯示的文字與圖片，
         再來帶入Tab頁面要顯示連結的Fragment Class，最後可帶入Bundle資訊。
         **/

        //小黑人建立一個Tab，這個Tab的Tag設定為one，
        //並設定Tab上顯示的文字為第一堂課與icon圖片，Tab連結切換至
        //LessonOneFragment class，無夾帶Bundle資訊。
        mTabHost.addTab(mTabHost.newTabSpec("one")
                        .setIndicator("會員登入")
                , MemberLoginFragment.class, null);

        //同上方Tab設定，不同處為帶入參數的差異
        mTabHost.addTab(mTabHost.newTabSpec("two")
                        .setIndicator("商家登入")
                , StoreLoginFragment.class, null);

    }

    private void checkisAutoLogin() {
        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("member", "");
        Member member = gson.fromJson(json, Member.class);
        String json2 = preferences.getString("store", "");
        Store store = gson.fromJson(json2, Store.class);
        if (member != null && member.getAutoLogin() == true){
            LoginMem(member.getMem_mail(), member.getMem_pw());
        }
        else if (store != null&&store.getAutologin() == true) {
            LoginStore(store.getStore_acc(), store.getStore_pw());
        } else return;
    }
    private void LoginMem(String account,String password){
        if (Common.networkConnected(this)) {
            String url = Common.URL + "MemberServlet";
            Member member = null;

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                member = new MemeberGetByMemIdTask().execute(url,account,password).get();
                member.setAutoLogin(false);
                Log.d("member",member.toString());
                if(member!=null){
                    Intent intent = new Intent();
                    intent.setClass(this,MainActivity.class);
                    startActivity(intent);
                    String welcome = "歡迎 " + member.getMem_name();
                    Toast.makeText(this,welcome,Toast.LENGTH_LONG).show();
                    finish();
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (member == null) {
                Common.showToast(this, "帳號不存在或密碼錯誤");
            } else {
            }
            progressDialog.cancel();

        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }

    }
    private void LoginStore(String account,String password){
        if (Common.networkConnected(this)) {
            String url = Common.URL + "StoreServlet";
            Store store = null;

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                store = new StoreGetByStoreaccTask().execute(url,account,password).get();
                store.setAutologin(false);
                Log.d("member",store.toString());
                if(store!=null){
                    Intent intent = new Intent();
                    intent.setClass(this,StoreMainActivity.class);
                    startActivity(intent);
                    String welcome = "歡迎 " + store.getStore_name();
                    Toast.makeText(this,welcome,Toast.LENGTH_LONG).show();
                    this.finish();
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (store == null) {
                Common.showToast(this, "帳號不存在或密碼錯誤");
            } else {
            }
            progressDialog.cancel();

        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }

    }
}
