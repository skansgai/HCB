package com.haochibao.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.haochibao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class InterctionActivity extends Activity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction);
        listView = (ListView) findViewById(R.id.interaction_lv);
        InterctionAdapter interctionAdapter= new InterctionAdapter(InterctionActivity.this,list);
        listView.setAdapter(interctionAdapter);

    }
    List<String>list = new ArrayList<String>();
    class InterctionAdapter extends BaseAdapter{
        Context context;
        List<String>list;
        LayoutInflater layoutInflater;
        public InterctionAdapter(Context context,List<String>list){
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
            if(convertView==null){
                convertView = layoutInflater.inflate(R.layout.item_interaction,null);
            }
            return convertView;
        }
    }
}
