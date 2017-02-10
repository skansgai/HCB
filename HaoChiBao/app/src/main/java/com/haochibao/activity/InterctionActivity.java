package com.haochibao.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.http.AsyncImageLoader;
import com.haochibao.utill.http.GetHttp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Created by Administrator on 2016/12/13.
 */
public class InterctionActivity extends Activity {
    ListView listView;
    ImageView img_left;
    private AsyncImageLoader asyncImageLoader;
    InterctionAdapter interctionAdapter;
    private ImageView interactImageview;
    private List< HashMap<String,String>> list= new ArrayList< HashMap<String,String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interaction);
        init();
        getActivityImage();
        cookInfo();
        interctionAdapter= new InterctionAdapter(InterctionActivity.this,list);
        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void init(){
        listView = (ListView) findViewById(R.id.interaction_lv);
        img_left = (ImageView) findViewById(R.id.img_left);
        interactImageview= (ImageView) findViewById(R.id.interact_image);
    }
    class InterctionAdapter extends BaseAdapter{
        Context context;
        List< HashMap<String,String>> list;
        LayoutInflater layoutInflater;
        public InterctionAdapter(Context context,List< HashMap<String,String>> list){
            this.context=context;
            this.list=list;
            asyncImageLoader=new AsyncImageLoader();
        }
        @Override
        public int getCount() {
            return list.size();
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
            ViewHodler viewHodler;
            layoutInflater = LayoutInflater.from(context);
            if(convertView==null){
                viewHodler=new ViewHodler();
                convertView = layoutInflater.inflate(R.layout.item_interaction,null);
                viewHodler.imageView= (ImageView) convertView.findViewById(R.id.user_image);
                viewHodler.userName= (TextView) convertView.findViewById(R.id.user_name);
                viewHodler.describe= (TextView) convertView.findViewById(R.id.describe);
                viewHodler.time= (TextView) convertView.findViewById(R.id.time);
                convertView.setTag(viewHodler);
            }
            viewHodler= (ViewHodler) convertView.getTag();
            String userName=list.get(position).get("cookName") ;
            String describe=list.get(position).get("describle");
            String time=list.get(position).get("time");
            viewHodler.userName.setText(userName);
            viewHodler.describe.setText(describe);
            viewHodler.time.setText(time);
            String uri=list.get(position).get("imgPath");
            ImageView imageView=viewHodler.imageView;
            imageView.setTag(uri);
            Drawable cacheImage=asyncImageLoader.loadDrawable(uri, new AsyncImageLoader.ImageCallback() {
                @Override
                public void imageLoaded(Drawable imageDrawable, String imageUrl) {
                    ImageView imageViewByTog=(ImageView)listView.findViewWithTag(imageUrl);
                    if (imageViewByTog != null){
                        imageViewByTog.setImageDrawable(imageDrawable);
                    }
                }
            });
            if (cacheImage == null){
                imageView.setImageResource(R.mipmap.ic_launcher);
            }else {
                imageView.setImageDrawable(cacheImage);
                viewHodler.imageView.setImageDrawable(cacheImage);
            }
            return convertView;
        }
        class ViewHodler{
            private ImageView imageView;
            private TextView userName;
            private TextView describe;
            private TextView time;

        }
    }
    public void cookInfo(){
        String uri="http://119.29.60.248/index.php/home/index/getInteract";
        try {
            URL url=new URL(uri);
            GetHttp getHttp=new GetHttp(this,url);
            getHttp.setOnClicklistener(new GetHttp.onResultListener() {
                @Override
                public void onClick(String data) throws JSONException, IOException {
                    Log.i("====================",data);
                    if (data!=null){
                        JSONObject object=new JSONObject(data);
                        JSONArray array=object.optJSONArray("result");
                        for (int i=0;i<array.length();i++){
                            JSONObject subObject=array.getJSONObject(i);
                            HashMap<String,String> map=new HashMap<String, String>();
                            map.put("imgPath",subObject.optString("img",""));
                            map.put("cookName",subObject.optString("user_name"));
                            map.put("describle",subObject.optString("describle",""));
                            map.put("time",subObject.optString("time",""));
                            list.add(map);
                            Message message=new Message();
                            message.what=0;
                            handler.sendMessage(message);
                        }
                    }

                }
            });
            getHttp.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void getActivityImage(){
        String uri="http://119.29.60.248/index.php/home/index/getInteracctImage?state=1";
        try {
            URL url=new URL(uri);
            GetHttp getHttp=new GetHttp(this,url);
            getHttp.setOnClicklistener(new GetHttp.onResultListener() {
                @Override
                public void onClick(String data) throws JSONException, IOException {
                    if (data!=null){
                        Log.i("==========>",data);
                        JSONObject object=new JSONObject(data);
                        JSONArray array=object.optJSONArray("result");
                        for (int i=0;i<array.length();i++){
                            JSONObject subJson=array.getJSONObject(i);
                            String path=subJson.optString("path",null);
                            String name=subJson.optString("name",null);
                            Bitmap bitmap= BitmapFactory.decodeStream(new URL(path).openStream());
                            Message message=new Message();
                            message.what=1;
                            message.obj=bitmap;
                            handler.sendMessage(message);
                        }
                    }

                }
            });
            getHttp.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                listView.setAdapter(interctionAdapter);
            }
            if (msg.what==1){
                Bitmap bitmap= (Bitmap) msg.obj;
                if (bitmap!=null){
                    interactImageview.setImageBitmap(bitmap);
                }
            }
        }
    };
}
