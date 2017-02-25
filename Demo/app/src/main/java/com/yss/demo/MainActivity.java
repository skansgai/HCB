package com.yss.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {
    ImageView imageView=null;

    private SwipeLayout swipeLayout=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipelayout);
        imageView= (ImageView) findViewById(R.id.imageview);http://
                Picasso.with(getApplication()).load("119.29.60.248/public/images/food.jpeg").into(imageView);
        if (swipeLayout==null){
            swipeLayout= (SwipeLayout) findViewById(R.id.sample1);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            //swipeLayout.(SwipeLayout.DragEdge.Left, findViewById(R.id.bottom_wrapper));
            swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {
                    Log.i("TAG","onStartOpen");
                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    Log.i("TAG","onOpen");
                }

                @Override
                public void onStartClose(SwipeLayout layout) {
                    Log.i("TAG","onStartClose");
                }

                @Override
                public void onClose(SwipeLayout layout) {
                    Log.i("TAG","onClose");
                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                    Log.i("TAG","onUpdate");
                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                    Log.i("TAG","onHandRelease");
                }
            });
        }

    }
}
