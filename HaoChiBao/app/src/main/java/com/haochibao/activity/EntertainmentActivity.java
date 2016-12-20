package com.haochibao.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.fragment.EntertainmentContentFragment;
import com.haochibao.fragment.EntertainmentGoodReputationFragment;
import com.haochibao.fragment.EntertainmentNearbyFragment;
import com.haochibao.fragment.EntertainmentRankFragment;
import com.haochibao.utill.adapter.EntertainmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class EntertainmentActivity extends FragmentActivity {
    ListView enterList;
    ImageView imgLeft;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        imgLeft = (ImageView) findViewById(R.id.img_left);
        enterList = (ListView) findViewById(R.id.entertainment_list);

        imgLeft.setOnClickListener(getOnClickListener());
        enterList.setAdapter(new EntertainmentAdapter(EntertainmentActivity.this));
    }
    public View.OnClickListener getOnClickListener(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.img_left:
                        finish();
                        break;
                }
            }
        };
        return onClickListener;
    }
}
