package com.example.george.eatme.Product;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.george.eatme.Common;
import com.example.george.eatme.Order.Orderlist;
import com.example.george.eatme.R;
import com.example.george.eatme.Store.Store;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by George on 2017/7/17.
 */

public class ProductFragment extends Fragment {
    private Store store;
    private List<Product> productlist;
    private RecyclerView recyclerView;
    public List<Orderlist> orderlists;
    public ProductFragment(Store store) {
        this.store = store;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        orderlists = new ArrayList<Orderlist>();
        recyclerView = (RecyclerView)view.findViewById(R.id.rvproduct);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        getproductlist();
        recyclerView.setAdapter(new ProductAdapter(getActivity(),productlist));
        Button button =(Button)view.findViewById(R.id.btncheck);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                switchFragment();
                for(Product product:productlist){
                    if(product.amount>0){
                        Orderlist orderlist = new Orderlist();
                        orderlist.setPro_id(product.getPro_id());
                        orderlist.setOrder_amount(product.amount);
                        orderlist.setPrice(product.getPro_price().intValue());
                        orderlist.setPro_name(product.getPro_name());
                        orderlists.add(orderlist);
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderlists", (Serializable) orderlists);
                bundle.putSerializable("store",store);
                OrderSettingFragment orderSettingFragment = new OrderSettingFragment();
                orderSettingFragment.setArguments(bundle);
                switchFragment(orderSettingFragment);
            }
        });
        return view;
    }

    private void getproductlist() {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "ProductServlet";

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {

                productlist = new ProductGetTask().execute(url,store.getStore_id()).get();

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }
    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flproduct, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
        private Context conotext;
        private List<Product> productlist;
        public ProductAdapter(Context context, List<Product> productlist) {
            this.conotext = context;
            this.productlist = productlist;
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
            private ImageView imageview;
            private TextView textview,amount;
            private ImageButton add,minus;
            public MyViewHolder(View view){
                super(view);
                imageview = (ImageView) view.findViewById(R.id.ivproduct);
                textview = (TextView)view.findViewById(R.id.tvproduct);
                amount = (TextView)view.findViewById(R.id.tvamount);
                add = (ImageButton) view.findViewById(R.id.btnadd);
                minus = (ImageButton) view.findViewById(R.id.btnminus);
            }
        }

        @Override
        public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutinflater = LayoutInflater.from(conotext);
            View view =layoutinflater.inflate(R.layout.view_item_product,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ProductAdapter.MyViewHolder holder, int position) {
            final Product product = productlist.get(position);
            product.amount = 0;
            if(product.getPro_image()!=null){
                holder.imageview.setImageBitmap(Bytes2Bimap(product.getPro_image()));
            }
            holder.textview.setText(product.getPro_name()
                                    +"  $ " + product.getPro_price().toString());
            holder.amount.setText(product.amount.toString());
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    product.amount++;
                    holder.amount.setText(product.amount.toString());
                }
            });
            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    product.amount--;
                    if(product.amount<0)
                        product.amount=0;
                    holder.amount.setText(product.amount.toString());
                }
            });


        }

        @Override
        public int getItemCount() {
            return productlist.size();
        }

        private Bitmap Bytes2Bimap(byte[] b){
            if(b.length!=0){
                return BitmapFactory.decodeByteArray(b, 0, b.length);
            }
            else {
                return null;
            }
        }
    }

}
