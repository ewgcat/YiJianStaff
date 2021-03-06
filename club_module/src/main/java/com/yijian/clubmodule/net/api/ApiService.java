package com.yijian.clubmodule.net.api;


import com.yijian.clubmodule.bean.PrivatePrepareLessonBody;
import com.yijian.clubmodule.net.requestbody.AbortFuFangBody;
import com.yijian.clubmodule.net.requestbody.AccessStatisticsRequestBody;
import com.yijian.clubmodule.net.requestbody.AddFuFangResultBody;
import com.yijian.clubmodule.net.requestbody.CardRequestBody;
import com.yijian.clubmodule.net.requestbody.EditHuiJiVipBody;
import com.yijian.clubmodule.net.requestbody.HuiFangTypeRequestBody;
import com.yijian.clubmodule.net.requestbody.HuiJiInviteListRequestBody;
import com.yijian.clubmodule.net.requestbody.HuifangRecordRequestBody;
import com.yijian.clubmodule.net.requestbody.PrivateCoursePingJiaRequestBody;
import com.yijian.clubmodule.net.requestbody.addpotential.AddPotentialRequestBody;
import com.yijian.clubmodule.net.requestbody.advice.AddAdviceBody;
import com.yijian.clubmodule.net.requestbody.course.SaveCourseRequestBody;
import com.yijian.clubmodule.net.requestbody.huifang.AddHuiFangResultBody;
import com.yijian.clubmodule.net.requestbody.huifang.HuifangTaskRequestBody;
import com.yijian.clubmodule.net.requestbody.invite.SaveInviteBody;
import com.yijian.clubmodule.net.requestbody.message.BusinessMessageRequestBody;
import com.yijian.clubmodule.net.requestbody.privatecourse.CoachPrivateCourseRequestBody;
import com.yijian.clubmodule.net.requestbody.questionnaire.QuestionnaireRequestBody;
import com.yijian.clubmodule.net.requestbody.savemenu.MenuRequestBody;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import com.yijian.clubmodule.net.requestbody.LoginRequestBody;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;



/**
 * 所有的接口定义在这里写
 */
public interface ApiService {


    /*GET 请求 下载*/
    @Streaming
    @GET
    Observable<ResponseBody> downloadFileGET(
            @Url String fileUrl
    );

    /*POST 请求 下载*/
    @Streaming
    @POST
    Observable<ResponseBody> downloadFilePOST(
            @Url String fileUrl
    );


    /*POST 请求 上传单个文件*/
    @Multipart
    @POST()
    Observable<JSONObject> upLoadImage(
            @Url String url,
            @Part() MultipartBody.Part file
    );

    /*POST 请求 上传单个文件*/
    @Multipart
    @POST()
    Observable<JSONObject> upLoadImageHasParam(
            @Url String url,
            @HeaderMap Map<String, String> headers,
            @Query("fileType") Integer param,
            @Part() List<MultipartBody.Part> parts
    );

    /*POST 请求 上传文件*/

    @Multipart
    @POST()
    Observable<JSONObject> uploadFiles(@Url String url, @HeaderMap Map<String, String> headers, @Query("fileType") String param, @Part() List<MultipartBody.Part> parts);

    //登录
    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> login(@Url String url, @Body LoginRequestBody loginRequest);


    //提交回访记录
    @POST
    Observable<JSONObject> postAddHuiFangResult(@Url String url, @HeaderMap Map<String, String> headers, @Body AddHuiFangResultBody body);

    //获取问卷列表
    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> getQuestionnaireList(@Url String url, @HeaderMap Map<String, String> headers, @Body QuestionnaireRequestBody body);



    //添加潜在
    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postAddPotential(@Url String addPotentialUrl, @HeaderMap Map<String, String> headers, @Body AddPotentialRequestBody addPotentialRequestBody);


    //保存图标位置
    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> saveMenuChange(@Url String url, @HeaderMap Map<String, String> headers, @Body MenuRequestBody menuRequestBody);

    //私教课
    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> getCoachPrivateCourseList(@Url String url, @HeaderMap Map<String, String> headers, @Body CoachPrivateCourseRequestBody body);

    //会籍卡产品
    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> getHuiJiCardGoodsList(@Url String url, @HeaderMap Map<String, String> headers, @Body CardRequestBody body);

    /**
     * 表单请求
     *
     * @param url
     * @param username
     * @param telephone
     * @return
     */

    //发送验证码
    @FormUrlEncoded
    @POST
    Observable<JSONObject> getCode(@Url String url, @Field("username") String username, @Field("telephone") String telephone);

    //找回密码
    @FormUrlEncoded
    @POST
    Observable<JSONObject> resetPassword(@Url String url, @Field("username") String username, @Field("telephone") String telephone, @Field("verificationCode") String verificationCode, @Field("newPwd") String newPwd, @Field("confirmPwd") String confirmPwd);


    //会籍（客服）获取会员列表
    @GET
    Observable<JSONObject> getDataList(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> param);

    //首页图标
    @GET
    Observable<JSONObject> getIndexMenuList(@Url String url, @HeaderMap Map<String, String> headers);


    /**
     * 会籍会员详情编辑
     *
     * @param url
     * @param headers
     * @param editHuiJiVipBody
     * @return
     */
    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> editHuiJiVipDetail(@Url String url, @HeaderMap Map<String, String> headers, @Body EditHuiJiVipBody editHuiJiVipBody);


    /**
     * 保存私教课备课内容
     */
    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> savePrivatePrepareLesson(@Url String url, @HeaderMap Map<String, String> headers, @Body PrivatePrepareLessonBody privatePrepareLessonBody);


    //post 表单
    @POST
    Observable<JSONObject> postNoHeaderNoParam(@Url String url);

    @POST
    Observable<JSONObject> postHasHeaderNoParam(@Url String url, @HeaderMap Map<String, String> headers);

    @POST
    Observable<JSONObject> postNoHeaderHasParam(@Url String url, @QueryMap Map<String, String> param);

    @FormUrlEncoded
    @POST
    Observable<JSONObject> postHasHeaderHasParam(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST
    Observable<JSONObject> postHasHeaderHasParamOfObject(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, Object> param);

    @FormUrlEncoded
    @POST
    Observable<JSONObject> postHasHeaderHasParamOfInteger(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, Integer> param);


    //get 有请求头
    @GET
    Observable<JSONObject> getNoHeaderNoParam(@Url String url);

    @GET
    Observable<JSONObject> getHasHeaderNoParam(@Url String url, @HeaderMap Map<String, String> headers);

    @GET
    Observable<JSONObject> getNoHeaderHasParam(@Url String url, @QueryMap Map<String, String> param);

    @GET
    Observable<JSONObject> getHasHeaderHasParam(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, String> param);

    @GET
    Observable<JSONObject> getHasHeaderHasObjectParam(@Url String url, @HeaderMap Map<String, String> headers, @QueryMap Map<String, Object> param);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> getBusinessMessage(@Url String loginUrl, @HeaderMap Map<String, String> headers, @Body BusinessMessageRequestBody businessMessageRequestBody);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postAddAdvice(@Url String url, @HeaderMap HashMap<String, String> headers, @Body AddAdviceBody addAdviceBody);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> getHuiJiInviteRecord(@Url String indexHuiJiInvitationRecordUrl, @HeaderMap HashMap<String, String> headers, @Body HuiJiInviteListRequestBody body);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postAccessStatistics(@Url String postAccessStatisticsUrl, @HeaderMap Map<String, String> headers, @Body List<AccessStatisticsRequestBody> accessStatisticsRequestBody);


    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postInvateContent(@Url String url, @HeaderMap HashMap<String, String> headers, @Body SaveInviteBody saveInviteBody);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postHuiFangTask(@Url String url, @HeaderMap HashMap<String, String> headers, @Body HuifangTaskRequestBody huifangTaskRequestBody);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postFuHuiFangResult(@Url String url, @HeaderMap HashMap<String, String> headers, @Body AddFuFangResultBody body);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postHuiFangRecord(@Url String url, @HeaderMap HashMap<String, String> headers, @Body HuifangRecordRequestBody huifangRecordRequestBody);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postPrivateCoursePingJia(@Url String url, @HeaderMap HashMap<String, String> headers, @Body PrivateCoursePingJiaRequestBody body);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postSaveCourse(@Url String url, @HeaderMap HashMap<String, String> headers, @Body SaveCourseRequestBody body);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postAbortFuFang(@Url String url, @HeaderMap HashMap<String, String> headers, @Body AbortFuFangBody body);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postHuiFangType(@Url String url, @HeaderMap HashMap<String, String> headers, @Body HuiFangTypeRequestBody huifangTaskRequestBody);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postLockTime(@Url String url, @HeaderMap HashMap<String, String> headers, @Body SaveCourseRequestBody.PrivateCoachCAPDTOsBean body);

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST
    Observable<JSONObject> postUpdateFlag(@Url String url, @HeaderMap Map<String, String> headers,  @QueryMap Map<String, String> param);
}
