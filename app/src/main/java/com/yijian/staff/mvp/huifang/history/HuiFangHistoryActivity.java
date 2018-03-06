package com.yijian.staff.mvp.huifang.history;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yijian.staff.R;
import com.yijian.staff.mvp.huifang.bean.HuiFangInfo;
import com.yijian.staff.util.Logger;
import com.yijian.staff.util.system.StatusBarUtils;
import com.yijian.staff.widget.NavigationBar;
import com.yijian.staff.widget.NavigationBarItemFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HuiFangHistoryActivity extends AppCompatActivity {
    private List<HuiFangInfo> huiFangInfoList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setLightStatusBar(this, 0xffffff);

        setContentView(R.layout.activity_hui_fang_history);

        initView();

    }

    private void initView() {
        NavigationBar navigationBar = (NavigationBar) findViewById(R.id.hui_fang_history_navigation_bar);
        navigationBar.setTitle("回访记录", "#000000");
        navigationBar.setNavigationBarBackgroudColor(Color.parseColor("#ffffff"));
        navigationBar.setLeftButtonView(NavigationBarItemFactory.createNavigationItemImageView(this, NavigationBarItemFactory.NavigationItemType.BACK_BLACK));
        navigationBar.setLeftButtonClickListener(NavigationBarItemFactory.createBackClickListener(this));

        RecyclerView recyclerView = findViewById(R.id.rlv);



        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("name","朱沙");
            jsonObject.put("headUrl","headUrl");
            jsonObject.put("sex","男");
            jsonObject.put("quanyi","私教课");
            jsonObject.put("outdateTime","2018-2-2");
            jsonObject.put("outdateReason","工作太忙");
            jsonObject.put("huifangType","过期回访");
            jsonObject.put("huifangReason","　　中新社北京3月5日电 (记者 唐贵江)农业部部长韩长赋5日在全国两会“部长通道”回答媒体记者提问时表示，近年来中国粮食连年丰收，粮食产量连续5年稳定在1.2万亿斤的台阶上，化肥的使用对粮食增长有重要作用，但不能说是化肥“喂”出来的。2017年，中国的化肥使用实现了负增长，提前三年实现了“十三五”目标，也就是化肥农药使用量的零增长");
            for (int i = 0; i < 10; i++) {
                HuiFangInfo huiFangInfo = new HuiFangInfo(jsonObject);
                huiFangInfoList.add(huiFangInfo);
            }


            LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
            //设置RecyclerView 布局
            recyclerView.setLayoutManager(layoutmanager);
            HuiFangHistoryAdapter huiFangHistoryAdapter = new HuiFangHistoryAdapter(this, huiFangInfoList);
            recyclerView.setAdapter(huiFangHistoryAdapter);
        } catch (JSONException e) {
            Logger.i("TEST","JSONException: "+e);

        }
    }


}
