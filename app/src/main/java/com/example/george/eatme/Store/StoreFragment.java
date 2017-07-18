package com.example.george.eatme.Store;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.george.eatme.Common;
import com.example.george.eatme.Order.AlertDialogFragment;
import com.example.george.eatme.R;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by George on 2017/7/17.
 */

public class StoreFragment extends Fragment {
    private String storeclass,area;
    private List<Store> storelist;
    private RecyclerView recyclerView;
    public StoreFragment(String storeclass,String area) {
        this.storeclass = storeclass;
        this.area = area;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.storerecylerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        getarea();
        getstorelist();
        recyclerView.setAdapter(new StoreAdapter(getActivity(),storelist));
        return view;
    }

    private void getstorelist() {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "StoreServlet";

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {

                storelist = new StoreGetTask().execute(url,area,storeclass).get();

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }
    private void getarea(){
        Intent intent = getActivity().getIntent();
        area = intent.getStringExtra("area");

    }

    private class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
        private Context conotext;
        private List<Store> storelist;
        public StoreAdapter(Context context, List<Store> storelist) {
            this.conotext = context;
            this.storelist = storelist;
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
            ImageView imageview;
            TextView textview;

            public MyViewHolder(View view){
                super(view);
                imageview = (ImageView) view.findViewById(R.id.ivstore);
                textview = (TextView)view.findViewById(R.id.tvstore);
            }
        }

        @Override
        public StoreAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutinflater = LayoutInflater.from(conotext);
            View view =layoutinflater.inflate(R.layout.view_item_store,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(StoreAdapter.MyViewHolder holder, int position) {
            final Store store = storelist.get(position);
            if(store.getStore_image()!=null){
                holder.imageview.setImageBitmap(Bytes2Bimap(store.getStore_image()));
            }

            holder.textview.setText(store.getStore_name()+
                                    "\n地址  " + store.getStore_addr()+
                                    "\n評價  " + store.getStore_star());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle =new Bundle();
                    bundle.putSerializable("store",store);
                    StoreDialogFragment storeDialogFragment = new StoreDialogFragment();
                    storeDialogFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    storeDialogFragment.show(fragmentManager,"alert");
                }
            });
        }

        @Override
        public int getItemCount() {
            return storelist.size();
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
