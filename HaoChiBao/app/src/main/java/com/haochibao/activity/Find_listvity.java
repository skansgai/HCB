package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.haochibao.R;
import com.haochibao.utill.adapter.FindListvityAdapter;

/**
 * Created by Administrator on 2016/12/13.
 */
public class Find_listvity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        ListView listView= (ListView) findViewById(R.id.find_list);
        listView.setAdapter(new FindListvityAdapter(Find_listvity.this));
    }
}
