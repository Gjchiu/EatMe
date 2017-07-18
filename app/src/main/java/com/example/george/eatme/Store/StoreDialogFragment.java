package com.example.george.eatme.Store;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.george.eatme.Product.ProductActivity;
import com.example.george.eatme.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Java on 2017/7/13.
 */

public class StoreDialogFragment extends DialogFragment {
    private Store store;
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
        View view = inflater.inflate(R.layout.alertdialog_store, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Store store = (Store) getArguments().getSerializable("store");

        ImageView ivstore = (ImageView) view.findViewById(R.id.ivalretstore);
        TextView tvstorename = (TextView)view.findViewById(R.id.tvstorename);
        TextView tvstorephone = (TextView)view.findViewById(R.id.tvstorephone);
        TextView tvstoreout = (TextView)view.findViewById(R.id.tvstoreout);
        TextView tvstorestar = (TextView)view.findViewById(R.id.tvstorestar);
        TextView tvstoreadd = (TextView)view.findViewById(R.id.tvstoreadd);
        if(store.getStore_image()!=null){
            ivstore.setImageBitmap(Bytes2Bimap(store.getStore_image()));
        }
        tvstorename.setText(store.getStore_name());
        tvstorephone.setText("電話  "+store.getStore_phone());
        tvstoreout.setText(store.getStore_out());
        tvstorestar.setText("評價  " + store.getStore_star());
        tvstoreadd.setText("地址  " + store.getStore_addr());
        Button btntakeproduct = (Button) view.findViewById(R.id.btntakeproduct);
        btntakeproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle =new Bundle();
                bundle.putSerializable("store",store);
                intent.putExtras(bundle);
                intent.setClass(getActivity(), ProductActivity.class);
                startActivity(intent);
            }
        });
        Button btncancel = (Button) view.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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