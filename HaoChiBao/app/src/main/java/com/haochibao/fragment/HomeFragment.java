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
import com.haochibao.R;
import com.haochibao.activity.MineCollectionActivity;
import com.haochibao.activity.MineInformationActivity;
import com.haochibao.activity.Settingactivity;
/**
 * Created by Administrator on 2016/12/13.
 * 我的页面
 */

public class HomeFragment extends Fragment {
    private RelativeLayout minePage,setting,mineCollection;
    private Intent intent;
    private Context context;
    private View view;
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
    //初始化
    public void init(){
        minePage= (RelativeLayout) view.findViewById(R.id.mine_page_btn);
        setting= (RelativeLayout)view. findViewById(R.id.setting);
        mineCollection= (RelativeLayout)view. findViewById(R.id.mine_collection);
        minePage.setOnClickListener(onClickListener);
        setting.setOnClickListener(onClickListener);
        mineCollection.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mine_page_btn:
                    intent=new Intent(context,MineInformationActivity.class);
                    startActivity(intent);
                    break;
                case R.id.setting:
                    intent=new Intent(context,Settingactivity.class);
                    startActivity(intent);
                    break;
                case R.id.mine_collection:
                    intent=new Intent(context,MineCollectionActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
