package com.example.george.eatme.Product;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.support.v7.widget.AppCompatButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.george.eatme.Common;
import com.example.george.eatme.Member.Member;
import com.example.george.eatme.Order.Orderlist;
import com.example.george.eatme.Order.Store_Order;
import com.example.george.eatme.R;
import com.example.george.eatme.Store.Store;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Java on 2017/7/19.
 */

public class OrderSettingFragment extends Fragment{
    private int  mYear,mMonth,mDay,mHour,mMinute;
    private TextView tvshowtine;
    private Store store;
    private String[] getway;
    private String time;
    private Member member;
    public List<Orderlist> orderlists;
    private int total;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oredersetting, container, false);
        orderlists = (List<Orderlist>) getArguments().getSerializable("orderlists");
        for(Orderlist orderlist:orderlists){
            total = orderlist.getPrice() * orderlist.getOrder_amount();
        }

        store = (Store)getArguments().getSerializable("store");
        final Store_Order order =new Store_Order();
        loadPreferences();
        order.setMem_id(member.getMem_id());
        order.setStore_id(store.getStore_id());

        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.address);
        final EditText editText = (EditText)view.findViewById(R.id.etadd);

        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        if(store.getStore_out().equals("有外送")){
            getway = new String[]{"內用", "自取", "外送"};
        }else {
            getway = new String[]{"內用", "自取"};
        }
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                getway);
        spinner.setAdapter(lunchList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                order.setOrder_way(getway[position]);
                if(getway[position].equals("外送")){
                    linearLayout.setVisibility(VISIBLE);
                }else{
                    linearLayout.setVisibility(GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tvshowtine = (TextView)view.findViewById(R.id.tvshowtine);
        ImageButton btnpicktime =(ImageButton) view.findViewById(R.id.btnpicktime);
        btnpicktime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mYear==0) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);
                }

                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        mHour = hour;
                        mMinute = minute;
                        updateDisplay();
                    }
                }, mHour,mMinute,false).show();

                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        mYear=year;
                        mMonth=month;
                        mDay=day;
                        updateDisplay();
                    }
                }, mYear,mMonth, mDay).show();
            }

        });

        Button btnset = (Button)view.findViewById(R.id.btnset);
        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.setOrder_time(tvshowtine.getText().toString());
                order.setReceive_address(editText.getText().toString());
                order.setOrder_taketime(tvshowtine.getText().toString());
                order.setTotalprice(total);
                Boolean bool = false;
                getresult(bool,order);
                if(bool == true){
                    setalertdialog();
                }else{
                    Toast.makeText(getActivity(),"訂單成立失敗",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    private void updateDisplay() {
        tvshowtine.setText(new StringBuilder().append(mYear).append("-")
                .append(pad(mMonth + 1)).append("-").append(pad(mDay))
                .append(" ").append(pad(mHour)).append(":")
                .append(pad(mMinute)));
    }
    private String pad(int number) {
        if (number >= 10)
            return String.valueOf(number);
        else
            return "0" + String.valueOf(number);
    }
    private void loadPreferences() {
        SharedPreferences preferences
                = getActivity().getSharedPreferences("Login",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("member", "");
        member = gson.fromJson(json, Member.class);
    }
    private void setalertdialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("訂單成立");
        dialog.setMessage("訂單成立成功!");
        dialog.setPositiveButton("確定",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialoginterface, int i){
                        getActivity().finish();
                    }
                });
        dialog.show();
    }

    private void getresult(Boolean bool,Store_Order order) {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "Store_OrderServlet1";

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {

                bool = new OrderInsertTask().execute(url,order,orderlists).get();

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }
}
