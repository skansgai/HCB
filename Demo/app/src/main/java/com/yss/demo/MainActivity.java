package com.yss.demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alivc.player.AliVcMediaPlayer;
import com.daimajia.swipe.SwipeLayout;
import com.squareup.picasso.Picasso;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {
    ImageView imageView = null;
    private Context context;
    private SwipeLayout swipeLayout = null;
    private AliVcMediaPlayer mPlayer = null;
    private SeekBar mSeekBar = null;

    private StringBuilder builder = null;
    private GestureDetector gestureDetector = null;
    private com.alivc.player.MediaPlayer.VideoScalingMode mode = null;
    private BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo wifi = manager.getActiveNetworkInfo();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        imageView = (ImageView) findViewById(R.id.image1);
        /*if (swipeLayout==null){
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
        try {
            URL url = new URL("/uploadfile/Collfiles/20160721/20160721111414812.png");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
    }
}
