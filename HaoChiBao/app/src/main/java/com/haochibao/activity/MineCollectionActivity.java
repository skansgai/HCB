package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.haochibao.R;
import com.haochibao.utill.adapter.MineCollectionAdapter;

/**
 * Created by Administrator on 2016/12/10.
 */

public class MineCollectionActivity extends Activity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_collection_lv);
        init();
        listView.setAdapter(new MineCollectionAdapter(this));
    }

    public void init(){
        listView= (ListView) findViewById(R.id.my_collection_lv);
    }

}
