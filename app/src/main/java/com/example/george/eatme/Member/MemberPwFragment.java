package com.example.george.eatme.Member;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.george.eatme.Common;
import com.example.george.eatme.R;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Java on 2017/7/3.
 */

public class MemberPwFragment extends Fragment {
    private final static String TAG = "MemberPwFragment";
    Bundle bundle;
    Member member;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_password, container, false);
        member = (Member)getArguments().getSerializable("member");

        Button button = (Button)view.findViewById(R.id.btnupdatepass);
        final EditText edtpw = (EditText)view.findViewById(R.id.edtpw);
        final EditText edtnewpw = (EditText)view.findViewById(R.id.edtnewpw);
        final EditText edtpwcheck = (EditText)view.findViewById(R.id.edtpwcheck);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(edtpw.getText().toString().trim().equals(member.getMem_pw()))){
                    Toast.makeText(getActivity(),"密碼錯誤",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!(edtnewpw.getText().toString().equals(edtpwcheck.getText().toString()))){
                    Toast.makeText(getActivity(),"確認密碼錯誤",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (Common.networkConnected(getActivity())) {
                    String url = Common.URL + "MemberServlet";

                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    try {
                        member = new MemberUpdatePwTask().execute(url,member.getMem_mail(),edtnewpw.getText().toString()).get();
                        savePreferences();
                        getActivity().getFragmentManager().popBackStack(null,0);
                        Toast.makeText(getActivity(),"密碼更改成功",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    progressDialog.cancel();
                } else {
                    Common.showToast(getActivity(), R.string.msg_NoNetwork);
                }

            }
        });
        return view;
    }

    private void savePreferences() {
        SharedPreferences preferences
                = getActivity().getSharedPreferences("Login",MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(member);
        preferencesEditor.putString("member", json);
        preferencesEditor.commit();

    }

}
