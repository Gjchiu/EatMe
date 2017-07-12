package com.example.george.eatme.Order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.george.eatme.R;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by George on 2017/6/22.
 */

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
        return new ViewHolder(itemView);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtakeorderTitle, tvOrderDetail;
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            tvtakeorderTitle = (TextView) itemView.findViewById(R.id.tvtakeorderTitle);
        }
    }

    @Override
    public void onBindViewHolder(final TakeOrderRecyclerViewAdapter.ViewHolder holder, int position) {
        Store_Order order = orderList.get(position);
        String title = order.getOrder_id();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        holder.tvtakeorderTitle.setText("訂單編號：" + title +
                                    "\n日期：" + sdf.format(order.getOrder_time()) +
                                    "\n訂餐店家："+ order.getStore_name() +
                                    "\n總金額："+ order.getTotalprice()+
                                    "              狀態："+order.getOrder_state());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"123",Toast.LENGTH_SHORT).show();
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
