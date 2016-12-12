package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.haochibao.R;
import com.haochibao.utill.adapter.MineCollectionAdapter;

/**
 * Created by Administrator on 2016/12/10.
 */

public class MineCollectionActivity extends Activity {
    private ListView listView;
    private ImageView backBtn,cleanCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_collection_lv);
        init();
    }

    public void init(){
        backBtn= (ImageView) findViewById(R.id.back_btn);
        cleanCollection= (ImageView) findViewById(R.id.clean_collection);
        backBtn.setOnClickListener(onClickListener);
        listView= (ListView) findViewById(R.id.my_collection_lv);
        listView.setAdapter(new MineCollectionAdapter(this));
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
            finish();
            break;
            }
        }
    };
}
