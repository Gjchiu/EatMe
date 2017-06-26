package com.example.george.eatme.Order;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.george.eatme.Common;
import com.example.george.eatme.OrderActivity;
import com.example.george.eatme.R;

import java.util.List;

import static android.R.attr.data;


/**
 * Created by George on 2017/6/22.
 */

public class OrderFragment extends android.support.v4.app.Fragment{
    private final static String TAG = "OrderFragment";
    private RecyclerView rvOrder;
    private boolean[] orderExpanded;
    Bundle bundle =  getArguments();;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment, container, false);
        rvOrder = (RecyclerView) view.findViewById(R.id.rvOrders);
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
            if (Common.networkConnected(getActivity())) {
                String url = Common.URL + "Store_OrderServlet1";
                List<Store_Order> orderList = null;

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
//                if(bundle.getString("action").equals("getAll")){
                    orderList = new OrderGetAllTask().execute(url).get();
//                }
//                    orderList = new OrderGetByStateTask().execute(url).get();


            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (orderList == null || orderList.isEmpty()) {
                Common.showToast(getActivity(), "沒連線");
            } else {
                OrderRecyclerViewAdapter orderRecyclerViewAdapter =  new OrderRecyclerViewAdapter(getActivity(), orderList);
                rvOrder.setAdapter(orderRecyclerViewAdapter);
                orderRecyclerViewAdapter.setOnItemClickListener(new OrderRecyclerViewAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view , int position){
                        Log.d("Recycle",String.valueOf(position));
                    }
                });
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }
}
