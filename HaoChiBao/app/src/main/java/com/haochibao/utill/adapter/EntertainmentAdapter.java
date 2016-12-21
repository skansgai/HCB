package com.haochibao.utill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.model.EntertainmentModel;
import com.haochibao.utill.view_holder.SortViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class EntertainmentAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<EntertainmentModel> list;
    public EntertainmentAdapter(Context context,List<EntertainmentModel> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
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
        SortViewHolder viewHolder = new SortViewHolder();
        if (convertView == null){
            convertView = inflater.inflate(R.layout.entertainment_list_item,null);
            viewHolder.setName((TextView) convertView.findViewById(R.id.name));
            viewHolder.setDistance((TextView) convertView.findViewById(R.id.distance));
            viewHolder.setType((TextView) convertView.findViewById(R.id.type));
            viewHolder.setLocation((TextView) convertView.findViewById(R.id.location));
            viewHolder.setLook((TextView) convertView.findViewById(R.id.have_a_look_at));
            viewHolder.setPrice((TextView) convertView.findViewById(R.id.price));
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (SortViewHolder) convertView.getTag();
        }
        viewHolder.getName().setText(list.get(position).getName());
        viewHolder.getPrice().setText(list.get(position).getPrice());
        viewHolder.getDistance().setText(list.get(position).getDistance());
        viewHolder.getLocation().setText(list.get(position).getLocation());
        viewHolder.getType().setText(list.get(position).getType());
        return convertView;
    }
}
