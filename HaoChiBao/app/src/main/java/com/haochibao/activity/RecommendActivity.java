package com.haochibao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.model.Recommend;
import com.haochibao.utill.view_holder.RecommendViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.haochibao.R.id.name;

/**
 * Created by Administrator on 2016/12/12.
 */
public class RecommendActivity extends Activity {
    final static String TAG = "RecommendActivity";
    RecommendViewHolder viewHolder;
    ImageView img_left;
    ListView recommend_list_view;
    Intent intent;
    RadioGroup radioGroup;
    RecommendAdapter recommendAdapter;
    RadioButton recommend_chafing_dish, recommend_self_help,
            recommend_sichuan_cuisine, recommend_snack;
    String type = "火锅";
    Recommend recommend = new Recommend();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Bitmap bitmap = (Bitmap) msg.obj;
                if (viewHolder.img != null) {
                    viewHolder.img.setImageBitmap(bitmap);
                }
            }
            if (msg.what == 2) {
                recommendAdapter = new RecommendAdapter(RecommendActivity.this, list);
                Log.i("222222222",""+list.size());
                recommend_list_view.setAdapter(recommendAdapter);
                recommend_list_view.setOnItemClickListener(onItemClickListener);
                //     recommendAdapter.notifyDataSetChanged(); 有上面的new就不需要再用此方法。
            }
        }
    };
    List<Recommend> list = new ArrayList<Recommend>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        img_left = (ImageView) findViewById(R.id.img_left);
        radioGroup = (RadioGroup) findViewById(R.id.recommend_radiongroup);

        recommend_chafing_dish = (RadioButton) findViewById(R.id.recommend_chafing_dish);
        recommend_self_help = (RadioButton) findViewById(R.id.recommend_self_help);
        recommend_sichuan_cuisine = (RadioButton) findViewById(R.id.recommend_sichuan_cuisine);
        recommend_snack = (RadioButton) findViewById(R.id.recommend_snack);
        recommend_list_view = (ListView) findViewById(R.id.recommend_list_view);
        new Thread() {
            @Override
            public void run() {
                getDate();
            }
        }.start();
        recommendAdapter = new RecommendAdapter(RecommendActivity.this, list);
        recommend_list_view.setAdapter(recommendAdapter);
        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.recommend_chafing_dish:
                        type = recommend_chafing_dish.getText().toString();
                        get();
                        break;
                    case R.id.recommend_self_help:
                        type = recommend_self_help.getText().toString();
                        get();

                        break;
                    case R.id.recommend_sichuan_cuisine:
                        type = recommend_sichuan_cuisine.getText().toString();
                        get();

                        break;
                    case R.id.recommend_snack:
                        type = recommend_snack.getText().toString();
                        get();
                        break;

                }
            }
        });

    }

    public void get() {
        new Thread() {
            @Override
            public void run() {
                getDate();

            }
        }.start();
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            intent = new Intent(RecommendActivity.this, ShopDetailsActivity.class);
            intent.putExtra("XXXid",recommend.getId());
            startActivity(intent);
        }
    };


    class RecommendAdapter extends BaseAdapter {
        Context context;
        List<Recommend> list;
        LayoutInflater layoutInflater;

        public RecommendAdapter(Context context, List<Recommend> list) {
            this.context = context;
            this.list = list;
            layoutInflater = LayoutInflater.from(context);
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


            if (convertView == null) {
                viewHolder = new RecommendViewHolder();
                convertView = layoutInflater.inflate(R.layout.item_recommend, null);
                viewHolder.name = ((TextView) convertView.findViewById(name));
                viewHolder.img = ((ImageView) convertView.findViewById(R.id.img));
                viewHolder.grade = ((RatingBar) convertView.findViewById(R.id.grade));
                viewHolder.price = (TextView) convertView.findViewById(R.id.price);
                viewHolder.describe = (TextView) convertView.findViewById(R.id.describe);
                convertView.setTag(viewHolder);
            }
            viewHolder = (RecommendViewHolder) convertView.getTag();
            viewHolder.name.setText(list.get(position).getName());
            viewHolder.grade.setRating((float) list.get(position).getGrade());
            viewHolder.price.setText("" + list.get(position).getPrice());
            viewHolder.describe.setText(list.get(position).getDescribe());
            final String path = list.get(position).getImg();
            new Thread() {
                @Override
                public void run() {
                    try {
                        final URL url = new URL(path);
                        Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
                        Message message = new Message();
                        message.what = 1;
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            return convertView;
        }
    }

    public void getDate() {
       // list.clear();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String httpUrl = "http://192.168.7.23/index.php/home/index/getServiceType?typename="+ URLEncoder.encode(type,"utf-8");
            URL url = new URL(httpUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuilder.append(str);
                }
                Log.i("stringBuilder", "状态码" + stringBuilder.toString());
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                list = new ArrayList<Recommend>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jobj = jsonArray.getJSONObject(i);
                    recommend.setName(jobj.getString("name"));
                    recommend.setImg(jobj.getString("img"));
                    recommend.setGrade((float) jobj.getDouble("grade"));
                    recommend.setPrice(jobj.getDouble("price"));
                    recommend.setDescribe(jobj.getString("describe"));
                    recommend.setId(jobj.getInt("id"));
                    list.add(recommend);
                }
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
