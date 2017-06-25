package com.example.george.eatme.Order;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.george.eatme.OrderActivity;
import com.example.george.eatme.R;

import java.text.SimpleDateFormat;
import java.util.List;



/**
 * Created by George on 2017/6/22.
 */

public class OrderRecyclerViewAdapter extends RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    private LayoutInflater layoutInflater;
    private List<Store_Order> orderList;
    private boolean[] orderExpanded;
    private OnItemClickListener mOnItemClickListener = null;
    private  String[]  datas;

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public OrderRecyclerViewAdapter(Context context, List<Store_Order> orderList) {
        layoutInflater = LayoutInflater.from(context);
        this.orderList = orderList;
//        orderExpanded = new boolean[orderList.size()];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.order_recyleview_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        //将创建的View注册点击事件
        itemView.setOnClickListener(this);
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
                                    "\n日期：" + sdf.format(order.getOrder_time())  +
                                    "\n訂餐店家："+ order.getStore_name() +
                                    "\n總金額："+ order.getTotalprice());
        holder.mTextView.setText(datas[position]);
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
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
