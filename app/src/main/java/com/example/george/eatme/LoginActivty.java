package com.example.george.eatme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Member.MemeberGetByMemIdTask;


public class LoginActivty extends AppCompatActivity{
    EditText edaccount,edpassword;
    private final static String TAG = "LoginActivty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();
    }

    public void findView(){
        edaccount = (EditText) findViewById(R.id.account);
        edpassword = (EditText) findViewById(R.id.password);
    }


    public void checkmember(View view) {
        String account = edaccount.getText().toString();
        String password = edpassword.getText().toString();

        if (Common.networkConnected(this)) {
            String url = Common.URL + "MemberServlet1";
            Member member = null;

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {

                member = new MemeberGetByMemIdTask().execute(url,account).get();
                if(account.equals(member.getMem_mail())||password.equals(member.getMem_pw())){
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent();
                    intent.setClass(this,MainActivity.class);
                    bundle.putSerializable("member",member);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (member == null) {
                Common.showToast(this, "帳號  密碼錯誤");
            } else {
            }
            progressDialog.cancel();

        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }

    }
}
