package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.haochibao.R;
import com.haochibao.fragment.HomeFragment;
import com.haochibao.fragment.MineFragment;
import com.haochibao.fragment.NoLoginMineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 * 首页
 */

public class HomeViewPagerActivity extends FragmentActivity {
    private RadioGroup radioGroup;
    private RadioButton homeBtn,discoverBtn,mineBtn;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private boolean isLogin=false; 
    private Fragment mineFragment;
    private Fragment homeFragment;
    private List<RadioButton> radioButtonList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_viewpager);
        init();
    }
    private void init(){
        radioGroup= (RadioGroup) findViewById(R.id.home_viewpager_group);
        homeBtn= (RadioButton) findViewById(R.id.home_btn);
        discoverBtn= (RadioButton) findViewById(R.id.discover_btn);
        mineBtn= (RadioButton) findViewById(R.id.mine_btn);
        viewPager= (ViewPager) findViewById(R.id.home_viewpager);

        radioButtonList=new ArrayList<RadioButton>();
        radioButtonList.add(homeBtn);
        radioButtonList.add(mineBtn);

        fragmentList=new ArrayList<Fragment>();
        homeFragment=new HomeFragment();
        if (isLogin){
             mineFragment=new MineFragment();
        }else {
             mineFragment=new NoLoginMineFragment();
        }
        if (homeFragment!=null){
            fragmentList.add(homeFragment);
        }
        if (mineFragment!=null){
         fragmentList.add(mineFragment);
        }

       viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
           @Override
           public Fragment getItem(int position) {
               return fragmentList.get(position);
           }
           @Override
           public int getCount() {
               return fragmentList.size();
           }
       });
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        viewPager.setOnPageChangeListener(onPageChangeListener);
    }
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
              switch (checkedId){
                  case R.id.home_btn:
                      homeBtn.setChecked(true);
                      homeBtn.setTextColor(getResources().getColor(R.color.mainRed));
                      mineBtn.setChecked(false);
                      mineBtn.setTextColor(getResources().getColor(R.color.textBlack));
                      discoverBtn.setChecked(false);
                      discoverBtn.setTextColor(getResources().getColor(R.color.textBlack));
                      viewPager.setCurrentItem(0);
                      break;
                  case R.id.discover_btn:
                      homeBtn.setChecked(false);
                      homeBtn.setTextColor(getResources().getColor(R.color.textBlack));
                      mineBtn.setChecked(false);
                      mineBtn.setTextColor(getResources().getColor(R.color.textBlack));
                      discoverBtn.setChecked(true);
                      discoverBtn.setTextColor(getResources().getColor(R.color.mainRed));
                      break;
                  case R.id.mine_btn:
                      homeBtn.setChecked(false);
                      homeBtn.setTextColor(getResources().getColor(R.color.textBlack));
                      mineBtn.setChecked(true);
                      mineBtn.setTextColor(getResources().getColor(R.color.mainRed));
                      discoverBtn.setChecked(false);
                      discoverBtn.setTextColor(getResources().getColor(R.color.textBlack));
                      viewPager.setCurrentItem(1);
                      break;

              }
        }
    };
    ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            radioButtonList.get(position).setChecked(true);
            radioButtonList.get(position).setTextColor(getResources().getColor(R.color.mainRed));
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
