package com.yijian.commonlib.widget;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yijian.commonlib.R;

/**
 * author：李帅华
 * email：850716183@qq.com
 * time: 2018/5/17 10:15:52
 */
public class ErrorView extends LinearLayout {

    public ErrorView(Context context) {
        super(context);
    }

    private Button emptyBt;
    private ImageView emptyImg;
    private TextView emptyTv;

    public ErrorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.error_layout, this);
        setGravity(Gravity.CENTER);
        emptyBt = (Button) findViewById(R.id.bt_retry);
        emptyImg = (ImageView) findViewById(R.id.empty_img);
        emptyTv = (TextView) findViewById(R.id.empty_msg_tv);
    }

    public void init(@DrawableRes int imgResId, String msgText, String buttonText, OnClickListener buttonClickListener) {
        setImg(imgResId);
        setMsg(msgText);
        setButton(buttonText, buttonClickListener);
    }

    public void setButton(String text, OnClickListener listener) {
        if (emptyBt != null) {
            emptyBt.setVisibility(VISIBLE);
            emptyBt.setText(text);
            emptyBt.setOnClickListener(listener);
        }
    }

    public void setMsg(String text) {
        if (emptyTv != null) {
            emptyTv.setText(text);
        }
    }

    public void setImg(@DrawableRes int resId) {
        if (emptyImg != null) {
            emptyImg.setImageResource(resId);
        }
    }


    public Button getEmptyBt() {
        return emptyBt;
    }

    public void setEmptyBt(Button emptyBt) {
        this.emptyBt = emptyBt;
    }

    public ImageView getEmptyImg() {
        return emptyImg;
    }

    public void setEmptyImg(ImageView emptyImg) {
        this.emptyImg = emptyImg;
    }

    public TextView getEmptyTv() {
        return emptyTv;
    }

    public void setEmptyTv(TextView emptyTv) {
        this.emptyTv = emptyTv;
    }
}
