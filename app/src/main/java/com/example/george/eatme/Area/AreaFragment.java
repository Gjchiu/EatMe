package com.example.george.eatme.Area;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.george.eatme.R;
import com.example.george.eatme.Store.StoreActivity;

import java.util.List;

/**
 * Created by Java on 2017/7/13.
 */

public class AreaFragment extends Fragment {
    private final static String TAG = "AreaFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        setView(view);
        return view;
    }
    public void setView(View view){
        ListView listView =(ListView)view.findViewById(R.id.lvarea);
        listView.setAdapter(new AreaAdapter(getActivity()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String area = (String)adapterView.getItemAtPosition(i);
                Log.d("Area",area);
                    Bundle bunble = new Bundle();
                    bunble.putString("area",area);
                    Intent intent = new Intent();
                    intent.putExtras(bunble);
                    intent.setClass(getActivity(), StoreActivity.class);
                    startActivity(intent);
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.memlayout, fragment);
        fragmentTransaction.commit();
    }
    public class AreaAdapter extends BaseAdapter {
        Context context;
        List<String> arealist;
        String area;

        public AreaAdapter(Context context) {
            this.context = context;
            this.arealist = new Area().getlist();
        }

        @Override
        public int getCount() {
            return arealist.size();
        }

        @Override
        public Object getItem(int position) {
            return arealist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater layoutInflater =LayoutInflater.from(context);
                view = layoutInflater.inflate(R.layout.view_item_area,viewGroup,false);
            }
            area = arealist.get(position);
            TextView textView = (TextView)view.findViewById(R.id.areatvid);
            textView.setText(area);
            return view;
        }

    }

}
