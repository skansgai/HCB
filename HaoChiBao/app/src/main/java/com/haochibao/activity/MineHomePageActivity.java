package com.haochibao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.haochibao.R;

/**
 * Created by Administrator on 2016/12/10.
 * 我的主页
 */

public class MineHomePageActivity extends Activity {
    private RelativeLayout minePage,setting,mineCollection;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_home_page);
        init();
    }
    //初始化
    public void init(){
        minePage= (RelativeLayout) findViewById(R.id.mine_page_btn);
        setting= (RelativeLayout) findViewById(R.id.setting);
        mineCollection= (RelativeLayout) findViewById(R.id.mine_collection);
        minePage.setOnClickListener(onClickListener);
        setting.setOnClickListener(onClickListener);
        mineCollection.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_page_btn:
                intent=new Intent(MineHomePageActivity.this,MineInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                intent=new Intent(MineHomePageActivity.this,Settingactivity.class);
                startActivity(intent);
                break;
            case R.id.mine_collection:
                intent=new Intent(MineHomePageActivity.this,MineCollectionActivity.class);
                startActivity(intent);
                break;
        }
        }
    };
}
