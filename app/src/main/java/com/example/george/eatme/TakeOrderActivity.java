package com.example.george.eatme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.george.eatme.Order.*;
import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Order.Store_Order;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.text.SimpleDateFormat;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Created by George on 2017/7/12.
 */

public class TakeOrderActivity extends AppCompatActivity {
    private final static String TAG = "TakeOrderActivity";
    private RecyclerView rvtakeorder;
    private Member member;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeorder);
        loadPreferences();
        setToolbar();
        rvtakeorder = (RecyclerView) findViewById(R.id.rvtakeorder);
        rvtakeorder.setLayoutManager(new LinearLayoutManager(this));
        getorders();
    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.takeorder_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getorders(){
        if (Common.networkConnected(this)) {
            String url = Common.URL + "Store_OrderServlet";
            List<Store_Order> orderList = null;

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                orderList =new OrderGetByTakeTask().execute(url,member.getMem_id()).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (orderList == null || orderList.isEmpty()) {
                Common.showToast(this, "無訂單");
            } else {
                TakeOrderRecyclerViewAdapter takeOrderRecyclerViewAdapter =  new TakeOrderRecyclerViewAdapter(this, orderList);
                rvtakeorder.setAdapter(takeOrderRecyclerViewAdapter);
            }
            progressDialog.cancel();

        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
    }
    private void loadPreferences() {
        SharedPreferences preferences
                = getSharedPreferences("Login",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("member", "");
        member = gson.fromJson(json, Member.class);
        Log.d("member111",member.getMem_id());
    }

    public class TakeOrderRecyclerViewAdapter extends RecyclerView.Adapter<TakeOrderRecyclerViewAdapter.ViewHolder> {
        private LayoutInflater layoutInflater;
        private List<Store_Order> orderList;
        private boolean[] orderExpanded;
        Context context;


        public TakeOrderRecyclerViewAdapter(Context context, List<Store_Order> orderList) {
            layoutInflater = LayoutInflater.from(context);
            this.orderList = orderList;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.view_item_recyleview_takeorder, parent, false);
            ViewHolder vh = new ViewHolder(itemView);
            return vh;
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvtakeorderTitle;
            ImageButton btntakeorder;
            Button button;
            public ViewHolder(View itemView) {
                super(itemView);
                tvtakeorderTitle = (TextView) itemView.findViewById(R.id.tvtakeorderTitle);
                btntakeorder = (ImageButton) itemView.findViewById(R.id.btntakeorder);
            }
        }

        @Override
        public void onBindViewHolder(final TakeOrderRecyclerViewAdapter.ViewHolder holder, int position) {
            final Store_Order order = orderList.get(position);
            String title = order.getOrder_id();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            holder.tvtakeorderTitle.setText("訂單編號：" + title +
                    "\n日期：" + order.getOrder_time() +
                    "\n訂餐店家："+ order.getStore_name() +
                    "\n總金額："+ order.getTotalprice()+
                    "\n狀態："+order.getOrder_state());
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                }
            });
            holder.btntakeorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle =new Bundle();
                    bundle.putString("order_id",order.getOrder_id());
                    AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                    alertDialogFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    alertDialogFragment.show(fragmentManager,"alert");

                }
            });

//        holder.tvOrderDetail.setText(order.getTotalprice().toString());
//        holder.tvOrderDetail.setVisibility(
//                orderExpanded[position] ? View.VISIBLE : View.GONE);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                expand(holder.getAdapterPosition());
//            }
//        });
        }

        @Override
        public int getItemCount() {
            return orderList.size();
        }


//    private void expand(int position) {
//        // 被點擊的資料列才會彈出內容，其他資料列的內容會自動縮起來
//        // for (int i=0; i<orderExpanded.length; i++) {
//        // orderExpanded[i] = false;
//        // }
//        // orderExpanded[position] = true;
//
//        orderExpanded[position] = !orderExpanded[position];
//        notifyDataSetChanged();
//    }

    }
}
