package com.haochibao.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.haochibao.R;
import com.haochibao.utill.adapter.EntertainmentAdapter;

/**
 * Created by David on 2016/12/12.
 */

public class ShopingContentFragment extends Fragment {
    Activity activity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView listView = (ListView) inflater.inflate(R.layout.fragment_entertainment_content,null);
        listView.setAdapter(new EntertainmentAdapter(activity));
        return listView;
    }
}
