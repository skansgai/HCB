package com.haochibao.activity;

import android.app.Activity;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.haochibao.R;
import com.haochibao.utill.model.HotPotDetails;
import com.haochibao.utill.view.FlowLayout;
import com.haochibao.utill.view_holder.HotPotDetailsViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class HotPotDetailsActivity extends Activity {
    ListView listView;
    ImageView img_chakan_lv, img_left;
    LinearLayout phone_edit, hcb_share, hcb_comment;
    FlowLayout hcbao_comment,hcb_share_desrcibe;
    List<HotPotDetails> hotPotDetailsList = new ArrayList<HotPotDetails>();
    String[] comment = {"店家不错", "服务不错", "上菜快菜量多"};
    String[] desribe = {"店家不错", "服务不错", "上菜快菜量多"};
    LayoutInflater layoutInflater;
    TextView poptextview;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_pot_details);
        layoutInflater = LayoutInflater.from(this);
        View headView = layoutInflater.inflate(R.layout.hot_pot_details_head, null);

        listView = (ListView) findViewById(R.id.hotpot_lv);
        img_chakan_lv = (ImageView) findViewById(R.id.img_chakan_lv);
        img_left = (ImageView) headView.findViewById(R.id.img_left);
        phone_edit = (LinearLayout) headView.findViewById(R.id.phone_edit);
        hcb_share = (LinearLayout) headView.findViewById(R.id.hcb_share);
        hcb_comment = (LinearLayout) headView.findViewById(R.id.hcb_comment);
        hcbao_comment = (FlowLayout) headView.findViewById(R.id.hcbao_comment);
        hcb_share_desrcibe = (FlowLayout) headView.findViewById(R.id.hcb_share_describe);
        new Thread() {
            @Override
            public void run() {
                getService();
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                getUserComment();
            }
        }.start();

        HotPotDetailsAdapter hotPotDetailsAdapter = new HotPotDetailsAdapter(this, hotPotDetailsList);
        listView.addHeaderView(headView);
        listView.setAdapter(hotPotDetailsAdapter);

        img_chakan_lv.setOnClickListener(onClickListener);
        img_left.setOnClickListener(onClickListener);
        phone_edit.setOnClickListener(onClickListener);
        hcb_share.setOnClickListener(onClickListener);
        hcb_comment.setOnClickListener(onClickListener);
        addSearchList();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
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
                    intent = new Intent(HotPotDetailsActivity.this, MyRecommendActivity.class);
                    startActivity(intent);
                    break;
                case R.id.hcb_comment:
                    intent = new Intent(HotPotDetailsActivity.this, CommentActivity.class);
                    startActivity(intent);
                    break;
            }

        }
    };

    class HotPotDetailsAdapter extends BaseAdapter {
        Context context;
        List<HotPotDetails> hotPotDetailsList;
        LayoutInflater layoutInflater;


        public HotPotDetailsAdapter(Context context, List<HotPotDetails> hotPotDetailsList) {
            this.context = context;
            this.hotPotDetailsList = hotPotDetailsList;

        }

        @Override
        public int getCount() {
            return hotPotDetailsList.size();
        }

        @Override
        public Object getItem(int position) {
            return hotPotDetailsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HotPotDetailsViewHolder viewHolder;

            layoutInflater = LayoutInflater.from(context);
            if (convertView == null) {
                 viewHolder = new HotPotDetailsViewHolder();
                convertView = layoutInflater.inflate(R.layout.item_hotpotlistview, null);
                viewHolder.user_name = (TextView) convertView.findViewById(R.id.user_name);
                viewHolder.time = (TextView) convertView.findViewById(R.id.time);
                viewHolder.grade = (RatingBar) convertView.findViewById(R.id.grade);
                viewHolder.comment = (TextView) convertView.findViewById(R.id.comment);
                convertView.setTag(viewHolder);
            }
            viewHolder = (HotPotDetailsViewHolder) convertView.getTag();
            viewHolder.user_name.setText(hotPotDetailsList.get(position).getUser_name());
            viewHolder.time.setText(hotPotDetailsList.get(position).getTime());
            viewHolder.grade.setRating(hotPotDetailsList.get(position).getGrade().floatValue());
            viewHolder.comment.setText(hotPotDetailsList.get(position).getComment());
            return convertView;
        }
    }

    public void createPopupWindow() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View popview = layoutInflater.inflate(R.layout.popwindow_phone, null);
        poptextview = (TextView) popview.findViewById(R.id.phone);
        poptextview.setText(phone);
        PopupWindow popupWindow = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        popupWindow.showAtLocation(phone_edit, Gravity.BOTTOM, 0, 0);
        ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.lineGray));
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.isShowing();
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                Log.i("OnTouchListener", "OnTouchListener" + event.getAction());
                return false;
            }
        });
    }

    public void addSearchList() {
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < comment.length; i++) {
            TextView subview = (TextView) inflater.inflate(R.layout.hot_search_child, hcbao_comment, false);
            subview.setText(comment[i]);
            hcbao_comment.addView(subview);
        }
        for (int i= 0;i<desribe.length;i++){
            TextView textView = (TextView) inflater.inflate(R.layout.hot_search_child,hcb_share_desrcibe,false);
            textView.setText(desribe[i]);
            hcb_share_desrcibe.addView(textView);
        }
    }

    public void getService() {

        try {
            StringBuilder stringBuilder = new StringBuilder();
            String httpUrl = "http://10.0.2.2/index.php/home/index/getServiceInfo?id=1";
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
                Log.i("stringBuilder", "状态码" + stringBuilder);
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                HotPotDetailsViewHolder viewHolder = new HotPotDetailsViewHolder();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String name = obj.optString("name");
                    String img = obj.optString("img");
                    String grade = obj.optString("grade");
                    String price = obj.optString("price");
                    String location = obj.optString("location");
                    String describe = obj.optString("describe");
                    phone = obj.optString("phone");
                    String zan = obj.optString("zan");
                    String visited = obj.optString("visited");

                    viewHolder.name = (TextView) findViewById(R.id.name);
                    viewHolder.img = (ImageView) findViewById(R.id.img);
                    viewHolder.grade = (RatingBar) findViewById(R.id.grade);
                    viewHolder.price = (TextView) findViewById(R.id.price);
                    viewHolder.location = (TextView) findViewById(R.id.location);
                    viewHolder.describe = (TextView) findViewById(R.id.describe);
                    viewHolder.phone = (TextView) findViewById(R.id.phone);
                    // viewHolder.zan = (TextView) findViewById(R.id.zan);

                    viewHolder.name.setText(name);
                    Log.i("能接收到数据吗=========>", "" + name);
                    //viewHolder.img.setImageBitmap();
                    viewHolder.grade.setRating(Float.parseFloat(grade));
                    viewHolder.price.setText(price);
                    viewHolder.location.setText(location);
                    viewHolder.describe.setText(describe);
                    viewHolder.phone.setText(phone);
                    // viewHolder.zan.setText(zan);
                    // viewHolder.visited.setText(visited);


                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getUserComment() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String httpUrl = "http://10.0.2.2/index.php/home/index/getServiceComment?id=1";
            URL url = new URL(httpUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        httpURLConnection.getInputStream()));
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuilder.append(str);
                }
                Log.i("stringBuilder", "" + stringBuilder);
                JSONObject object = new JSONObject(stringBuilder.toString());
                JSONArray jsonArray = object.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    HotPotDetails hotPotDetails = new HotPotDetails();
                    hotPotDetails.setUser_name(object1.getString("user_name"));
                    hotPotDetails.setComment(object1.getString("content"));
                    hotPotDetails.setTime(object1.getString("time"));
                    hotPotDetails.setGrade(object1.getDouble("grade"));
                    hotPotDetailsList.add(hotPotDetails);
                }
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
