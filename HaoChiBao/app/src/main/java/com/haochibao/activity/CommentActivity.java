package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.haochibao.R;
import com.haochibao.utill.adapter.CommentListAdater;

/**
 * Created by Administrator on 2016/12/10.
 */
public class CommentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ListView commentList = (ListView) findViewById(R.id.comment_list);
        commentList.setAdapter(new CommentListAdater(this));
    }
}
