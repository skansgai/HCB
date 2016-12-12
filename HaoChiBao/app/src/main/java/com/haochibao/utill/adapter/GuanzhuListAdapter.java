package com.haochibao.utill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.haochibao.R;

/**
 * Created by Administrator on 2016/12/12.
 */
public class GuanzhuListAdapter extends BaseAdapter {
    Context context;
    public GuanzhuListAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        convertView=layoutInflater.inflate(R.layout.activity_guanzhu_listview ,null);
        return convertView;

    }
}
