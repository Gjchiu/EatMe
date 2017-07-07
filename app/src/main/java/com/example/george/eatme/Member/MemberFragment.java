package com.example.george.eatme.Member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.george.eatme.R;

/**
 * Created by Java on 2017/7/4.
 */

public class MemberFragment extends Fragment {
    private final static String TAG = "MemberFragment";
    Member member;
    Bundle bundle;
    MemberFragment memberFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member, container, false);
        member = (Member) getArguments().getSerializable("member");
        setTextView(view);
        return view;
    }
    public void setTextView(View view){
        TextView tvmemname =(TextView)view.findViewById(R.id.tvmemname);
        TextView tvmemmail =(TextView)view.findViewById(R.id.tvmemmail);
        TextView tvmemphone =(TextView)view.findViewById(R.id.tvmemphone);
        TextView tvmemstate =(TextView)view.findViewById(R.id.tvmemstate);
        Button button =(Button)view.findViewById(R.id.btnupdatepw);
        tvmemname.setText(member.getMem_name());
        tvmemmail.setText(member.getMem_mail());
        tvmemphone.setText(member.getMem_phone());
        tvmemstate.setText(member.getMem_state());
        memberFragment = new MemberFragment();
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switchFragment(new MemberPwFragment());
//                bundle = new Bundle();
//                bundle.putSerializable("member",member);
//                memberFragment.setArguments(bundle);
//            }
//        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.memlayout, fragment);
        fragmentTransaction.commit();
    }

}
