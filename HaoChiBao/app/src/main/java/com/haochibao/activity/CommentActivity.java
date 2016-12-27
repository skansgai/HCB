package com.haochibao.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.adapter.CommentListAdater;
import com.haochibao.utill.model.CommentModel;
import com.haochibao.utill.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/10.
 */
public class CommentActivity extends Activity {
    LinearLayout commentSquare;
    RadioGroup rgTop;
    RadioButton rgTopLeft;
    RadioButton rgTopRight;
    ImageView btnBack;
    TextView btnSearch;
    List<CommentModel> list = new ArrayList<CommentModel>();
    ListView commentList;
    FlowLayout searchList;
    String[] searchNames = {"全部","好评","差评","最新点评","团购点评（81）", "分量较少（51）",
            "请客（4）","锅底不错（6）","店面大","口味真不错（66）","服务热情" , "位置不错"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout listHead = (LinearLayout) inflater.inflate(R.layout.comment_list_head,null);
        commentList = (ListView) findViewById(R.id.comment_list);
        commentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                commentList.deferNotifyDataSetChanged();
            }
        });
        searchList = (FlowLayout) listHead.findViewById(R.id.comment_search_list);
        rgTop = (RadioGroup) findViewById(R.id.rg_top);
        rgTopLeft = (RadioButton) findViewById(R.id.rg_top_left);
        rgTopRight = (RadioButton) findViewById(R.id.rg_top_right);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        btnSearch = (TextView) findViewById(R.id.btn_top_search);
        getData();
        addSearchList();
        //设置RadioButton的初始状态
        resetRadioButton(1);
        rgTopLeft.setChecked(true);
        
        commentList.addHeaderView(listHead,null,true);
        commentList.setAdapter(new CommentListAdater(this,list));
        rgTop.setOnCheckedChangeListener(getOnCheckedChangeListener());
        btnBack.setOnClickListener(getOnClickListener());
        btnSearch.setOnClickListener(getOnClickListener());
    }
    public void getData(){
        for (int i=0;i<9;i++){
            CommentModel model = new CommentModel();
            model.content="11.9元斩获一箱原价40元的天友核桃花生奶";
            model.scan="134";
            model.time="上周五";
            model.userName = "日川冈坂";
            model.userLevel = "99";
            if (i%3==1){
                model.imgIds.add(R.mipmap.my_beauty_photo);
                model.imgIds.add(R.mipmap.entertainment_item_img);
            }else {
                model.imgIds.add(R.mipmap.my_beauty_photo);
                model.imgIds.add(R.mipmap.entertainment_item_img);
                model.imgIds.add(R.mipmap.my_head_portrait);
                model.imgIds.add(R.mipmap.my_beauty_photo);
                model.imgIds.add(R.mipmap.entertainment_item_img);
                model.imgIds.add(R.mipmap.my_head_portrait);
            }
            list.add(model);
        }
    }
    public void addSearchList(){
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i=0;i<searchNames.length;i++){
            TextView hotSearchChild = (TextView) inflater.inflate(R.layout.hot_search_child,searchList,false);
            hotSearchChild.setText(searchNames[i]);
            searchList.addView(hotSearchChild);
        }
    }
    public RadioGroup.OnCheckedChangeListener getOnCheckedChangeListener(){
        RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.rg_top_left:
                        resetRadioButton(1);
                        break;
                    case R.id.rg_top_right:
                        resetRadioButton(2);
                        break;
                }
            }
        };
        return onCheckedChangeListener;
    }
    public void resetRadioButton(int i){
        switch (i){
            case 1:
                rgTopLeft.setTextColor(getResources().getColor(R.color.mainRed));
                rgTopRight.setTextColor(getResources().getColor(R.color.white));
                break;
            case 2:
                rgTopRight.setTextColor(getResources().getColor(R.color.mainRed));
                rgTopLeft.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }
    public View.OnClickListener getOnClickListener(){
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getId()){
                    case R.id.btn_back:
                        finish();
                        break;
                    case R.id.btn_top_search:
                        intent = new Intent(CommentActivity.this,SearchActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };
        return onClickListener;
    }
}
