package com.example.george.eatme.Order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.george.eatme.R;

import java.util.List;



/**
 * Created by George on 2017/6/22.
 */

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder>{
    private LayoutInflater layoutInflater;
    private List<Store_Order> orderList;
    private boolean[] orderExpanded;

    public OrderRecyclerViewAdapter(Context context, List<Store_Order> orderList) {
        layoutInflater = LayoutInflater.from(context);
        this.orderList = orderList;
        orderExpanded = new boolean[orderList.size()];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.order_recyleview_item, parent, false);
        return new ViewHolder(itemView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderTitle, tvOrderDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderTitle = (TextView) itemView.findViewById(R.id.tvorderTitle);
            tvOrderDetail = (TextView) itemView.findViewById(R.id.tvorderDetail);
        }
    }

    @Override
    public void onBindViewHolder(final OrderRecyclerViewAdapter.ViewHolder holder, int position) {
        Store_Order order = orderList.get(position);
        String title = order.getOrder_id();
        holder.tvOrderTitle.setText(title);
        holder.tvOrderDetail.setText(order.getTotalprice().toString());
        holder.tvOrderDetail.setVisibility(
                orderExpanded[position] ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    private void expand(int position) {
        // 被點擊的資料列才會彈出內容，其他資料列的內容會自動縮起來
        // for (int i=0; i<newsExpanded.length; i++) {
        // newsExpanded[i] = false;
        // }
        // newsExpanded[position] = true;

        orderExpanded[position] = !orderExpanded[position];
        notifyDataSetChanged();
    }
}
