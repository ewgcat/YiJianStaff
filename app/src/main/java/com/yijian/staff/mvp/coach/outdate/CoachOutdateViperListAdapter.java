package com.yijian.staff.mvp.coach.outdate;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yijian.staff.R;
import com.yijian.staff.bean.CoachViperBean;
import com.yijian.staff.mvp.coach.card.CoachVipCardListAdapter;
import com.yijian.staff.mvp.coach.detail.CoachViperDetailActivity;
import com.yijian.staff.mvp.coach.viperlist.adpater.CoachViperListAdapter;
import com.yijian.staff.net.httpmanager.HttpManager;
import com.yijian.staff.net.response.ResultJSONObjectObserver;
import com.yijian.staff.util.CommonUtil;
import com.yijian.staff.util.DateUtil;
import com.yijian.staff.util.GlideCircleTransform;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by yangk on 2018/3/26.
 */

public class CoachOutdateViperListAdapter extends RecyclerView.Adapter<CoachOutdateViperListAdapter.ViewHolder>  {

    private List<CoachViperBean> coachViperBeanList;
    private Context context;

    public CoachOutdateViperListAdapter(Context context, List<CoachViperBean> coachViperBeanList){
        this.context = context;
        this.coachViperBeanList = coachViperBeanList;
    }


    @Override
    public CoachOutdateViperListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outdate_coach_date, parent, false);
        CoachOutdateViperListAdapter.ViewHolder holder = new CoachOutdateViperListAdapter.ViewHolder(view);
        return holder;
    }

    public void update(List<CoachViperBean> coachViperBeanList) {
        this.coachViperBeanList = coachViperBeanList;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(CoachOutdateViperListAdapter.ViewHolder holder, int position) {
        CoachViperBean coachViperBean = coachViperBeanList.get(position);

        holder.tv_name.setText(coachViperBean.getName());
        int resId;
        if (coachViperBean.getSex().equals("1")) {
            resId = R.mipmap.lg_man;
        } else if (coachViperBean.getSex().equals("2")) {
            resId = R.mipmap.lg_women;
        } else {
            resId = R.mipmap.lg_man;

        }
        holder.iv_gender.setImageResource(resId);

        String headImg = coachViperBean.getHeadImg();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.placeholder)
                .error(R.mipmap.placeholder)
                .transform(new GlideCircleTransform())
                .priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context).load(headImg).apply(options).into( holder.iv_header);

        //详情
        holder.lin_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CoachViperDetailActivity.class);
                intent.putExtra("vipType", 3);
                intent.putExtra("coachViperBean", coachViperBean);
                context.startActivity(intent);
            }
        });

        Boolean isProtected = coachViperBean.getProtected();
        if (isProtected) {
            Drawable jd_choose = context.getResources().getDrawable(R.mipmap.suo);
            jd_choose.setBounds(0, 0, jd_choose.getMinimumWidth(), jd_choose.getMinimumHeight());
            holder.tv_call.setCompoundDrawables(jd_choose, null, null, null);
            holder.tv_call.setText("保护7天");
        } else {
            Drawable jd_choose = context.getResources().getDrawable(R.mipmap.dianhua);
            jd_choose.setBounds(0, 0, jd_choose.getMinimumWidth(), jd_choose.getMinimumHeight());
            holder.tv_call.setCompoundDrawables(jd_choose, null, null, null);
            holder.tv_call.setText("");
            String mobile = coachViperBean.getMobile();
            holder.tv_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(mobile)){
                        if (CommonUtil.isPhoneFormat(mobile)){
                            CommonUtil.callPhone(context, mobile);
                            HashMap<String, String> param = new HashMap<>();
                            param.put("interviewRecordId","4");
                            param.put("memberId",coachViperBean.getMemberId());
                            HttpManager.getHasHeaderHasParam(HttpManager.GET_VIP_COACH_HUI_FANG_CALL_PHONE_URL, param, new ResultJSONObjectObserver() {
                                @Override
                                public void onSuccess(JSONObject result) {

                                }

                                @Override
                                public void onFail(String msg) {

                                }
                            });
                        }else {
                            Toast.makeText(context,"返回的手机号不正确！",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context,"未录入手机号！",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return coachViperBeanList == null ? 0 : coachViperBeanList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_header;
        ImageView iv_gender;
        TextView tv_name;
        TextView tv_call;

        LinearLayout lin_content;

        public ViewHolder(View view) {
            super(view);
            lin_content = view.findViewById(R.id.lin_content);
            iv_header = view.findViewById(R.id.iv_header);
            iv_gender = view.findViewById(R.id.iv_gender);
            tv_call = view.findViewById(R.id.tv_call);
            tv_name = view.findViewById(R.id.tv_name);
        }
    }
}
