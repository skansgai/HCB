package com.haochibao.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.fragment.AttractionContentFragment;
import com.haochibao.utill.fragment.AttractionGoodReputationFragment;
import com.haochibao.utill.fragment.AttractionNearbyFragment;
import com.haochibao.utill.fragment.AttractionRankFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class AttractionActivity extends FragmentActivity {
    LinearLayout content;
    LinearLayout goodReputation;
    LinearLayout nearby;
    LinearLayout rank;
    TextView textContent;
    TextView textGoodReputation;
    TextView textNearby;
    TextView textRank;
    ViewPager viewPager;
    List<Fragment> mFragment = new ArrayList<Fragment>();
    FragmentPagerAdapter mAdapter;
    ImageView img_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);
        viewPager = (ViewPager) findViewById(R.id.attraction_view_pager);
        img_left = (ImageView) findViewById(R.id.img_left);
        init();
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };
        viewPager.setAdapter(mAdapter);
        content.setOnClickListener(getStateOnClickListener());
        nearby.setOnClickListener(getStateOnClickListener());
        goodReputation.setOnClickListener(getStateOnClickListener());
        rank.setOnClickListener(getStateOnClickListener());
        setChecked(0);
        viewPager.setOnPageChangeListener(getOnPageChangeListener());
        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void init(){
        content = (LinearLayout) findViewById(R.id.attraction_content);
        rank = (LinearLayout) findViewById(R.id.attraction_rank);
        nearby = (LinearLayout) findViewById(R.id.attraction_nearby);
        goodReputation = (LinearLayout) findViewById(R.id.attraction_good_reputation);
        textContent = (TextView) findViewById(R.id.text_content);
        textGoodReputation = (TextView) findViewById(R.id.text_good_reputation);
        textRank = (TextView) findViewById(R.id.text_rank);
        textNearby = (TextView) findViewById(R.id.text_nearby);
        Fragment contentFragment = new AttractionContentFragment();
        Fragment nearbyFragment = new AttractionNearbyFragment();
        Fragment goodReputationFragment = new AttractionGoodReputationFragment();
        Fragment rankFragment = new AttractionRankFragment();
        mFragment.add(contentFragment);
        mFragment.add(nearbyFragment);
        mFragment.add(goodReputationFragment);
        mFragment.add(rankFragment);
    }
    //每次点击时设置textColor为黑色
    public void resetText(){
        textContent.setTextColor(getResources().getColor(R.color.textBlack));
        textGoodReputation.setTextColor(getResources().getColor(R.color.textBlack));
        textNearby.setTextColor(getResources().getColor(R.color.textBlack));
        textRank.setTextColor(getResources().getColor(R.color.textBlack));
    }
    public void setChecked(int i){
        switch (i){
            case 0:
                textNearby.setTextColor(getResources().getColor(R.color.mainRed));
                viewPager.setCurrentItem(0);
                break;
            case 1:
                textContent.setTextColor(getResources().getColor(R.color.mainRed));
                viewPager.setCurrentItem(1);
                break;
            case 2:
                textRank.setTextColor(getResources().getColor(R.color.mainRed));
                viewPager.setCurrentItem(2);
                break;
            case 3:
                textGoodReputation.setTextColor(getResources().getColor(R.color.mainRed));
                viewPager.setCurrentItem(3);
                break;
        }
    }
    public View.OnClickListener getStateOnClickListener(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetText();
                switch (v.getId()){
                    case R.id.attraction_nearby:
                        setChecked(0);
                        break;
                    case R.id.attraction_content:
                        setChecked(1);
                        break;
                    case R.id.attraction_rank:
                        setChecked(2);
                        break;
                    case R.id.attraction_good_reputation:
                        setChecked(3);
                        break;
                }
            }
        };
        return onClickListener;
    }
    public ViewPager.OnPageChangeListener getOnPageChangeListener(){
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetText();
                switch (position){
                    case 0:
                        textNearby.setTextColor(getResources().getColor(R.color.mainRed));
                        break;
                    case 1:
                        textContent.setTextColor(getResources().getColor(R.color.mainRed));
                        break;
                    case 2:
                        textRank.setTextColor(getResources().getColor(R.color.mainRed));
                        break;
                    case 3:
                        textGoodReputation.setTextColor(getResources().getColor(R.color.mainRed));
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        return onPageChangeListener;
    }
}
