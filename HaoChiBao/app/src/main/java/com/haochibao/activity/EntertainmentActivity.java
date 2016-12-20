package com.haochibao.activity;

import android.app.Activity;
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
import com.haochibao.utill.adapter.EntertainmentAdapter;
import com.haochibao.utill.model.EntertainmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class EntertainmentActivity extends Activity {
    ListView enterList;
    ImageView imgLeft;
    List<EntertainmentModel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        imgLeft = (ImageView) findViewById(R.id.img_left);
        enterList = (ListView) findViewById(R.id.entertainment_list);
        list = new ArrayList<EntertainmentModel>();
        getData();

        imgLeft.setOnClickListener(getOnClickListener());
        enterList.setAdapter(new EntertainmentAdapter(EntertainmentActivity.this,list));
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
    public void getData(){
        for (int i=0;i<12;i++){
            EntertainmentModel model = new EntertainmentModel();
            model.setName("巴将军");
            model.setLocation("沙坪坝");
            model.setDistance("1000");
            model.setPrice("45");
            model.setType("火锅");
            list.add(model);
        }
    }
}
