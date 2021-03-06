package com.yijian.clubmodule.ui.vipermanage.student.viperlist.fragment;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yijian.clubmodule.R;
import com.yijian.clubmodule.net.requestbody.AccessStatisticsRequestBody;
import com.yijian.clubmodule.bean.CoachViperBean;
import com.yijian.clubmodule.ui.vipermanage.student.viperlist.adpater.CoachViperListAdapter;
import com.yijian.clubmodule.ui.vipermanage.student.viperlist.filter.CoachViperFilterBean;
import com.yijian.commonlib.db.DBManager;
import com.yijian.commonlib.db.bean.User;
import com.yijian.commonlib.base.mvc.MvcBaseFragment;
import com.yijian.clubmodule.net.httpmanager.HttpManager;
import com.yijian.commonlib.net.response.ResultJSONObjectObserver;
import com.yijian.commonlib.rx.RxBus;
import com.yijian.commonlib.util.CommonUtil;
import com.yijian.commonlib.util.JsonUtil;
import com.yijian.commonlib.widget.EmptyView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by yangk on 2018/3/6.
 * 全部会员信息
 */

public class CoachAllViperFragment extends MvcBaseFragment {


    RecyclerView rv_vip_all;
    SmartRefreshLayout refreshLayout;
    EmptyView empty_view;
    TextView tv_total_num;
    private List<CoachViperBean> coachViperBeanList = new ArrayList<>();
    private int pageNum = 1;//页码
    private int pageSize = 10;//每页数量
    private int total;

    private CoachViperListAdapter coachViperListAdapter;
    private CoachViperFilterBean coachViperFilterBean;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_vip_all_people_info;
    }

    @Override
    public void initView() {
        initView(getRootView());
        refresh(null);
    }


    private void initView(View view) {
        rv_vip_all = getRootView().findViewById(R.id.rv);
        refreshLayout = getRootView().findViewById(R.id.refreshLayout);
        empty_view = getRootView().findViewById(R.id.empty_view);
        tv_total_num = getRootView().findViewById(R.id.tv_total_num);
        empty_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh(null);
            }
        });
        String version = CommonUtil.getAccessStatisticsVersionName(getContext()) + " " + CommonUtil.getVersionCode(getContext());
        AccessStatisticsRequestBody body=new AccessStatisticsRequestBody("app_formal_student",version);
        HttpManager.postAccessStatistics(body, new ResultJSONObjectObserver(getLifecycle()) {
            @Override
            public void onSuccess(JSONObject result) {

            }

            @Override
            public void onFail(String msg) {

            }
        });
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        rv_vip_all.setLayoutManager(layoutmanager);

        coachViperListAdapter = new CoachViperListAdapter(getLifecycle(),getActivity(), coachViperBeanList, true);
        rv_vip_all.setAdapter(coachViperListAdapter);


        initComponent();

        Disposable disposable = RxBus.getDefault().toDefaultFlowable(CoachViperFilterBean.class, new Consumer<CoachViperFilterBean>() {
            @Override
            public void accept(CoachViperFilterBean filterBean) throws Exception {
                refresh(filterBean);
            }
        });

        empty_view.setButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh(coachViperFilterBean);
            }
        });
    }

    private void refresh(CoachViperFilterBean coachViperFilterBean) {
        showLoading();
        empty_view.setVisibility(View.GONE);
        coachViperBeanList.clear();
        pageNum = 1;
        pageSize = 10;
        this.coachViperFilterBean = coachViperFilterBean;
        HashMap<String, String> header = new HashMap<>();
        User user = DBManager.getInstance().queryUser();
        header.put("token", user.getToken());

        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        if (coachViperFilterBean != null) {

            if (coachViperFilterBean.getJoinTimeType() != -2) {
                map.put("joinTimeType", coachViperFilterBean.getJoinTimeType());
            }
            if (coachViperFilterBean.getExpiringDay() != -1) {
                map.put("expiringDay", coachViperFilterBean.getExpiringDay());
            }
            if (coachViperFilterBean.getSex() != -1) {
                map.put("sex", coachViperFilterBean.getSex());
            }
            if (coachViperFilterBean.getCourseType() != null) {
                map.put("courseType", coachViperFilterBean.getCourseType());
            }
            if (coachViperFilterBean.getBuyTime() != -1) {
                map.put("buyTime", coachViperFilterBean.getBuyTime());
            }
            if (!TextUtils.isEmpty(coachViperFilterBean.getStartTime())) {
                map.put("startTime", coachViperFilterBean.getStartTime());
            }
            if (!TextUtils.isEmpty(coachViperFilterBean.getEndTime())) {
                map.put("endTime", coachViperFilterBean.getEndTime());
            }


        }

        HttpManager.getCoachAllViperList(header, map, new ResultJSONObjectObserver(getLifecycle()) {
            @Override
            public void onSuccess(JSONObject result) {
                hideLoading();
                refreshLayout.finishRefresh(2000, true);

                coachViperBeanList.clear();

                pageNum = JsonUtil.getInt(result, "pageNum") + 1;
                total = JsonUtil.getInt(result, "total");
                if (total==-1){
                    total=0;
                }
                tv_total_num.setText("正式学员总人数："+total+"人");
                JSONArray records = JsonUtil.getJsonArray(result, "records");
                try {
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject jsonObject = (JSONObject) records.get(i);
                        CoachViperBean coachViperBean = new CoachViperBean(jsonObject);
                        coachViperBeanList.add(coachViperBean);
                    }
                    coachViperListAdapter.update(coachViperBeanList);
                    if (coachViperBeanList.size() == 0) {
                        empty_view.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {


                }

            }

            @Override
            public void onFail(String msg) {
                refreshLayout.finishRefresh(2000, false);//传入false表示刷新失败
                hideLoading();
                coachViperListAdapter.update(coachViperBeanList);
                if (coachViperBeanList.size() == 0) {
                    empty_view.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    public void loadMore() {
        showLoading();
        empty_view.setVisibility(View.GONE);

        HashMap<String, String> header = new HashMap<>();
        User user = DBManager.getInstance().queryUser();
        header.put("token", user.getToken());

        HashMap<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum + "");
        map.put("pageSize", pageSize + "");
        if (coachViperFilterBean != null) {
            if (coachViperFilterBean.getJoinTimeType() != -2) {
                map.put("joinTimeType", coachViperFilterBean.getJoinTimeType());
            }
            if (coachViperFilterBean.getExpiringDay() != -1) {
                map.put("expiringDay", coachViperFilterBean.getExpiringDay());
            }
            if (coachViperFilterBean.getSex() != -1) {
                map.put("sex", coachViperFilterBean.getSex());
            }
            if (coachViperFilterBean.getCourseType() != null) {
                map.put("courseType", coachViperFilterBean.getCourseType());
            }
            if (coachViperFilterBean.getBuyTime() != -1) {
                map.put("buyTime", coachViperFilterBean.getBuyTime());
            }

            if (!TextUtils.isEmpty(coachViperFilterBean.getStartTime())) {
                map.put("startTime", coachViperFilterBean.getStartTime());
            }
            if (!TextUtils.isEmpty(coachViperFilterBean.getEndTime())) {
                map.put("endTime", coachViperFilterBean.getEndTime());
            }


        }
        HttpManager.getCoachAllViperList(header, map, new ResultJSONObjectObserver(getLifecycle()) {
            @Override
            public void onSuccess(JSONObject result) {
                hideLoading();

                pageNum = JsonUtil.getInt(result, "pageNum") + 1;



                refreshLayout.finishLoadMore(2000, true, false);//传入false表示刷新失败

                JSONArray records = JsonUtil.getJsonArray(result, "records");
                for (int i = 0; i < records.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) records.get(i);
                        CoachViperBean coachViperBean = new CoachViperBean(jsonObject);
                        coachViperBeanList.add(coachViperBean);
                    } catch (JSONException e) {
                    }
                }
                coachViperListAdapter.update(coachViperBeanList);
                if (coachViperBeanList.size() == 0) {
                    empty_view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(String msg) {
                hideLoading();

                refreshLayout.finishLoadMore(2000, false, false);//传入false表示刷新失败
                coachViperListAdapter.update(coachViperBeanList);
                if (coachViperBeanList.size() == 0) {
                    empty_view.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void initComponent() {
        //设置 Header 为 BezierRadar 样式
        BezierRadarHeader header = new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true);
        header.setPrimaryColor(Color.parseColor("#1997f8"));
        refreshLayout.setRefreshHeader(header);
        //设置 Footer 为 球脉冲
        BallPulseFooter footer = new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale);
        footer.setAnimatingColor(Color.parseColor("#1997f8"));
        refreshLayout.setRefreshFooter(footer);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh(coachViperFilterBean);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
            }
        });
    }

}
