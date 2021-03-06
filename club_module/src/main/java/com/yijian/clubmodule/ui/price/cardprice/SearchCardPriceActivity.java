package com.yijian.clubmodule.ui.price.cardprice;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.yijian.clubmodule.R;
import com.yijian.commonlib.base.mvc.MvcBaseActivity;
import com.yijian.commonlib.util.CommonUtil;
import com.yijian.commonlib.util.system.KeyBroadUtil;

/**
 * Created by The_P on 2018/5/18.
 */

public class SearchCardPriceActivity extends MvcBaseActivity {
    private static final String TAG = "SearchCardPriceActivity";
    EditText etSearch;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_huijibaojia_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        initView();
    }

    private void initView() {
        etSearch= findViewById(R. id.et_search);
        CommonUtil.showSoftInputFromWindow(this,etSearch);
        TextView tvComplete = findViewById(R.id.tv_complete);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:

                        String name = etSearch.getText().toString().trim();
                        Intent intent = new Intent();
                        intent.putExtra("search", name);
                        KeyBroadUtil.hideKeyBroad(SearchCardPriceActivity.this, etSearch);
                        setResult(1, intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = etSearch.getText();
                String s = text.toString().trim();
                Log.e(TAG, "onClick: " + s.length());
                Intent intent = new Intent();
                intent.putExtra("search", s);
                setResult(1, intent);
                KeyBroadUtil.hideKeyBroad(SearchCardPriceActivity.this, etSearch);
                finish();
            }
        });


    }

}
