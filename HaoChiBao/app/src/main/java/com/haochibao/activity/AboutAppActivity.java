package com.haochibao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haochibao.R;

/**
 * Created by Administrator on 2016/12/12.
 */

public class AboutAppActivity extends Activity {
    private TextView version,appAbout;
    private ImageView backBtn;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        init();
    }
    public void init(){
        version= (TextView) findViewById(R.id.app_version);
        appAbout= (TextView) findViewById(R.id.user_agreement);
        backBtn= (ImageView) findViewById(R.id.back_btn);
        version.setOnClickListener(onClickListener);
        appAbout.setOnClickListener(onClickListener);
        backBtn.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
              switch (v.getId()){
                  case R.id.user_agreement:
                      intent=new Intent(AboutAppActivity.this,UseragreementActivity.class);
                      startActivity(intent);
                      break;
                  case R.id.back_btn:
                      finish();
                      break;
              }
        }
    };
}
