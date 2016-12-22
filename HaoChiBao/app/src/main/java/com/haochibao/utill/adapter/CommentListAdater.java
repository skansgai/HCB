package com.haochibao.utill.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.haochibao.R;
import com.haochibao.utill.model.CommentModel;
import com.haochibao.utill.view_holder.CommentViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/12/10.
 */
public class CommentListAdater extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<CommentModel> list;
    CommentViewHolder viewHolder;
    public CommentListAdater(Context context,List<CommentModel> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        viewHolder = new CommentViewHolder();
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
        if (convertView == null){
            convertView = inflater.inflate(R.layout.comment_list_item,null);
            viewHolder.comment = (ImageView) convertView.findViewById(R.id.comment);
            viewHolder.praise = (ImageView) convertView.findViewById(R.id.praise);
            viewHolder.userPortrait = (ImageView) convertView.findViewById(R.id.user_portrait);
            viewHolder.userLevel = (TextView) convertView.findViewById(R.id.user_level);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.commentImg = (ImageView) convertView.findViewById(R.id.img_one);
            viewHolder.rating = (RatingBar) convertView.findViewById(R.id.rating);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.scan = (TextView) convertView.findViewById(R.id.scan);
            viewHolder.commentSquare = (LinearLayout) convertView.findViewById(R.id.comment_square);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (CommentViewHolder) convertView.getTag();
        }
        viewHolder.userName.setText(list.get(position).userName);
        viewHolder.userLevel.setText(list.get(position).userLevel);
        viewHolder.content.setText(list.get(position).content);
        viewHolder.time.setText(list.get(position).time);
        viewHolder.scan.setText(list.get(position).scan);
        viewHolder.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"点赞",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.commentSquare.setVisibility(View.VISIBLE);
                Toast.makeText(context,"评论",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
