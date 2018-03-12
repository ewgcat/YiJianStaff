package com.yijian.staff.mvp.reception.step1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yijian.staff.R;
import com.yijian.staff.mvp.reception.step1.bean.QuestionEntry;
import com.yijian.staff.mvp.reception.step1.bean.QuestionOption;
import com.yijian.staff.mvp.reception.step1.recyclerView.ChildViewHolder;
import com.yijian.staff.mvp.reception.step1.recyclerView.ExpandableRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by The_P on 2018/3/12.
 */

public class Step1QuestAdapter extends ExpandableRecyclerAdapter<QuestionEntry, QuestionOption, QuestionViewHolder, ChildViewHolder> implements QuestionSingleCheckViewHolder.SingleCheckListener, QuestionMultiCheckViewHolder.MultiCheckListener, QuestionWriteViewHolder.WriteListener {
    private static final String TAG = "Step1QuestAdapter";
    private static final int SINGLECHECK = 3;
    private static final int MULTICHECK =4;
    private static final int WRITE = 5;

    private LayoutInflater mInflater;
    private List<QuestionEntry> QuestionList;
    private Context mContext;

    private Map<Integer,Integer> singleCheck=new HashMap<>();//单选的结果集合

    private Map<Integer,HashSet<Integer>> multiCheck=new HashMap<>();//多选的结果集合

    private Map<Integer,String> write=new HashMap<>();//填空的结果集合

    public Step1QuestAdapter(@NonNull List<QuestionEntry> parentList, Context context) {
        super(parentList);
        QuestionList = parentList;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View titleview = mInflater.inflate(R.layout.item_quest_title, parentViewGroup, false);

        return new QuestionViewHolder(titleview);
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        ChildViewHolder viewHolder;
        switch (viewType) {

            case SINGLECHECK:
                View singleView = mInflater.inflate(R.layout.item_quest_option_single, childViewGroup, false);
                QuestionSingleCheckViewHolder viewHolder0 = new QuestionSingleCheckViewHolder(singleView);
                viewHolder0.setSingleCheckListener(this);
                viewHolder = viewHolder0;
                break;
            case MULTICHECK:
                View multiView = mInflater.inflate(R.layout.item_quest_option_multi, childViewGroup, false);
                QuestionMultiCheckViewHolder viewHolder1 = new QuestionMultiCheckViewHolder(multiView);
                viewHolder1.setSingleCheckListener(this);
                viewHolder = viewHolder1;
                break;
            default:
            case WRITE:
                View writeView = mInflater.inflate(R.layout.item_quest_option_write, childViewGroup, false);
                QuestionWriteViewHolder viewHolder2 = new QuestionWriteViewHolder(writeView);
                viewHolder2.setWriteListener(this);
                viewHolder=viewHolder2;
                break;

        }

        return viewHolder;
    }

    @Override
    public void onBindParentViewHolder(@NonNull QuestionViewHolder parentViewHolder, int parentPosition, @NonNull QuestionEntry parent) {
        parentViewHolder.bind(parent,parentPosition);
    }

    @Override
    public void onBindChildViewHolder(@NonNull ChildViewHolder childViewHolder, int parentPosition, int childPosition,
                                      @NonNull QuestionOption child, int flatPosition) {
        if (childViewHolder instanceof QuestionSingleCheckViewHolder){
//            Log.e(TAG, "onBindChildViewHolder:QuestionSingleCheckViewHolder  parentPosition "+ parentPosition);
            ((QuestionSingleCheckViewHolder) childViewHolder).bind(child,parentPosition,childPosition);
        }else if (childViewHolder instanceof QuestionMultiCheckViewHolder){
//            Log.e(TAG, "onBindChildViewHolder:QuestionMultiCheckViewHolder  parentPosition "+ parentPosition);
            ((QuestionMultiCheckViewHolder) childViewHolder).bind(child,parentPosition,childPosition);
        }else if (childViewHolder instanceof QuestionWriteViewHolder){
            ((QuestionWriteViewHolder) childViewHolder).bind(child,parentPosition,childPosition);
        }
    }

    @Override
    public int getChildViewType(int parentPosition, int childPosition) {
        QuestionOption questionOption = QuestionList.get(parentPosition).getQuestionOption(childPosition);
        int type=WRITE;
        switch (questionOption.getType()){
            case QuestionOption.TYPE_SINGLECHECK:
                type=SINGLECHECK;
                break;
              case QuestionOption.TYPE_MULTICHECK:
                  type=MULTICHECK;
                break;
              case QuestionOption.TYPE_WRITE:
                  type=WRITE;
                break;
        }
        return type;
    }



    @Override
    public void onMultiClick(QuestionOption child, int parentPosition,int childPosition) {
//        Toast.makeText(mContext,"QuestionOption=="+child.getmName()+" ,parentPosition"+parentPosition,Toast.LENGTH_SHORT).show();

        child.setSelected(child.isSelected()?false:true);
        notifyDataSetChanged();


        HashSet<Integer> integers = multiCheck.get(QuestionList.get(parentPosition).getId());
        if (child.isSelected()){
            if (integers==null)integers =new HashSet<>();
            integers.add(childPosition);

        }else {
            if (integers==null)return;
            integers.remove(childPosition);
        }
        multiCheck.put(QuestionList.get(parentPosition).getId(),integers);
    }

    @Override
    public void onSingleClick(QuestionOption child, int parentPosition,int childPosition) {
//        Toast.makeText(mContext,"QuestionOption=="+child.getmName()+" ,parentPosition"+parentPosition,Toast.LENGTH_SHORT).show();
        QuestionEntry questionEntry = QuestionList.get(parentPosition);
        for (QuestionOption ingredient1:questionEntry.getChildList() ) {
            ingredient1.setSelected(false);
        }
        child.setSelected(true);
        notifyDataSetChanged();

        singleCheck.put(QuestionList.get(parentPosition).getId(),childPosition);

    }

    @Override
    public void onWrited(int parentPosition, Editable s) {
        write.put(QuestionList.get(parentPosition).getId(),s.toString());
    }


    public Map<Integer, Integer> getSingleCheck() {
        return singleCheck;
    }

    public Map<Integer, HashSet<Integer>> getMultiCheck() {
        return multiCheck;
    }

    public Map<Integer, String> getWrite() {
        return write;
    }


}
