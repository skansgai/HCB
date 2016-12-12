package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.haochibao.R;
import com.haochibao.utill.adapter.EntertainmentAdapter;

/**
 * Created by Administrator on 2016/12/12.
 */
public class HotelActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        ListView entertainmentList = (ListView) findViewById(R.id.entertainment_list);
        entertainmentList.setAdapter(new EntertainmentAdapter(this));
    }
}
