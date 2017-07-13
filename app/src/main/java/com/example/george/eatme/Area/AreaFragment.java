package com.example.george.eatme.Area;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Member.MemberFragment;
import com.example.george.eatme.Member.MemberPwFragment;
import com.example.george.eatme.R;

/**
 * Created by Java on 2017/7/13.
 */

public class AreaFragment extends Fragment {
    private final static String TAG = "AreaFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choosearea, container, false);
        setView(view);
        return view;
    }
    public void setView(View view){
        ListView listView =(ListView)view.findViewById(R.id.lvarea);
        listView.setAdapter(new AreaAdapter(getActivity()));
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.memlayout, fragment);
        fragmentTransaction.commit();
    }

}
