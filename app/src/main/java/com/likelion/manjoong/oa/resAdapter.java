package com.likelion.manjoong.oa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mac on 2017. 4. 13..
 */

public class resAdapter extends BaseAdapter {
    private ArrayList<restaurant> datalist ;
    private ArrayList<restaurant> arrlist = new ArrayList<>() ;
    private Context context;


    public resAdapter(ArrayList<restaurant> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int i) {
        return datalist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        }
        TextView txt1 = (TextView) view.findViewById(R.id.textName);
        TextView txt2 = (TextView) view.findViewById(R.id.textTel);


        final int position = i;





        txt1.setText(datalist.get(i).getName());
        txt2.setText(datalist.get(i).getTell());


        return view;
    }



}
