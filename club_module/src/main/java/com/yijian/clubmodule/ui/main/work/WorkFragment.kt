package com.yijian.clubmodule.ui.main.work

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager

import com.yijian.clubmodule.R
import com.yijian.clubmodule.bean.IndexDataInfo
import com.yijian.clubmodule.jpush.ClearRedPointUtil
import com.yijian.clubmodule.jpush.bean.PushInfoBean
import com.yijian.commonlib.base.mvc.MvcBaseFragment

import com.yijian.clubmodule.net.httpmanager.HttpManager
import com.yijian.commonlib.net.response.ResponseObserver
import com.yijian.commonlib.prefs.SharePreferenceUtil
import com.yijian.clubmodule.db.ClubDBManager
import com.yijian.clubmodule.db.bean.OthermodelVo
import com.yijian.clubmodule.ui.face.FaceDetectorActivity
import com.yijian.clubmodule.ui.reception.ReceptionActivity
import com.yijian.clubmodule.ui.vipermanage.search.HuiJiSearchActivity
import com.yijian.commonlib.util.Logger

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

import kotlinx.android.synthetic.main.fragment_work.*


@SuppressLint("ValidFragment")
class WorkFragment : MvcBaseFragment(), View.OnClickListener {
    override fun onClick(view: View) {
        when (view.id) {
            R.id.et_search ->
                // 此处为得到焦点时的处理内容
                startActivity(Intent(context, HuiJiSearchActivity::class.java))
            R.id.ll_jiedai -> {
                ClearRedPointUtil.clearJieDaiNotice(this.getLifecycle())
                showJieDaiView(1)
                SharePreferenceUtil.setHasNewJiedaiPush(false)
                startActivity(Intent(context, ReceptionActivity::class.java))
            }
            R.id.iv_face -> startActivity(Intent(context, FaceDetectorActivity::class.java))
        }    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_work
    }



    lateinit var indexMenuAdapter: IndexMenuAdapter
    private val menuList = ArrayList<IndexDataInfo.MenuModelListBean.SubMeneModelListBean>()
    private var faceRecognition: Boolean = false
    private var reception: Boolean = false
    var hasNewJiedaiPush: Boolean = false
    var hasNewYueKePush: Boolean = false


    override
    fun initView() {


        indexMenuAdapter = IndexMenuAdapter(this.getLifecycle(), context, menuList)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = indexMenuAdapter

        val othermodelVo = ClubDBManager.getInstance().queryOthermodelVo()
        if (othermodelVo==null){
            initData();
        }else{
            faceRecognition = othermodelVo.faceRecognition
            if (faceRecognition) {
                iv_face.visibility = View.VISIBLE
            } else {
                iv_face.visibility = View.GONE
            }
            reception = othermodelVo.reception

        }
        hasNewJiedaiPush = SharePreferenceUtil.hasNewJiedaiPush()
        hasNewYueKePush = SharePreferenceUtil.hasNewYueKePush()
        initData()
        swipe_refresh_layout.setOnRefreshListener { initData() }
        et_search.setOnClickListener(this)
        ll_jiedai.setOnClickListener(this)
        iv_face.setOnClickListener(this)

    }

    fun showJieDaiView(i: Int) {
        if (isAdded) {
            if (i == 0) {//没有接待权限
                ll_jie_dai_container.background = resources.getDrawable(R.mipmap.home_no_jd, null)
                ll_jiedai_container.visibility = View.GONE
                ll_jiedai.visibility = View.GONE
            } else if (i == 1) {//有接待权限，没有新消息
                ll_jie_dai_container.background = resources.getDrawable(R.mipmap.home_no_new_jd, null)
                ll_jiedai_container.visibility = View.VISIBLE
                ll_jiedai.visibility = View.VISIBLE
            } else if (i == 2) {//有接待权限，有新消息
                ll_jie_dai_container.background = resources.getDrawable(R.mipmap.home_new_jd, null)
                ll_jiedai_container.visibility = View.VISIBLE
                ll_jiedai.visibility = View.VISIBLE
            }
        }
    }


    fun observe(pushInfoBean: PushInfoBean) {

        hasNewJiedaiPush = pushInfoBean.hasNewJiedaiPush
        hasNewYueKePush = pushInfoBean.hasNewYueKePush

        setRedPoint()

    }

    private fun setRedPoint() {
        if (reception) {
            if (hasNewJiedaiPush) {
                showJieDaiView(2)
            } else {
                showJieDaiView(1)
            }
        } else {
            showJieDaiView(0)
        }
        if (hasNewYueKePush) {
            Logger.i(TAG, "有排课信息推送")

            for (i in menuList.indices) {
                if (menuList[i].menuKey == "app_course_appoint_info") {
                    menuList[i].count = 1
                }
            }
        }
        indexMenuAdapter.notifyDataSetChanged()

    }


    private fun initData() {
        showLoading()

        HttpManager.getNewIndexMenuList(object : ResponseObserver<IndexDataInfo>(getLifecycle()) {
            override fun onSuccess(result: IndexDataInfo) {
                hideLoading()
                val othermodelVoBean = result.othermodelVo
                val jsonObject = JSONObject()
                try {
                    jsonObject.put("faceRecognition", othermodelVoBean.isFaceRecognition)
                    jsonObject.put("reception", othermodelVoBean.isReception)
                    jsonObject.put("coachSchedule", othermodelVoBean.isCoachSchedule)
                    jsonObject.put("sellerSchedule", othermodelVoBean.isSellerSchedule)
                    val othermodelVo = OthermodelVo(jsonObject)
                    ClubDBManager.getInstance().insertOrReplaceOthermodelVo(othermodelVo)
                    faceRecognition = othermodelVo.faceRecognition
                    if (faceRecognition) {
                        iv_face.visibility = View.VISIBLE
                    } else {
                        iv_face.visibility = View.GONE
                    }
                    reception = othermodelVo.reception
                    swipe_refresh_layout.isRefreshing = false
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                //TODO 首页 接待 人脸识别权限

                menuList.clear()
                val menuModelList = result.menuModelList
                SharePreferenceUtil.setAppSellerBuiness(false)
                SharePreferenceUtil.setAppCourseBuiness(false)
                for (i in menuModelList.indices) {
                    val menuModelListBean = menuModelList[i]
                    if (menuModelListBean != null && menuModelListBean.menuKey == "app_workbench") {
                        val subMeneModelList = menuModelListBean.subMeneModelList
                        for (j in subMeneModelList.indices) {
                            val subMeneModelListBean = subMeneModelList[j]
                            menuList.add(subMeneModelListBean)
                        }
                    } else if (menuModelListBean != null && menuModelListBean.menuKey == "app_business_message") {
                        val subMeneModelList = menuModelListBean.subMeneModelList
                        for (j in subMeneModelList.indices) {
                            val subMeneModelListBean = subMeneModelList[j]
                            if (subMeneModelListBean.menuKey == "app_seller_business") {
                                SharePreferenceUtil.setAppSellerBuiness(true)
                            } else if (subMeneModelListBean.menuKey == "app_course_business") {
                                SharePreferenceUtil.setAppCourseBuiness(true)
                            }
                        }
                    }
                }
                setRedPoint()


            }

            override fun onFail(msg: String) {
                swipe_refresh_layout.isRefreshing = false
                hideLoading()
                showToast(msg)
            }
        })


    }




    companion object {
        private val TAG = WorkFragment::class.java.simpleName

        lateinit var mWorkFragment: WorkFragment
    }

}


