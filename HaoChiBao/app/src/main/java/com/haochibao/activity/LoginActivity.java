package com.haochibao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.haochibao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private List<View> mViews = new ArrayList<View>();
    private Button mEnterButton, mPassButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initView();
        initEvents();

    }

    private void initEvents() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {


            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        LayoutInflater mInflater = LayoutInflater.from(this);
        View tab01 = mInflater.inflate(R.layout.item_login01, null);
        View tab02 = mInflater.inflate(R.layout.item_login02, null);
        View tab03 = mInflater.inflate(R.layout.item_login03, null);
        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);
        mPassButton = (Button) tab01.findViewById(R.id.imgbtn_pass);
        mEnterButton = (Button) tab03.findViewById(R.id.imgbtn_enter);
        mPassButton.setOnClickListener(onClickListener);
        mEnterButton.setOnClickListener(onClickListener);

        mAdapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mViews.size();
            }
        };

        mViewPager.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        Intent intent;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgbtn_pass:
                    intent = new Intent(LoginActivity.this, HomeViewPagerActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.imgbtn_enter:
                    intent = new Intent(LoginActivity.this, HomeViewPagerActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

}


