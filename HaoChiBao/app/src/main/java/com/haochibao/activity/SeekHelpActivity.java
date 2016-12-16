package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.haochibao.R;

/**
 * Created by Administrator on 2016/12/13.
 */
public class SeekHelpActivity extends Activity {
    ImageView img_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekhelp);
        img_left = (ImageView) findViewById(R.id.img_left);
        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
