package com.example.george.eatme.Member;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.george.eatme.Common;
import com.example.george.eatme.R;

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
        final EditText edtpw = (EditText)view.findViewById(R.id.edtnewpw);
        final EditText edtnewpw = (EditText)view.findViewById(R.id.edtnewpw);
        final EditText edtpwcheck = (EditText)view.findViewById(R.id.edtpwcheck);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtpw.getText().equals(member.getMem_pw())){
                    Toast.makeText(getActivity(),"密碼錯誤",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(edtnewpw.getText().equals(edtpwcheck.getText())){
                    Toast.makeText(getActivity(),"確認密碼錯誤",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (Common.networkConnected(getActivity())) {
                    String url = Common.URL + "MemberServlet1";

                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                    try {
                        member = new MemberUpdatePwTask().execute(url,member.getMem_mail(),edtnewpw.getText()).get();
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

}
