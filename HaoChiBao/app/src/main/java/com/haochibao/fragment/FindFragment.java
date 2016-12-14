package com.haochibao.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.adapter.FindListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */

public class FindFragment extends Fragment {
    private View view;
    private TextView location;
    private RadioGroup radioGroup;
    private RadioButton newsBtn,attentionBtn;
    private ImageView shareBtn;
    private ListView listView;
    private Context context;
    private List<HashMap<String,Object>> attentionList=new ArrayList<HashMap<String,Object>>();
    private Intent intent;
    private FindListViewAdapter findAdapter;
    private SimpleAdapter simpleAdapter;
    private String[] from={"userImage","userName","LeftIcon","RightIcon"};
    private int[] to={R.id.user_image,R.id.user_name,R.id.left_icon,R.id.right_icon};
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_find_lv,null);
        init();
        return view;
    }

    public void init(){
        location= (TextView) view.findViewById(R.id.location);
        radioGroup= (RadioGroup) view.findViewById(R.id.group);
        newsBtn= (RadioButton) view.findViewById(R.id.news);
        attentionBtn= (RadioButton) view.findViewById(R.id.attention);
        shareBtn= (ImageView) view.findViewById(R.id.share);
        listView= (ListView) view.findViewById(R.id.find_list_view);
        findAdapter=new FindListViewAdapter(context);
        listView.setAdapter(findAdapter);
        location.setOnClickListener(onClickListener);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        getAttentionData();
        simpleAdapter=new SimpleAdapter(
                context,
                attentionList,
                R.layout.activity_find_lv_attention_item,
                from,
                to
        );
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.location:
                break;
            case R.id.share:

                break;
            }
        }
    };
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.news:
                newsBtn.setTextColor(getResources().getColor(R.color.mainRed));
                attentionBtn.setTextColor(getResources().getColor(R.color.white));
                listView.setAdapter(findAdapter);
                break;
            case R.id.attention:
                newsBtn.setTextColor(getResources().getColor(R.color.white));
                attentionBtn.setTextColor(getResources().getColor(R.color.mainRed));
                listView.setAdapter(simpleAdapter);
                break;
        }
        }
    };
    public void getAttentionData(){
        for (int i=0;i<20;i++){
            HashMap<String,Object> map=new HashMap<String,Object>();
            map.put("userImage",R.mipmap.my_head_portrait);
            map.put("userName","娃哈哈！！！");
            int r= (int) (Math.random()*3+1);
            if (r==1){
                map.put("LeftIcon",R.mipmap.found_interactive_arro);
            }else if (r==2){
                map.put("LeftIcon",R.mipmap.found_interactive_arr);
            }else if (r==3){
                map.put("LeftIcon",R.mipmap.found_interactive_arrow);
            }
            map.put("RightIcon",R.mipmap.my_location);
            attentionList.add(map);
        }
    }
}
