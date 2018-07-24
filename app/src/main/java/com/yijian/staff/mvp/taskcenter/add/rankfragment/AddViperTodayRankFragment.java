package com.yijian.staff.mvp.taskcenter.add.rankfragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijian.staff.R;
import com.yijian.staff.mvp.base.mvc.MvcBaseFragment;
import com.yijian.staff.bean.RankBean;
import com.yijian.staff.mvp.taskcenter.add.AddViperRankListAdatper;
import com.yijian.staff.mvp.taskcenter.list.RankListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddViperTodayRankFragment extends MvcBaseFragment {
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_completed_precent)
    TextView tvCompletedPrecent;
    @BindView(R.id.tv_rank_position)
    TextView tvRankPosition;
    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private List<RankBean> rankBeanList = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_viper_today_rank;
    }

    @Override
    public void initView() {
        rankBeanList.clear();

        LinearLayoutManager layoutmanager = new LinearLayoutManager(getContext());
        //设置RecyclerView 布局
        rv.setLayoutManager(layoutmanager);
        for (int i = 0; i < 3; i++) {
            RankBean rankBean = new RankBean();
            rankBean.setName("员工" + (i + 1));
            rankBean.setCompletedPrecent((100 - (30 * i)) + "");
            rankBean.setTvRankPosition((i + 1) + "");
            rankBeanList.add(rankBean);
        }
        AddViperRankListAdatper adatper = new AddViperRankListAdatper(getContext(), rankBeanList);
        rv.setAdapter(adatper);
    }




    @OnClick(R.id.tv_more)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), RankListActivity.class);
        intent.putExtra("RANK_TYPE",RankListActivity.TODAY_RANK_TYPE);
        startActivity(intent);
    }
}