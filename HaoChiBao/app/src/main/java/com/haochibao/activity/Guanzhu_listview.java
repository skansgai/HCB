package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.haochibao.R;
import com.haochibao.utill.adapter.GuanzhuListAdapter;

/**
 * Created by Administrator on 2016/12/12.
 */
public class Guanzhu_listview extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanzhu);
        ListView listView= (ListView) findViewById(R.id.list);
        listView.setAdapter(new GuanzhuListAdapter(Guanzhu_listview.this));

    }
}
