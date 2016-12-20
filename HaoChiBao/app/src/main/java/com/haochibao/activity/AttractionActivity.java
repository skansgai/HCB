package com.haochibao.activity;

import android.os.Bundle;
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
import com.haochibao.fragment.AttractionContentFragment;
import com.haochibao.fragment.AttractionGoodReputationFragment;
import com.haochibao.fragment.AttractionNearbyFragment;
import com.haochibao.fragment.AttractionRankFragment;
import com.haochibao.utill.adapter.EntertainmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class AttractionActivity extends FragmentActivity {
    ImageView img_left;
    ListView attractionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);
        img_left = (ImageView) findViewById(R.id.img_left);
        attractionList = (ListView) findViewById(R.id.attraction_list);

        img_left.setOnClickListener(getOnClickListener());
        attractionList.setAdapter(new EntertainmentAdapter(this));
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
