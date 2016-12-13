package com.haochibao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haochibao.R;

/**
 * Created by Administrator on 2016/12/13.
 */

public class MineFragment extends Fragment {
    private View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view=inflater.inflate(R.layout.activity_mine_home_page,null);
        return view;
    }
}
