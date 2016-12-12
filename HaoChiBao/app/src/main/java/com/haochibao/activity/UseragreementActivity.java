package com.haochibao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.haochibao.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/12.
 */

public class UseragreementActivity extends Activity {
    private ListView listView;
    private ImageView backBtn;
    String[] from={"title","content"};
    private int[] to={R.id.title,R.id.content};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement_lv);
        init();
    }
    public void init(){
        listView= (ListView) findViewById(R.id.user_agreement_lv);
        backBtn= (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<HashMap<String,String>> list=getDate();
        SimpleAdapter simpleAdapter=new SimpleAdapter(
                this,
                list,
                R.layout.activity_user_agreement_lv_item,
                from,
                to);
        listView.setAdapter(simpleAdapter);
    }

    List<HashMap<String,String>> list= new ArrayList<HashMap<String,String>>();
    public List<HashMap<String,String>> getDate(){
        for (int i=0;i<5;i++){
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("title",i+"免责声明");
            map.put("content","1、用户在使用本服务前需要注册一个“陌陌”帐号。“陌陌”帐号应当使用手机号码绑定注册，" +
                    "请用户使用尚未与“陌陌”帐号绑定的手机号码，以及未被陌陌科技根据本协议封禁的手机号码注册“陌陌”" +
                    "帐号。陌陌科技可以根据用户需求或产品需要对帐号注册和绑定的方式进行变更，而无须事先通知用户。\n" +
                    "2、“陌陌”系基于地理位置的移动社交产品，用户注册时应当授权陌陌科技公开及使用其地理位置信息方可" +
                    "成功注册“陌陌”帐号。故用户完成注册即表明用户同意陌陌科技提取、公开及使用用户的地理位置信息。" +
                    "如用户需要终止向其他用户公开其地理位置信息，可自行设置为隐身状态。\n" +
                    "3、鉴于“陌陌”帐号的绑定注册方式，您同意陌陌科技在注册时将使用您提供的手机号码及/或自动提取您的" +
                    "手机号码及自动提取您的手机设备识别码等信息用于注册。\n" +
                    "4、在用户注册及使用本服务时，陌陌科技需要搜集能识别用户身份的个人信息以便陌陌科技可以在必要时联" +
                    "系用户，或为用户提供更好的使用体验。陌陌科技搜集的信息包括但不限于用户的姓名、性别、年龄、出生" +
                    "日期、身份证号、地址、学校情况、公司情况、所属行业、兴趣爱好、常出没的地方、个人说明；陌陌科技" +
                    "同意对这些信息的使用将受限于第三条用户个人隐私信息保护的约束。");
            list.add(map);
        }
        return list;
    }
}
