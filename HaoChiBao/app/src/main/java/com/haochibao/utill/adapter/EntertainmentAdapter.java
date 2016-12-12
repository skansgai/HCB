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
public class EntertainmentAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    public EntertainmentAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 8;
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
        if (convertView == null){
            convertView = inflater.inflate(R.layout.entertainment_list_item,null);
        }
        return convertView;
    }
}
