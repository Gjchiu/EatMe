package com.example.george.eatme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Member.MemeberGetByMemIdTask;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Java on 2017/7/21.
 */

public class MemberLoginFragment extends Fragment {
    EditText edaccount,edpassword;
    CheckBox checkBox;
    SharedPreferences preferences;
    Button button;
    private final  static String PREFERENCES_NAME = "Login";
    private final  static boolean DEFAULT_AUTO_FOUCS = true;
    private final  static int DEFAULT_AUTO_TIME = 10;
    private final static String TAG = "Login2Activty";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memlogin, container, false);
        edaccount = (EditText) view.findViewById(R.id.account);
        edpassword = (EditText)view. findViewById(R.id.password);
        checkBox = (CheckBox)view.findViewById(R.id.LogincheckBox);
        button = (Button)view.findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = edaccount.getText().toString();
                String password = edpassword.getText().toString();
                Login(account,password);
            }
        });

        edaccount.setText("wang@abc.com");
        edpassword.setText("group1");
        return view;
    }
    private void checkisAutoLogin() {
        preferences =getActivity().getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE);
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
                = getActivity().getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("MyObject", "");
        Member member = gson.fromJson(json, Member.class);

    }
    private void Login(String account,String password){
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "MemberServlet";
            Member member = null;

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                member = new MemeberGetByMemIdTask().execute(url,account,password).get();
                member.setAutoLogin(false);
                Log.d("member",member.toString());
                if(member!=null){
                    if(checkBox.isChecked()){
                        member.setAutoLogin(true);
                        preferences =getActivity().getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor preferencesEditor = preferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(member);
                        preferencesEditor.putString("member", json);
                        preferencesEditor.commit();
                    }
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),MainActivity.class);
                    startActivity(intent);
                    String welcome = "歡迎 " + member.getMem_name();
                    Toast.makeText(getActivity(),welcome,Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (member == null) {
                Common.showToast(getActivity(), "帳號不存在或密碼錯誤");
            } else {
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }

    }
}
