package com.yijian.staff.mvp.vipermanage.student.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yijian.staff.R;

/**
 * Created by The_P on 2018/5/15.
 */

public class AdapterCoachViper_2 extends AdapterAbsCoachViper {

    public static final int TYPE0 = 0;
    public static final int TYPE1 = 1;
    public static final int TYPE3 = 3;

    private Context context;

    public AdapterCoachViper_2(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolderCoachVipper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        ViewHolderCoachVipper holder;
        switch (viewType) {
            default:
            case TYPE0:
                view = LayoutInflater.from(context).inflate(R.layout.item_coachvip_type0, parent, false);
                holder = new ViewHolderCoachVipType_0(view, false);
                holder.setHuijiVipInterface(this);
                break;
            case TYPE1:
                view = LayoutInflater.from(context).inflate(R.layout.item_coachvip_type1, parent, false);
                holder = new ViewHolderCoachVipType_1(view);
                break;
            case TYPE3:
                view = LayoutInflater.from(context).inflate(R.layout.item_coachvip_type3, parent, false);
                holder = new ViewHolderCoachVipType_3(view);
                holder.setHuijiVipInterface(this);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCoachVipper holder, int position) {
        if (mViperDetailBean != null) holder.bindView(mViperDetailBean);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            default:
            case 0:
                return TYPE0;
            case 1:
                return TYPE1;
            case 2:
                return TYPE3;
        }

    }

    @Override
    public void visit() {
        if (adapterInterface != null) adapterInterface.clickVisit();
    }

    @Override
    public void edit() {
        if (adapterInterface != null) adapterInterface.clickEdit();
    }


}
