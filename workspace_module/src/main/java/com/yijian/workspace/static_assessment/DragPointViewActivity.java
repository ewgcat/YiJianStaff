package com.yijian.workspace.static_assessment;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yijian.commonlib.base.mvc.MvcBaseActivity;
import com.yijian.commonlib.widget.NavigationBar;
import com.yijian.workspace.R;
import com.yijian.workspace.bean.StaticRequestBody;
import com.yijian.workspace.utils.ActivityUtils;
import com.yijian.workspace.utils.StreamUtils;
import com.yijian.workspace.widget.ControlView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class DragPointViewActivity extends MvcBaseActivity implements IphotoCrop, View.OnClickListener {

    ImageView iv_pendding;
    ImageView iv_sample;
    ImageView iv_crop;
    RelativeLayout rel_container;
    DragPointView pointView_positive;
    SideDragPointView pointView_side;
    ControlView controlview;
    private int type;
    private int maxWidth, maxHeight; //最大宽高
    private int cWidth, cHeight; //截取的宽高

    @Override
    protected int getLayoutID() {
        return R.layout.activity_drag_point_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {



        iv_pendding = findViewById(R.id.iv_pendding);
        iv_sample = findViewById(R.id.iv_sample);
        iv_crop = findViewById(R.id.iv_crop);
        rel_container = findViewById(R.id.rel_container);
        pointView_positive = findViewById(R.id.pointView_positive);
        pointView_side = findViewById(R.id.pointView_side);
        controlview = findViewById(R.id.controlview);


        findViewById(R.id.right_tv).setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");
        pointView_positive.setVisibility(type == 0 ? View.VISIBLE : View.GONE);
        pointView_side.setVisibility(type == 0 ? View.GONE : View.VISIBLE);
        if (type == 0) {
            controlview.setListener(pointView_positive);
        } else {
            controlview.setListener(pointView_side);
        }
        File file = new File(getCacheDir() + (type == 0 ? "/img_positive.jpg" : "/img_side.jpg"));

        RequestOptions options = centerCropTransform().priority(Priority.HIGH).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(this).load(file).apply(options).into(iv_pendding);

        initTitle();
        pointView_positive.setListener(this);
        pointView_side.setListener(this);
        ViewTreeObserver viewTreeObserver = rel_container.getViewTreeObserver();
        viewTreeObserver
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        rel_container.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                        maxWidth = rel_container.getMeasuredWidth();
                        maxHeight = rel_container.getMeasuredHeight();
                        cWidth = iv_crop.getMeasuredWidth();
                        cHeight = iv_crop.getMeasuredHeight();
                    }
                });
    }

    private void initTitle() {
        NavigationBar navigationBar = findViewById(R.id.navigation_bar);
        navigationBar.setTitle(type == 0 ? "正面照" : "侧身照");
        navigationBar.hideLeftSecondIv();
        navigationBar.setBackClickListener(this); TextView rightTv = navigationBar.getmRightTv();
        rightTv.setText("完成");
        rightTv.setTextColor(getResources().getColor(R.color.blue));
        navigationBar.setBackClickListener(this);
    }




    @Override
    public void onTouchCrop(float cX, float cY) {
        rel_container.setDrawingCacheEnabled(true);
        Bitmap bitmap = rel_container.getDrawingCache();
//        iv_crop.setImageBitmap(bitmap);
        // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
//        rel_container.setDrawingCacheEnabled(false);

        int diffX = (int) (cX - cWidth / 2);
        int sumX = (int) (cX + cWidth / 2);
        int diffY = (int) (cY - cHeight / 2);
        int sumY = (int) (cY + cHeight / 2);
        int startX = 0;
        int startY = 0;

        if (diffX >= 0 && sumX <= maxWidth && diffY >= 0 && sumY <= maxHeight) { //正常区域
            startX = (int) (cX - cWidth / 2);
            startY = (int) (cY - cHeight / 2);
            Log.e("Test", "正常区域");
        } else if (diffX <= 0 && diffY <= 0) { //超出了左上角的限制
            startX = 0;
            startY = 0;
            Log.e("Test", "超出了左上角的限制");
        } else if (sumX >= maxWidth && diffY <= 0) { //超出右上角的限制
            startX = maxWidth - cWidth;
            startY = 0;
            Log.e("Test", "超出右上角的限制");
        } else if (diffX <= 0 && sumY >= maxHeight) { // 超出了左下角的限制
            startX = 0;
            startY = maxHeight - cHeight;
            Log.e("Test", "超出了左下角的限制");
        } else if (sumX >= maxWidth && sumY >= maxHeight) { //超出了右下角的限制
            startX = maxWidth - cWidth;
            startY = maxHeight - cHeight;
            Log.e("Test", "超出了右下角的限制");
        } else if (diffX > 0 && sumX < maxWidth && diffY < 0) { //超出了上边限制
            startX = (int) (cX - cWidth / 2);
            startY = 0;
            Log.e("Test", "超出了上边限制");
        } else if (diffX > 0 && sumX < maxWidth && sumY > maxHeight) { //超出了下边限制
            startX = (int) (cX - cWidth / 2);
            startY = (int) (maxHeight - cHeight);
            Log.e("Test", "超出了下边限制");
        } else if (diffY > 0 && sumY < maxHeight && diffX < 0) { //超出了左边限制
            startX = 0;
            startY = (int) (cY - cHeight / 2);
            Log.e("Test", "超出了左边限制");
        } else if (diffY > 0 && sumY < maxHeight && sumX > maxWidth) { //超出了右边限制
            startX = (int) (maxWidth - cWidth);
            startY = (int) (cY - cHeight / 2);
            Log.e("Test", "超出了右边限制");
        } else {
            Log.e("Test", "不触发事件  cX===" + cX + "cY===" + cY);
        }
        Log.e("Test", "sumX===" + sumX + " diffY==" + diffY + "  startX ===" + startX + " startY===" +
                startY + "  cWidth===" + cWidth + "cHeight===" + cHeight + "  maxWidth===" + maxWidth + "  maxHeight===" + maxHeight);
        Matrix matrix = new Matrix();
        matrix.preScale(2, 2);
        iv_crop.setImageBitmap(Bitmap.createBitmap(bitmap, startX, startY, cWidth - 1, cHeight - 1, matrix, false));

    }

    @Override
    public void onClickCircle(int value) {
        if (value == PointEnum.POINT_EAR_LEFT.getValue()) { //左耳朵
            iv_sample.setImageResource(R.mipmap.positive_left_ear);
        } else if (value == PointEnum.POINT_EAR_RIGHT.getValue()) { //右耳
            iv_sample.setImageResource(R.mipmap.positive_right_ear);
        } else if (value == PointEnum.POINT_JIAN_LEFT.getValue()) { //左肩
            iv_sample.setImageResource(R.mipmap.positive_left_jian);
        } else if (value == PointEnum.POINT_JIAN_RIGHT.getValue()) { //右肩
            iv_sample.setImageResource(R.mipmap.positive_right_gonggu);
        }
        if (value == PointEnum.POINT_NAVEL.getValue()) { //肚脐眼
            iv_sample.setImageResource(R.mipmap.positive_navel);
        } else if (value == PointEnum.POINT_HIPBONE_LEFT.getValue()) { //左胯骨
            iv_sample.setImageResource(R.mipmap.positive_left_kuagu);
        } else if (value == PointEnum.POINT_HIPBONE_RIGHT.getValue()) { //右胯骨
            iv_sample.setImageResource(R.mipmap.positive_right_kuagu);
        } else if (value == PointEnum.POINT_KNEE_LEFT.getValue()) { //左膝盖
            iv_sample.setImageResource(R.mipmap.positive_left_knee);
        } else if (value == PointEnum.POINT_KNEE_RIGHT.getValue()) { //右膝盖
            iv_sample.setImageResource(R.mipmap.positive_right_knee);
        } else if (value == PointEnum.POINT_ANKLE_LEFT.getValue() || value == PointEnum.POINT_TIPTOE_LEFT.getValue()) { //左脚踝 脚尖
            iv_sample.setImageResource(R.mipmap.positive_left_jiaohuai);
        } else if (value == PointEnum.POINT_ANKLE_RIGHT.getValue() || value == PointEnum.POINT_TIPTOE_RIGHT.getValue()) { //右脚踝 脚尖
            iv_sample.setImageResource(R.mipmap.positive_right_jiaohuai);
        } else if (value == PointEnum.POINT_EAR.getValue()) { //耳朵
            iv_sample.setImageResource(R.mipmap.side_ear);
        } else if (value == PointEnum.POINT_GONGGU.getValue()) { //肱骨头
            iv_sample.setImageResource(R.mipmap.side_gonggu);
        } else if (value == PointEnum.POINT_JIAN.getValue()) { //肩胛骨
            iv_sample.setImageResource(R.mipmap.side_jian);
        } else if (value == PointEnum.POINT_HIPBONE.getValue()) { //胯骨
            iv_sample.setImageResource(R.mipmap.side_kuagu);
        } else if (value == PointEnum.POINT_KNEE.getValue()) { //膝盖
            iv_sample.setImageResource(R.mipmap.side_knee);
        }
    }

    @Override
    public void onClick(View v) {
        if (type == 0) {
            pointView_positive.clearSelCircle();
        } else {
            pointView_side.clearSelCircle();
        }
        rel_container.setDrawingCacheEnabled(true);
        Bitmap bitmap = rel_container.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] datas = baos.toByteArray();
        StreamUtils.createFileWithByte(datas, getCacheDir() + (type == 0 ? "/img_positive.jpg" : "/img_side.jpg"));
        StaticRequestBody staticRequestBody = StaticAssessmentActivity.getInstance().getStaticRequestBody();
        staticRequestBody.setGender(Integer.parseInt(ActivityUtils.workSpaceVipBean.getGender()));
        staticRequestBody.setBirthday(ActivityUtils.workSpaceVipBean.getBirthday());
        staticRequestBody.setMemberId(ActivityUtils.workSpaceVipBean.getMemberId());
        if (type == 0) { //正面
            List<Double> positiveList = pointView_positive.returnOrientation();
            staticRequestBody.setTbqxqk(positiveList.get(0));
            staticRequestBody.setJbqxqk(positiveList.get(1));
            staticRequestBody.setQjqkxol(positiveList.get(4));
            staticRequestBody.setNwbqkl(positiveList.get(5));
            staticRequestBody.setQjqkxor(positiveList.get(2));
            staticRequestBody.setNwbqkr(positiveList.get(3));
        } else { //侧面
            List<Double> sideList = pointView_side.returnOrientation();
            staticRequestBody.setXeqsqk(sideList.get(0));
            staticRequestBody.setTbqk(sideList.get(1));
            staticRequestBody.setSbqxqk(sideList.get(2));
        }
        ActivityUtils.startActivity(DragPointViewActivity.this, StaticAssessmentActivity.class);
        finish();    }
}
