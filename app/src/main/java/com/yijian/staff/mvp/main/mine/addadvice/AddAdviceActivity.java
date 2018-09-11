package com.yijian.staff.mvp.main.mine.addadvice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yijian.staff.R;
import com.yijian.staff.db.DBManager;
import com.yijian.staff.db.bean.User;
import com.yijian.staff.mvp.base.mvc.MvcBaseActivity;
import com.yijian.staff.net.httpmanager.HttpManager;
import com.yijian.staff.net.requestbody.advice.AddAdviceBody;
import com.yijian.staff.net.requestbody.advice.Advice;
import com.yijian.staff.net.response.ResultJSONObjectObserver;
import com.yijian.staff.util.CommonUtil;
import com.yijian.staff.widget.NavigationBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAdviceActivity extends MvcBaseActivity {

    @BindView(R.id.et_advice)
    EditText etAdvice;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_advice;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        NavigationBar NavigationBar = (NavigationBar) findViewById(R.id.advice_view_navigation_bar2);
        NavigationBar.setTitle("系统意见");
        NavigationBar.hideLeftSecondIv();
        NavigationBar.getFirstLeftIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard(etAdvice);
                finish();
            }
        });
        NavigationBar.setmRightTvText("发送");
        TextView textView = NavigationBar.getmRightTv();
        textView.setTextColor(getResources().getColor(R.color.blue));
        NavigationBar.setmRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });
        CommonUtil.showSoftInputFromWindow(this,etAdvice);

    }


    private void postData() {
        String advice = etAdvice.getText().toString();
        if (TextUtils.isEmpty(advice)) {
            showToast("请先填写反馈意见！");
        } else {
            String userId = DBManager.getInstance().queryUser().getUserId();
            Advice advicebody = new Advice(advice, userId);

            AddAdviceBody addAdviceBody = new AddAdviceBody(advicebody);
            HttpManager.postAddAdvice(HttpManager.ADD_FEEDBACK_URL, addAdviceBody, new ResultJSONObjectObserver(getLifecycle()) {
                @Override
                public void onSuccess(JSONObject result) {
                    hideKeyBoard(etAdvice);
                    showToast("提交建议成功");
                    finish();
                }

                @Override
                public void onFail(String msg) {
                    showToast(msg);
                }
            });

        }
    }
}
