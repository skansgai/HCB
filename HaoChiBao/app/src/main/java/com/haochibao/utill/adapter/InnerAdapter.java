package com.haochibao.utill.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.haochibao.R;
import com.haochibao.utill.model.CommentDetail;
import com.haochibao.utill.textstyle.ClickListener;
import com.haochibao.utill.textstyle.StyleBuilder;
import com.haochibao.utill.view.InnerListView;
import com.haochibao.utill.view_holder.InnerViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/1/3.
 */
public class InnerAdapter extends BaseAdapter {
    private Context context;
    LayoutInflater inflater;
    List<CommentDetail> list;
    public InnerAdapter(Context context,List<CommentDetail> list){
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
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InnerViewHolder viewHolder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.inner_list_item,null);
            viewHolder = new InnerViewHolder();
            viewHolder.setTextView((TextView) convertView.findViewById(R.id.inner_text));
            convertView.setTag(viewHolder);
        }
        viewHolder = (InnerViewHolder) convertView.getTag();
        StyleBuilder styleBuilder = new StyleBuilder();
        styleBuilder.addTextStyle(list.get(position).getHost()).textColor(Color.BLUE)
                .click(new ClickListener() {
                    @Override
                    public void click(String text) {
                        Toast.makeText(context,"text = "+text,Toast.LENGTH_SHORT).show();
                    }
                })
                .commit().text("回复")
                .addTextStyle(list.get(position).getName()).textColor(Color.BLUE).commit()
                .addTextStyle(list.get(position).getContent()).commit()
                .newLine();
        styleBuilder.show(viewHolder.getTextView());
        return convertView;
    }
}
