package com.haochibao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.adapter.AppRecommendAdapter;
import com.haochibao.utill.adapter.MineRecommendAdapter;
import com.haochibao.utill.model.AppRecommendModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class TuijianActivity extends Activity {
    private ListView listView;
    private ImageView backBtn;
    private RadioGroup radioGroup;
    private RadioButton appPush,minePush;
    private TextView searchBtn;
    private AppRecommendAdapter appAdapter;
    private MineRecommendAdapter mineAdapter;
    private Intent intent;
    private List<AppRecommendModel> mineList=new ArrayList<AppRecommendModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuijian_lv);
        init();
    }
    public void init(){
        backBtn= (ImageView) findViewById(R.id.back_btn);
        radioGroup= (RadioGroup) findViewById(R.id.push_group);
        appPush= (RadioButton) findViewById(R.id.app_push);
        minePush= (RadioButton) findViewById(R.id.mine_push);
        searchBtn= (TextView) findViewById(R.id.search_btn);
        listView= (ListView) findViewById(R.id.push_list_view);
        backBtn.setOnClickListener(onClickListener);
        searchBtn.setOnClickListener(onClickListener);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        List<AppRecommendModel> list=getMineData();
        appAdapter=new AppRecommendAdapter(this,list);
        mineAdapter=new MineRecommendAdapter(this);
        listView.setAdapter(appAdapter);
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             switch (v.getId()){
                 case R.id.back_btn:
                     finish();
                     break;
                 case R.id.search_btn:
                     intent=new Intent(TuijianActivity.this,SearchActivity.class);
                     startActivity(intent);
                     break;
             }
        }
    };
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.app_push:
                appPush.setChecked(true);
                listView.setAdapter(appAdapter);
                appPush.setTextColor(getResources().getColor(R.color.mainRed));
                minePush.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.mine_push:
                appPush.setChecked(false);
                listView.setAdapter(mineAdapter);
                appPush.setTextColor(getResources().getColor(R.color.white));
                minePush.setTextColor(getResources().getColor(R.color.mainRed));
                break;
        }
        }
    };
    public List<AppRecommendModel> getMineData(){
        for (int i=0;i<10;i++){
            AppRecommendModel model=new AppRecommendModel();
            model.setFooImageId(R.mipmap.my_beauty_photo);
            model.setFooImageId1(R.mipmap.my_head_portrait);
            model.setFoodName("麻辣香锅");
            model.setFoodName1("好吃串串");
            model.setFoodGrde(3.5);
            model.setFoodGrde1(4.5);
            model.setZan(true);
            model.setZan1(false);
            model.setZanNumber(100);
            model.setZanNumber1(20);
            mineList.add(model);
        }
        return mineList;
    }
}
