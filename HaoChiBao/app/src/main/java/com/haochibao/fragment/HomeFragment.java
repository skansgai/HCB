package com.haochibao.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.activity.MineCollectionActivity;
import com.haochibao.activity.MineInformationActivity;
import com.haochibao.activity.Settingactivity;
import com.haochibao.activity.TuijianActivity;

/**
 * Created by Administrator on 2016/12/13.
 * 我的页面
 */

public class HomeFragment extends Fragment {
    private View view;
    private TextView homeRecommend;
    private Intent intent;
    private Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_homepage,null);
        context=getActivity();
        init();
        return view;
    }
    public void init(){
        homeRecommend= (TextView) view.findViewById(R.id.home_recommend);
        homeRecommend.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_recommend:
                intent=new Intent(context, TuijianActivity.class);
                startActivity(intent);
                break;
            }
        }
    };
}
