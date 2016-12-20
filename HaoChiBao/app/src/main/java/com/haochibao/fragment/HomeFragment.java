package com.haochibao.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.activity.AttractionActivity;
import com.haochibao.activity.EntertainmentActivity;
import com.haochibao.activity.HotelActivity;
import com.haochibao.activity.InterctionActivity;
import com.haochibao.activity.ParkingActivity;
import com.haochibao.activity.RecommendActivity;
import com.haochibao.activity.SeekHelpActivity;
import com.haochibao.activity.ShopingActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/12/13.
 * 我的页面
 */

public class HomeFragment extends Fragment {
    TextView home_recommend,home_interaction,home_seek_help,home_recreation,
            home_grogshop,home_scenic_spots,home_shopping,home_park;
    private View view;
    private TextView homeRecommend;
    private Context context;
    private Activity mactivity;
    Intent intent;
    ListView listView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mactivity=getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_homepage,null);
        context=getActivity();
        init();
        home_recommend = (TextView) view.findViewById(R.id.home_recommend);
        home_interaction = (TextView) view.findViewById(R.id.home_interaction);
        home_seek_help = (TextView) view.findViewById(R.id.home_seek_help);
        home_recreation = (TextView) view.findViewById(R.id.home_recreation);
        home_grogshop = (TextView) view.findViewById(R.id.home_grogshop);
        home_scenic_spots = (TextView) view.findViewById(R.id.home_scenic_spots);
        home_shopping = (TextView) view.findViewById(R.id.home_shopping);
        home_park = (TextView) view.findViewById(R.id.home_park);
        listView = (ListView) view.findViewById(R.id.homepage_lv);
        HomePageAdapter homePageAdapter = new HomePageAdapter(this.getActivity(),list);
        listView.setAdapter(homePageAdapter);
        home_recommend.setOnClickListener(onClickListener);
        home_interaction.setOnClickListener(onClickListener);
        home_seek_help.setOnClickListener(onClickListener);
        home_recreation.setOnClickListener(onClickListener);
        home_grogshop.setOnClickListener(onClickListener);
        home_scenic_spots.setOnClickListener(onClickListener);
        home_shopping.setOnClickListener(onClickListener);
        home_park.setOnClickListener(onClickListener);
        return view;
    }
    public void init(){
        homeRecommend= (TextView) view.findViewById(R.id.home_recommend);
        homeRecommend.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.home_recommend:
                    intent = new Intent(mactivity,RecommendActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_interaction:
                    intent = new Intent(mactivity,InterctionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_seek_help:
                    intent = new Intent(mactivity,SeekHelpActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_recreation:
                    intent = new Intent(mactivity,EntertainmentActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_grogshop:
                    intent = new Intent(mactivity,HotelActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_scenic_spots:
                    intent = new Intent(mactivity,AttractionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_shopping:
                    intent = new Intent(mactivity,ShopingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.home_park:
                    intent = new Intent(mactivity,ParkingActivity.class);
                    startActivity(intent);
                    break;
            }

        }
    };
    List<String> list = new ArrayList<String>();
    class HomePageAdapter extends BaseAdapter{
        Context context;
        List<String>list;
        LayoutInflater layoutInflater;
    public HomePageAdapter(Context context,List<String>list){
        this.context = context;
        this.list=list;
    }
        @Override
        public int getCount() {
            return 10;
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
            layoutInflater = LayoutInflater.from(context);
            if(convertView==null){
               convertView = layoutInflater.inflate(R.layout.item_homepage_lv,null);
            }
            return convertView;
        }
    }

}
