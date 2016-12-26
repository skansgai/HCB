package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.haochibao.R;

/**
 * Created by Administrator on 2016/12/26.
 */

public class RegisterActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_register);
    }
}
