package com.haochibao.utill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.model.AppRecommendModel;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class AppRecommendAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<AppRecommendModel> list;
    public AppRecommendAdapter(Context context, List<AppRecommendModel> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
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
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.activity_tuijian_lv_item_app,null);
            viewHolder=new ViewHolder();
            viewHolder.mineRecommendLeftItem=
                    (LinearLayout) convertView.findViewById(R.id.mine_recommend_left_item);
            viewHolder.mineRecommendRightItem=
                    (LinearLayout) convertView.findViewById(R.id.mine_recommend_right_item);
            viewHolder.foodImg= (ImageView) convertView.findViewById(R.id.food_img);
            viewHolder.foodName= (TextView) convertView.findViewById(R.id.food_name);
            viewHolder.foodGrade= (RatingBar) convertView.findViewById(R.id.food_grade);
            viewHolder.zan= (CheckBox) convertView.findViewById(R.id.zan);
            viewHolder.zanNumber= (TextView) convertView.findViewById(R.id.zan_number);

            viewHolder.foodImg1= (ImageView) convertView.findViewById(R.id.food_img1);
            viewHolder.foodName1= (TextView) convertView.findViewById(R.id.food_name1);
            viewHolder.foodGrade1= (RatingBar) convertView.findViewById(R.id.food_grade1);
            viewHolder.zan1= (CheckBox) convertView.findViewById(R.id.zan1);
            viewHolder.zanNumber1= (TextView) convertView.findViewById(R.id.zan_number1);
            convertView.setTag(viewHolder);
        }
        viewHolder=(ViewHolder)convertView.getTag();
        AppRecommendModel model=list.get(position);
        viewHolder.foodImg.setImageResource(model.getFooImageId());
        viewHolder.foodImg1.setImageResource(model.getFooImageId1());
        viewHolder.foodName.setText(model.getFoodName());
        viewHolder.foodName1.setText(model.getFoodName1());
        viewHolder.foodGrade.setRating((float) model.getFoodGrde());
        viewHolder.foodGrade1.setRating((float) model.getFoodGrde1());
        viewHolder.zan.setChecked(model.isZan1());
        viewHolder.zan1.setChecked(model.isZan1());
        viewHolder.zanNumber.setText(model.getZanNumber()+"");
        viewHolder.zanNumber1.setText(model.getZanNumber1()+"");
        return convertView;
    }
    public class ViewHolder{
        LinearLayout mineRecommendLeftItem,mineRecommendRightItem;
        ImageView foodImg;
        TextView foodName;
        TextView zanNumber;
        RatingBar foodGrade;
        CheckBox zan;
        ImageView foodImg1;
        TextView foodName1;
        TextView zanNumber1;
        RatingBar foodGrade1;
        CheckBox zan1;
    }
}
