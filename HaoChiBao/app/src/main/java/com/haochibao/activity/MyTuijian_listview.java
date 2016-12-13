package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.haochibao.R;
import com.haochibao.utill.adapter.MytuijianListAdapter;

/**
 * Created by Administrator on 2016/12/13.
 */
public class MyTuijian_listview extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytuijian);
        ListView listView= (ListView) findViewById(R.id.tuijian_list);
        listView.setAdapter(new MytuijianListAdapter(MyTuijian_listview.this));
    }
}
