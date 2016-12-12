package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.haochibao.R;

/**
 * Created by Administrator on 2016/12/12.
 */

public class UserAdviseActvity extends Activity {
    private ImageView backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_advise);
        init();
    }
    //初始化方法
    public void init(){
        backBtn= (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(onClickListener);
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
