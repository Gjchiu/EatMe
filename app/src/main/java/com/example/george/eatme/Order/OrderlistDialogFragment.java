package com.example.george.eatme.Order;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.george.eatme.Common;
import com.example.george.eatme.Product.ProductActivity;
import com.example.george.eatme.R;
import com.example.george.eatme.Store.Store;
import com.example.george.eatme.Store.StoreActivity;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Java on 2017/7/13.
 */

public class OrderlistDialogFragment extends DialogFragment {
    private final static String TAG = "OrderlistDialogFragment";
    private Store_Order order;
    private List<Orderlist> orderlist;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //不顯示標題
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //載入xml layout
        View view = inflater.inflate(R.layout.dialog_orederlist, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String orderid = getArguments().getString("orderid");
        order = (Store_Order) getArguments().getSerializable("order");
        orderlist = (List<Orderlist>) getArguments().getSerializable("orderlists");
        ListView listView = (ListView)view.findViewById(R.id.lvorderlist);
        TextView tvtakeway = (TextView)view.findViewById(R.id.tvtakeway);
        TextView tvtaketime = (TextView)view.findViewById(R.id.tvtaketime);
        TextView tvaddress = (TextView)view.findViewById(R.id.tvaddress);
        TextView tvtotal = (TextView)view.findViewById(R.id.tvtotal);
        listView.setAdapter(new OrderlistAdapter(orderlist));
        tvtotal.setText("總金額   " + order.getTotalprice().toString());
        tvtakeway.setText("取餐方式     "+order.getOrder_way());
        tvtaketime.setText("取餐時間\n" + order.getOrder_taketime());
        if(order.getOrder_way().equals("外送")){
            tvaddress.setVisibility(View.VISIBLE);
            tvaddress.setText("送餐位址\n"+order.getReceive_address());
        }


        Button btnupdate = (Button) view.findViewById(R.id.btn2);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean bool = getresult(order.getOrder_id());
                if(bool==true){
                    DialogEvent();
                }
            }
        });
    }

    private Boolean getresult(String orderid) {
        Boolean bool = false;
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "Store_OrderServlet";
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {

                bool = new OrderUpdateTask().execute(url,orderid,order.getStore_id()).get();

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
        return bool;
    }
    private void DialogEvent(){
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
        dialog.setTitle("訂單");
        dialog.setMessage("訂單狀態UPDATE成功!");
        dialog.setPositiveButton("確定",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialoginterface, int i){
                        dismiss();
                        OrderlistDialogFragment.this.dismiss();
                    }
                });
        dialog.show();
    }



    private Bitmap Bytes2Bimap(byte[] b){
        if(b.length!=0){
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        else {
            return null;
        }
    }


    private class OrderlistAdapter extends BaseAdapter {
        List<Orderlist> orderlists;
        Context context;
        public OrderlistAdapter(List<Orderlist> orderlist) {
            this.orderlists = orderlist;
            this.context = getActivity();
        }

        @Override
        public int getCount() {
            return orderlist.size();
        }

        @Override
        public Object getItem(int i) {
            return orderlist.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                view = layoutInflater.inflate(R.layout.view_item_orderlist,viewGroup,false);
            }
            Orderlist orderlist = orderlists.get(i);
            TextView tvname = (TextView) view.findViewById(R.id.tvname);
            TextView tvamount = (TextView) view.findViewById(R.id.tvamount);
            TextView tvprice = (TextView) view.findViewById(R.id.tvprice);
            if(i==1){
                tvname.setText("品名\n"+orderlist.getPro_name().toString());
                tvamount.setText("數量\n"+orderlist.getOrder_amount().toString());
                int j = orderlist.getOrder_amount()*orderlist.getPrice();
                tvprice.setText("價錢\n"+String.valueOf(j));
            }
            tvname.setText(orderlist.getPro_name().toString());
            tvamount.setText(orderlist.getOrder_amount().toString());
            int j = orderlist.getOrder_amount()*orderlist.getPrice();
            tvprice.setText(String.valueOf(j));

            return view;
        }
    }

}