package com.example.george.eatme.Member;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.george.eatme.Common;
import com.example.george.eatme.Order.Store_Order;
import com.example.george.eatme.R;

import java.util.List;

/**
 * Created by Java on 2017/7/3.
 */

public class MemberPassFrament extends Fragment {
    private final static String TAG = "MemberPassFrament";
    Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);
        bundle =  getArguments();
        return view;
    }

}
