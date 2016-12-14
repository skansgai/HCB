package com.haochibao.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.haochibao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class HotPotDetailsActivity extends Activity {
    ListView listView;
    ImageView img_chakan_lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_pot_details);

        listView = (ListView) findViewById(R.id.hotpot_lv);
        img_chakan_lv = (ImageView) findViewById(R.id.img_chakan_lv);
        HotPotDetailsAdapter hotPotDetailsAdapter= new HotPotDetailsAdapter(this,list);
        listView.setAdapter(hotPotDetailsAdapter);
        img_chakan_lv.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.img_chakan_lv:
                    img_chakan_lv.setVisibility(View.VISIBLE);
                    break;
            }

        }
    };
    List<String>list = new ArrayList<String>();

    class HotPotDetailsAdapter extends BaseAdapter{
        Context context;
        List<String>list;
        LayoutInflater layoutInflater;
        public HotPotDetailsAdapter(Context context,List<String>list){
          this.context=context;
            this.list=list;
        }
        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater = LayoutInflater.from(context);
            if (convertView==null){
                convertView = layoutInflater.inflate(R.layout.item_hotpotlistview,null);
            }
            return convertView;
        }
    }
}
