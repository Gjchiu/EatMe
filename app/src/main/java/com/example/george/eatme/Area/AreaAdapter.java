package com.example.george.eatme.Area;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.george.eatme.R;

import java.util.List;

/**
 * Created by George on 2017/6/20.
 */

public class AreaAdapter extends BaseAdapter {
    Context context;
    List<String> arealist;

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
            view = layoutInflater.inflate(R.layout.area_view,viewGroup,false);
        }
        String area = arealist.get(position);
        TextView textView = (TextView)view.findViewById(R.id.areatvid);
        textView.setText(area);
        return view;
    }
}
