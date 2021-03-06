package com.yijian.clubmodule.ui.huifang.vip.task.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Toast

import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.scwang.smartrefresh.layout.header.BezierRadarHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.yijian.clubmodule.R
import com.yijian.clubmodule.bean.HuiFangInfo
import com.yijian.commonlib.base.mvc.MvcBaseFragment
import com.yijian.clubmodule.ui.huifang.vip.task.adapter.HuiFangTaskAdapter
import com.yijian.clubmodule.net.httpmanager.HttpManager
import com.yijian.clubmodule.net.httpmanager.url.HuiFangUrls
import com.yijian.clubmodule.net.requestbody.huifang.HuifangTaskRequestBody
import com.yijian.commonlib.net.response.ResultJSONArrayObserver
import kotlinx.android.synthetic.main.common_hui_fang_task.*

import org.json.JSONArray

import java.util.ArrayList

/**
 * author：李帅华
 * email：850716183@qq.com
 * time: 2018/3/5 19:39:24
 */
@SuppressLint("ValidFragment")
class BaseHuiFangTaskFragment : MvcBaseFragment {
    override fun getLayoutId(): Int {
        return R.layout.common_hui_fang_task
    }

    private val huiFangInfoList = ArrayList<HuiFangInfo>()
    lateinit var huiFangTaskAdapter:HuiFangTaskAdapter
    private var menu: Int =0
    private var offset = 1//页码
    private var pageSize = 10//每页数量


    constructor( menu: Int) {
        this.menu = menu
    }

    constructor() : super() {}

    override fun initView() {
        initComponent()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    fun initComponent() {
        //设置 Header 为 BezierRadar 样式
        val header = BezierRadarHeader(getContext()).setEnableHorizontalDrag(true)
        header.setPrimaryColor(Color.parseColor("#1997f8"))
        refreshLayout.setRefreshHeader(header)
        //设置 Footer 为 球脉冲
        val footer = BallPulseFooter(getContext()!!).setSpinnerStyle(SpinnerStyle.Scale)
        footer.setAnimatingColor(Color.parseColor("#1997f8"))
        refreshLayout.setRefreshFooter(footer)
        refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                refresh()
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                loadMore()
            }
        })

        rlv.layoutManager = LinearLayoutManager(context)
        huiFangTaskAdapter =HuiFangTaskAdapter(context!!, huiFangInfoList, menu)
        rlv.adapter = huiFangTaskAdapter
    }

    fun refresh() {
        offset = 0
        pageSize = 10
        huiFangInfoList.clear()
        showLoading()
        val huifangTaskRequestBody = HuifangTaskRequestBody()
        huifangTaskRequestBody.isChief = true
        huifangTaskRequestBody.menu = menu
        huifangTaskRequestBody.offset = offset
        huifangTaskRequestBody.size = pageSize
        HttpManager.postHuiFangTask(HuiFangUrls.HUI_FANG_TASK_URL, huifangTaskRequestBody, object : ResultJSONArrayObserver(lifecycle) {
            override fun onSuccess(result: JSONArray) {
                if ( result.length() == 0) {
                    empty_view.visibility = View.VISIBLE
                }else{
                    empty_view.visibility = View.GONE
                }
                refreshLayout.finishRefresh(2000, true)
                val list = com.alibaba.fastjson.JSONArray.parseArray(result.toString(), HuiFangInfo::class.java)
                huiFangInfoList.addAll(list)
                huiFangTaskAdapter.update(huiFangInfoList)
                hideLoading()
            }

            override fun onFail(msg: String) {
                refreshLayout.finishRefresh(2000, false)
                hideLoading()
            }
        })
    }

    fun loadMore() {
        showLoading()
        offset = huiFangInfoList.size
        val huifangTaskRequestBody = HuifangTaskRequestBody()
        huifangTaskRequestBody.isChief = true
        huifangTaskRequestBody.menu = menu
        huifangTaskRequestBody.offset = offset
        huifangTaskRequestBody.size = pageSize
        HttpManager.postHuiFangTask(HuiFangUrls.HUI_FANG_TASK_URL, huifangTaskRequestBody, object : ResultJSONArrayObserver(lifecycle) {
            override fun onSuccess(result: JSONArray) {
                refreshLayout.finishLoadMore(2000, true, false)//传入false表示刷新失败
                val list = com.alibaba.fastjson.JSONArray.parseArray(result.toString(), HuiFangInfo::class.java)
                huiFangInfoList.addAll(list)
                huiFangTaskAdapter.update(huiFangInfoList)
                hideLoading()
            }

            override fun onFail(msg: String) {
                refreshLayout.finishLoadMore(2000, false, false)//传入false表示刷新失败
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
                hideLoading()
            }
        })
    }


}
