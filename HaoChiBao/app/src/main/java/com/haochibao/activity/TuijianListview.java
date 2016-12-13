package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.haochibao.R;
import com.haochibao.utill.adapter.TuijianListAdapter;

/**
 * Created by Administrator on 2016/12/12.
 */
public class TuijianListview extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuijian);
        ListView listView= (ListView) findViewById(R.id.item);
        listView.setAdapter(new TuijianListAdapter(TuijianListview.this));
    }
}
