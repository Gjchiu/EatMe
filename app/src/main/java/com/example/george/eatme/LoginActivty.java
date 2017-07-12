package com.example.george.eatme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Member.MemeberGetByMemIdTask;
import com.google.gson.Gson;



public class LoginActivty extends AppCompatActivity{
    EditText edaccount,edpassword;
    CheckBox checkBox;
    SharedPreferences preferences;
    private final  static String PREFERENCES_NAME = "Login";
    private final  static boolean DEFAULT_AUTO_FOUCS = true;
    private final  static int DEFAULT_AUTO_TIME = 10;
    private final static String TAG = "LoginActivty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
        checkisAutoLogin();

    }

    private void checkisAutoLogin() {
        preferences =getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("member", "");
        Member member = gson.fromJson(json, Member.class);
        if(member==null)   return;
        else if(member.getAutoLogin()==true) {
            Login(member.getMem_mail(), member.getMem_pw());
        }
    }

    private void loadPreferences() {
        SharedPreferences preferences
                = getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("MyObject", "");
        Member member = gson.fromJson(json, Member.class);

    }

    public void findView(){
        edaccount = (EditText) findViewById(R.id.account);
        edpassword = (EditText) findViewById(R.id.password);
        checkBox = (CheckBox)findViewById(R.id.LogincheckBox);
        edaccount.setText("wang@abc.com");
        edpassword.setText("123");

    }


    public void checkmember(View view) {
        String account = edaccount.getText().toString();
        String password = edpassword.getText().toString();
        Login(account,password);
    }
    private void Login(String account,String password){
        if (Common.networkConnected(this)) {
            String url = Common.URL + "MemberServlet1";
            Member member = null;

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                member = new MemeberGetByMemIdTask().execute(url,account,password).get();
                member.setAutoLogin(false);
                Log.d("member",member.toString());
                if(member!=null){
                    if(checkBox.isChecked()){
                        member.setAutoLogin(true);
                        preferences =getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor preferencesEditor = preferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(member);
                        preferencesEditor.putString("member", json);
                        preferencesEditor.commit();
                    }
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
}
