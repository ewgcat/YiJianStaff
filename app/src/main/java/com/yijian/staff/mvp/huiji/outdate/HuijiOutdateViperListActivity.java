package com.yijian.staff.mvp.huiji.outdate;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yijian.staff.R;
import com.yijian.staff.mvp.huiji.bean.HuiJiViperBean;
import com.yijian.staff.mvp.huiji.viperlist.filter.HuijiViperFilterBean;
import com.yijian.staff.net.httpmanager.HttpManager;
import com.yijian.staff.net.response.ResultJSONObjectObserver;
import com.yijian.staff.rx.RxBus;
import com.yijian.staff.util.JsonUtil;
import com.yijian.staff.widget.NavigationBar2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.yijian.staff.net.httpmanager.HttpManager.GET_HUIJI_OUTDATE_VIPER_LIST_URL;

/**
 * 过期会员列表
 */
@Route(path = "/test/4")
public class HuijiOutdateViperListActivity extends AppCompatActivity {

    @BindView(R.id.rv_outdate)
    RecyclerView rv_outdate;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    List<HuiJiViperBean> vipOutdateInfoList = new ArrayList<HuiJiViperBean>();
    HuijiOutdateViperListAdapter huijiOutdateViperListAdapter;
    private int pageNum = 1;//页码
    private int pageSize = 1;//每页数量
    private int pages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outdate_viper_list);
        ButterKnife.bind(this);

        initTitle();
        initView();
        initData();
    }

    private void initData() {
        refresh();
    }

    private void initTitle() {
        NavigationBar2 navigationBar2 = findViewById(R.id.vip_over_navigation_bar2);
        navigationBar2.setTitle("过期会员");
        navigationBar2.setBackClickListener(this);
        navigationBar2.hideLeftSecondIv();

    }

    private void initView() {
        //设置RecyclerView 布局
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        //设置RecyclerView 布局
        rv_outdate.setLayoutManager(layoutmanager);
        huijiOutdateViperListAdapter = new HuijiOutdateViperListAdapter(this, vipOutdateInfoList);
        rv_outdate.setAdapter(huijiOutdateViperListAdapter);
        initComponent();

        Disposable disposable = RxBus.getDefault().toDefaultFlowable(HuijiViperFilterBean.class, new Consumer<HuijiViperFilterBean>() {
            @Override
            public void accept(HuijiViperFilterBean filterBean) throws Exception {
                refresh();
            }
        });
    }

    public void initComponent() {
        //设置 Header 为 BezierRadar 样式
        BezierRadarHeader header = new BezierRadarHeader(this).setEnableHorizontalDrag(true);
        header.setPrimaryColor(Color.parseColor("#1997f8"));
        refreshLayout.setRefreshHeader(header);
        //设置 Footer 为 球脉冲
        BallPulseFooter footer = new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale);
        footer.setAnimatingColor(Color.parseColor("#1997f8"));
        refreshLayout.setRefreshFooter(footer);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
            }
        });
    }


    private void refresh() {


        HashMap<String, String> map = new HashMap<>();
        map.put("pageNum", 1 + "");
        map.put("pageSize", 1 + "");

        HttpManager.getHasHeaderHasParam(GET_HUIJI_OUTDATE_VIPER_LIST_URL, map, new ResultJSONObjectObserver() {
            @Override
            public void onSuccess(JSONObject result) {
                refreshLayout.finishRefresh(2000, true);

                vipOutdateInfoList.clear();
                pageNum = JsonUtil.getInt(result, "pageNum") + 1;
                pages = JsonUtil.getInt(result, "pages");
                JSONArray records = JsonUtil.getJsonArray(result, "records");
                for (int i = 0; i < records.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) records.get(i);
                        HuiJiViperBean vipOutdateInfo = new HuiJiViperBean(jsonObject);
                        vipOutdateInfoList.add(vipOutdateInfo);
                    } catch (JSONException e) {


                    }
                }
                huijiOutdateViperListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                refreshLayout.finishRefresh(2000, false);//传入false表示刷新失败
                Toast.makeText(HuijiOutdateViperListActivity.this,msg,Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void loadMore() {


        HashMap<String, String> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("pageSize", pageSize + "");

        HttpManager.getHasHeaderHasParam(GET_HUIJI_OUTDATE_VIPER_LIST_URL,map, new ResultJSONObjectObserver() {
            @Override
            public void onSuccess(JSONObject result) {

                pageNum = JsonUtil.getInt(result, "pageNum") + 1;
                pages = JsonUtil.getInt(result, "pages");

                boolean hasMore = pages > pageNum ? true : false;
                refreshLayout.finishLoadMore(2000, true, hasMore);//传入false表示刷新失败

                JSONArray records = JsonUtil.getJsonArray(result, "records");
                for (int i = 0; i < records.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) records.get(i);
                        HuiJiViperBean vipOutdateInfo = new HuiJiViperBean(jsonObject);
                        vipOutdateInfoList.add(vipOutdateInfo);
                    } catch (JSONException e) {
                    }
                }
                huijiOutdateViperListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                boolean hasMore = pages > pageNum ? true : false;
                refreshLayout.finishLoadMore(2000, false, hasMore);//传入false表示刷新失败
                Toast.makeText(HuijiOutdateViperListActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }



}
