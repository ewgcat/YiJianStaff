package com.yijian.workspace.dynamic_assessment;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yijian.workspace.R;

import com.yijan.commonlib.mvp.base.mvc.MvcBaseActivity;
import com.yijan.commonlib.widget.NavigationBar;
import com.yijian.workspace.face.BitmapFaceUtils;
import com.yijian.workspace.utils.ActivityUtils;
import com.yijian.workspace.utils.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DynamicDragPointActivity extends MvcBaseActivity {

    @BindView(R.id.iv_pendding)
    ImageView iv_pendding;
    @BindView(R.id.rel_container)
    RelativeLayout rel_container;
    @BindView(R.id.pointView)
    DynamicPointView pointView;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_dynamic_drag_point;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        File file = new File(getCacheDir() + "/img_dynamic.jpg");
        GlideApp.with(this).load(file).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_pendding);
        initTitle();
    }

    private void initTitle() {
        NavigationBar navigationBar = findViewById(R.id.navigation_bar);
        navigationBar.setTitle("盆骨图打点");
        navigationBar.hideLeftSecondIv();
        navigationBar.setBackClickListener(this);
    }

    @OnClick({R.id.iv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finish:
                pointView.clearSelCircle();
                rel_container.setDrawingCacheEnabled(true);
                Bitmap bitmap = rel_container.getDrawingCache();
                Bitmap rotateBitmap = BitmapFaceUtils.rotateBitmap(bitmap, -90);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotateBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] datas = baos.toByteArray();
                StreamUtils.createFileWithByte(datas, getCacheDir() + "/img_dynamic.jpg");
                String result = pointView.getMinArea();
                Bundle bundle = new Bundle();
                bundle.putString("area", result);
                ActivityUtils.startActivity(DynamicDragPointActivity.this, DynamicAssessmentActivity.class, bundle);
                finish();
                break;
            default:
        }
    }

}