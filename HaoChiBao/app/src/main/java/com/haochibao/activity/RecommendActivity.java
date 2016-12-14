package com.haochibao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.haochibao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class RecommendActivity extends Activity {
    ImageView img_left;
    ListView recommend_list_view;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        img_left= (ImageView) findViewById(R.id.img_left);
        recommend_list_view = (ListView) findViewById(R.id.recommend_list_view);
        RecommendAdapter recommendAdapter = new RecommendAdapter(this,list);
        recommend_list_view.setAdapter(recommendAdapter);
        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recommend_list_view.setOnItemClickListener(onItemClickListener);
        getdate();
    }
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intent = new Intent(RecommendActivity.this,HotPotDetailsActivity.class);
                    startActivity(intent);
            Log.i("OnItemClickListener====","position"+position);
            }

    };
    List<String>list = new ArrayList<String>();
    public void getdate(){
        for(int i = 0;i<3;i++){
            String s = new String();
            list.add(s);
        }
    }

    class RecommendAdapter extends BaseAdapter{
        Context context;
        List<String>list;
        LayoutInflater layoutInflater;
        public RecommendAdapter(Context context, List<String>list){
            this.context =context;
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
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
               convertView = layoutInflater.inflate(R.layout.item_recommend,null);
            }
            return convertView;
        }
    }
}
