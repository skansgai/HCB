package com.haochibao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.haochibao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class HotPotDetailsActivity extends Activity {
    ListView listView;
    ImageView img_chakan_lv,img_left;
    LinearLayout phone_edit,hcb_share,hcb_comment;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_pot_details);

        listView = (ListView) findViewById(R.id.hotpot_lv);
        img_chakan_lv = (ImageView) findViewById(R.id.img_chakan_lv);
        img_left = (ImageView) findViewById(R.id.img_left);
        phone_edit = (LinearLayout) findViewById(R.id.phone_edit);
        hcb_share = (LinearLayout) findViewById(R.id.hcb_share);
        hcb_comment = (LinearLayout) findViewById(R.id.hcb_comment);
        HotPotDetailsAdapter hotPotDetailsAdapter= new HotPotDetailsAdapter(this,list);
        listView.setAdapter(hotPotDetailsAdapter);
        img_chakan_lv.setOnClickListener(onClickListener);
        img_left.setOnClickListener(onClickListener);
        phone_edit.setOnClickListener(onClickListener);
        hcb_share.setOnClickListener(onClickListener);
        hcb_comment.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.img_chakan_lv:
                    img_chakan_lv.setVisibility(View.VISIBLE);
                    break;
                case R.id.img_left:
                    finish();
                    break;
                case R.id.phone_edit:
                    createPopupWindow();
                    break;
                case R.id.hcb_share:
                    intent = new Intent(HotPotDetailsActivity.this,MyRecommendActivity.class);
                    startActivity(intent);
                    break;
                case R.id.hcb_comment:
                    intent = new Intent(HotPotDetailsActivity.this,CommentActivity.class);
                    startActivity(intent);
                    break;
            }

        }
    };
    List<String>list = new ArrayList<String>();

    class HotPotDetailsAdapter extends BaseAdapter{
        Context context;
        List<String>list;
        LayoutInflater layoutInflater;
        public HotPotDetailsAdapter(Context context,List<String>list){
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
            if (convertView==null){
                convertView = layoutInflater.inflate(R.layout.item_hotpotlistview,null);
            }
            return convertView;
        }
    }
    public void createPopupWindow(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View popview = layoutInflater.inflate(R.layout.popwindow_phone,null);
        PopupWindow popupWindow = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        popupWindow.showAtLocation(phone_edit, Gravity.BOTTOM,0,0);
        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.lineGray));
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.isShowing();
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                Log.i("OnTouchListener","OnTouchListener"+event.getAction());
                return false;
            }
        });

//        DialogUtill dialogUtill=new DialogUtill(this,popview,alertDialog);
//        alertDialog=dialogUtill.createDialog();

    }
}
