package com.example.george.eatme.Order;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.george.eatme.Common;
import com.example.george.eatme.Member.Member;
import com.example.george.eatme.R;
import com.example.george.eatme.Store.Store;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.george.eatme.R.drawable.member;


/**
 * Created by George on 2017/6/22.
 */

public class OrderFragment extends android.support.v4.app.Fragment{
    private final static String TAG = "OrderFragment";
    private RecyclerView rvOrder;
    private Member member;
    private Store store;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        loadPreferences();
        rvOrder = (RecyclerView) view.findViewById(R.id.rvOrders);
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
            if (Common.networkConnected(getActivity())) {
                String url = Common.URL + "Store_OrderServlet";
                List<Store_Order> orderList = null;
                String state = getArguments().getString("state");
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                    if(member == null){
                        orderList = new StoreOrderGetByStateTask().execute(url,store.getStore_id(),state).get();
                    }else{
                        orderList = new OrderGetByStateTask().execute(url,member.getMem_id(),state).get();
                    }


            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (orderList == null || orderList.isEmpty()) {
                Common.showToast(getActivity(), "無訂單");
            } else {
                OrderRecyclerViewAdapter orderRecyclerViewAdapter =  new OrderRecyclerViewAdapter(getActivity(), orderList);
                rvOrder.setAdapter(orderRecyclerViewAdapter);
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }
    private void loadPreferences() {
        SharedPreferences preferences
                = getActivity().getSharedPreferences("Login",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("member", "");
        member = gson.fromJson(json, Member.class);
        if(member == null){
            json = preferences.getString("store", "");
            store = gson.fromJson(json, Store.class);
        }
    }

    public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder> {
        private LayoutInflater layoutInflater;
        private List<Store_Order> orderList;
        private boolean[] orderExpanded;
        Context context;


        public OrderRecyclerViewAdapter(Context context, List<Store_Order> orderList) {
            layoutInflater = LayoutInflater.from(context);
            this.orderList = orderList;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.view_item_recyleview_order, parent, false);
            ViewHolder vh = new ViewHolder(itemView);
            return new ViewHolder(itemView);
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvOrderTitle, tvOrderDetail;

            public ViewHolder(View itemView) {
                super(itemView);
                tvOrderTitle = (TextView) itemView.findViewById(R.id.tvorderTitle);
            }
        }

        @Override
        public void onBindViewHolder(final OrderRecyclerViewAdapter.ViewHolder holder, int position) {
            Store_Order order = orderList.get(position);
            String title = order.getOrder_id();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

            holder.tvOrderTitle.setText("訂單編號：" + title +
                    "\n日期：" + order.getOrder_time() +
                    "\n訂餐店家："+ order.getStore_name() +
                    "\n總金額："+ order.getTotalprice()+
                    "\n狀態："+order.getOrder_state());
//            holder.itemView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context,"123",Toast.LENGTH_SHORT).show();
//                }
//            });

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
