package com.haochibao.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.haochibao.R;
import com.haochibao.activity.LoginActivity;
import com.haochibao.activity.RegisterActivity;
import com.haochibao.activity.Settingactivity;

/**
 * Created by Administrator on 2016/12/13.
 * 未登录我的页面
 */

public class NoLoginMineFragment extends Fragment {
    private View view;
    private TextView loginBtn,moreLogin;
    private RelativeLayout mineCollection,mineSpoor,minePageBtn,setting;
    private CheckBox dayNightBtn;
    private Intent intent;
    private Context context;
    private Bundle bundle;
    private boolean isLogin=false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view=inflater.inflate(R.layout.activity_no_login_mine,null);
        context=getActivity();
        init();
        return view;
    }
    //初始化控件
    public void init(){
        loginBtn= (TextView) view.findViewById(R.id.login_btn);
        moreLogin= (TextView) view.findViewById(R.id.more_login);
        mineCollection= (RelativeLayout) view.findViewById(R.id.mine_collection);
        mineSpoor= (RelativeLayout) view.findViewById(R.id.mine_spoor);
        minePageBtn= (RelativeLayout) view.findViewById(R.id.mine_page_btn);
        setting= (RelativeLayout) view.findViewById(R.id.setting);
        dayNightBtn= (CheckBox) view.findViewById(R.id.day_night_btn);
        bundle=getArguments();//获得Activity传值

        loginBtn.setOnClickListener(onClickListener);
        moreLogin.setOnClickListener(onClickListener);
        mineCollection.setOnClickListener(onClickListener);
        minePageBtn.setOnClickListener(onClickListener);
        setting.setOnClickListener(onClickListener);
        mineSpoor.setOnClickListener(onClickListener);
        dayNightBtn.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                //登陆按钮
                intent=new Intent(context,RegisterActivity.class);
                boolean isLogin=getActivity().getIntent().getBooleanExtra("isLogin",false);
                intent.putExtra("toLogin",isLogin);
                startActivityForResult(intent,100);
                break;
            case R.id.more_login:
                //更多登陆

                break;
            case R.id.mine_collection:
                //我的收藏
                createLoginDialog();
                break;
            case R.id.mine_spoor:
                //我的足迹
                createLoginDialog();
                break;
            case R.id.mine_page_btn:
                //我的主页
                createLoginDialog();
                break;
            case R.id.setting:
                //设置
                intent=new Intent(context, Settingactivity.class);
                startActivity(intent);
                break;
            case R.id.day_night_btn:
                //夜间模式
                if (dayNightBtn.isChecked()){
                    Toast.makeText(context,"夜间模式",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"日间模式",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        }
    };
    public void createLoginDialog(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.jinggao)
        .setTitle("提示")
        .setMessage("您还未登陆，是否立即登陆")
        .setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent=new Intent(context,RegisterActivity.class);
                boolean isLogin=getActivity().getIntent().getBooleanExtra("isLogin",false);
                intent.putExtra("toLogin",isLogin);
                startActivityForResult(intent,100);
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==101){
            isLogin=data.getBooleanExtra("toLogin",false);
        }
    }
}
