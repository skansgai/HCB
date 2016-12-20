package com.haochibao.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.haochibao.R;
import com.haochibao.utill.adapter.EntertainmentAdapter;

/**
 * Created by Administrator on 2016/12/12.
 */
public class ParkingActivity extends FragmentActivity {
    ListView parkingList;
    ImageView img_left;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        parkingList = (ListView) findViewById(R.id.parking_list);
        img_left = (ImageView) findViewById(R.id.img_left);

        img_left.setOnClickListener(getOnClickListener());
       // parkingList.setAdapter(new EntertainmentAdapter(this,));
    }

    public View.OnClickListener getOnClickListener() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.img_left:
                        finish();
                        break;

                }
            }
        };
        return onClickListener;
    }


}
