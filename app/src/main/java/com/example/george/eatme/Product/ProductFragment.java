package com.example.george.eatme.Product;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.george.eatme.Common;
import com.example.george.eatme.R;
import com.example.george.eatme.Store.Store;
import com.example.george.eatme.Store.StoreDialogFragment;
import com.example.george.eatme.Store.StoreGetTask;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by George on 2017/7/17.
 */

public class ProductFragment extends Fragment {
    private Store store;
    private List<Product> productlist;
    private RecyclerView recyclerView;
    public ProductFragment(Store store) {
        this.store = store;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.storerecylerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        getproductlist();
        recyclerView.setAdapter(new ProductAdapter(getActivity(),productlist));
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
        public void onBindViewHolder(ProductAdapter.MyViewHolder holder, int position) {
            final Product product = productlist.get(position);

            if(product.getPro_image()!=null){
                holder.imageview.setImageBitmap(Bytes2Bimap(product.getPro_image()));
            }
            holder.textview.setText(product.getPc_name()
                                    +" $ " + product.getPro_price().toString());
            holder.amount.setText(amo);
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    amo = amo +1;
                }
            });
            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
