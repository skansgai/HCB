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
    private View view;
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
        return view;
    }

}
