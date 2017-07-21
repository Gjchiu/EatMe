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
import com.example.george.eatme.Store.Store;
import com.example.george.eatme.Store.StoreGetByStoreaccTask;
import com.example.george.eatme.Store.StoreMainActivity;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Java on 2017/7/21.
 */

public class StoreLoginFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_storelogin, container, false);
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

        edaccount.setText("ABCABC50@abc.com");
        edpassword.setText("505050");
        return view;
    }

    private void Login(String account,String password){
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "StoreServlet";
            Store store = null;

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                store = new StoreGetByStoreaccTask().execute(url,account,password).get();
                store.setAutologin(false);
                Log.d("member",store.toString());
                if(store!=null){
                    if(checkBox.isChecked()){
                        store.setAutologin(true);
                        preferences =getActivity().getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor preferencesEditor = preferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(store);
                        preferencesEditor.putString("store", json);
                        preferencesEditor.commit();
                    }
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),StoreMainActivity.class);
                    startActivity(intent);
                    String welcome = "歡迎 " + store.getStore_name();
                    Toast.makeText(getActivity(),welcome,Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (store == null) {
                Common.showToast(getActivity(), "帳號不存在或密碼錯誤");
            } else {
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }

    }
}
