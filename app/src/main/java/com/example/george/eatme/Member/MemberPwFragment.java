package com.example.george.eatme.Member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.george.eatme.R;


/**
 * Created by Java on 2017/7/3.
 */

public class MemberPwFragment extends Fragment {
    private final static String TAG = "MemberPwFragment";
    Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);
        bundle =  getArguments();
        return view;
    }

}
