package com.yijian.staff.bean;

import com.yijian.staff.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * author：李帅华
 * email：850716183@qq.com
 * time: 2018/5/8 16:24:54
 */
public class TodayCoachViperBean implements Serializable{
    /**
     * CoachExpireVO：教练过期
     * CoachInfoVO ：教练正式
     * CoachIntentionVO：教练意向
     * CoachTodayVisitVO：教练今日来访
     * CustomerInfoVO：会籍正式
     * CustomerTodayVisitVO：会籍今日来访
     * CustomerExpireVO：会籍过期
     * CustomerIntentionVO：会籍意向
     * PotentialVO：潜在（会籍教练共用）
     */
    private String subclassName;
    private int experienceClassTimes;
    private String seller;//服务会籍

    private String fitnessHobby;
    private String mobile;
    private Boolean underProtected;


    private String healthStatus; //身体状态
    private String bodybuildingHobby; //健身爱好
    private String interestHobby; //兴趣爱好
    private String hobby; //兴趣爱好
    private String useCar; //使用车辆
    private String isIntentVip; // 0 意向会员  ，1  潜在会员

    private Long birthday;//生日
    private String birthdayType;//生日类型
    private String name;//会员姓名
    private String viperRole;//会员角色 普通会员

    private String sex;//性别 1:男 2:女
    private String memberId;//会员id
    private String headImg;//头像图片路径

    private String contractBalance;//合同余额
    private Long contractDeadline;//合同到期日
    private String contractId;//合同ID
    private String privateCourse;//私教课
    private String favorCourse;//喜欢的课程
    private String favorTeacher;//喜欢的教练
    private int purchaseCount;//购买次数
    private Long registerTime;//入籍时间
    private Long visitTime; //到场时间
    private Long leaveTime; //离场时间
    private String privateClass;//私教课
    private String fiirstId;//第一次体验课上课记录id ,
    private String secondId;//第二次体验课上课记录id
    private int firstType;//第一次体验课上课记录表类型    0 非自定义 1自定义
    private int secondType;//第二次体验课上课记录表类型  0 非自定义 1自定义

    private int clockedCount;

    private String historyCourse; //历史课程
    private Long deadline; //过期时间
    private String expiryReason; //过期原因

    //("合同ID列表")
    private List<String> contractIds;
    //("卡对象集合")
    private List<CardprodsBean> cardprodsBeans;

    public TodayCoachViperBean(JSONObject jsonObject) {
        this.birthday = JsonUtil.getLong(jsonObject, "birthday");
        this.birthdayType = JsonUtil.getString(jsonObject, "birthdayType");
        this.name = JsonUtil.getString(jsonObject, "name");
        this.viperRole = JsonUtil.getString(jsonObject, "viperRole");
        this.sex = JsonUtil.getString(jsonObject, "sex");
        this.memberId = JsonUtil.getString(jsonObject, "memberId");
        this.headImg = JsonUtil.getString(jsonObject, "headImg");
        this.experienceClassTimes = JsonUtil.getInt(jsonObject, "experienceClassTimes");
        this.seller = JsonUtil.getString(jsonObject, "seller");
        this.subclassName = JsonUtil.getString(jsonObject, "subclassName");
        this.contractBalance = JsonUtil.getString(jsonObject, "contractBalance");
        this.contractDeadline = JsonUtil.getLong(jsonObject, "contractDeadline");
        this.contractId = JsonUtil.getString(jsonObject, "contractId");
        this.privateCourse = JsonUtil.getString(jsonObject, "privateCourse");
        this.favorCourse = JsonUtil.getString(jsonObject, "favorCourse");
        this.favorTeacher = JsonUtil.getString(jsonObject, "favorTeacher");
        this.purchaseCount = JsonUtil.getInt(jsonObject, "purchaseCount");
        this.registerTime = JsonUtil.getLong(jsonObject, "registerTime");
        this.visitTime = JsonUtil.getLong(jsonObject, "bePresentTime");
        this.leaveTime = JsonUtil.getLong(jsonObject, "departureTime");
        this.healthStatus = JsonUtil.getString(jsonObject, "healthStatus");
        this.bodybuildingHobby = JsonUtil.getString(jsonObject, "bodybuildingHobby");
        this.interestHobby = JsonUtil.getString(jsonObject, "interestHobby");
        this.fitnessHobby = JsonUtil.getString(jsonObject, "fitnessHobby");
        this.hobby = JsonUtil.getString(jsonObject, "hobby");
        this.useCar = JsonUtil.getString(jsonObject, "useCar");
        this.isIntentVip = JsonUtil.getString(jsonObject, "isIntentVip");
        this.privateClass = JsonUtil.getString(jsonObject, "privateClass");
        this.historyCourse = JsonUtil.getString(jsonObject, "historyCourse");
        this.deadline = JsonUtil.getLong(jsonObject, "deadline");
        this.expiryReason = JsonUtil.getString(jsonObject, "expiryReason");
        this.fiirstId = JsonUtil.getString(jsonObject, "fiirstId");
        this.secondId = JsonUtil.getString(jsonObject, "secondId");
        this.mobile = JsonUtil.getString(jsonObject, "mobile");
        this.underProtected = JsonUtil.getBoolean(jsonObject, "underProtected");
        this.firstType = JsonUtil.getInt(jsonObject, "firstType");
        this.secondType = JsonUtil.getInt(jsonObject, "secondType");
        this.clockedCount = JsonUtil.getInt(jsonObject, "clockedCount");
        this.contractIds = com.alibaba.fastjson.JSONArray.parseArray(JsonUtil.getJsonArray(jsonObject, "contractIds").toString(), String.class);

        try {
            if (jsonObject.has("cardprods")) {

                this.cardprodsBeans = com.alibaba.fastjson.JSONObject.parseArray(jsonObject.getJSONArray("cardprods").toString(), CardprodsBean.class);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getClockedCount() {
        return clockedCount;
    }

    public int getFirstType() {
        return firstType;
    }

    public int getSecondType() {
        return secondType;
    }

    public String getFiirstId() {
        return fiirstId;
    }

    public String getSecondId() {
        return secondId;
    }

    public String getMobile() {
        return mobile;
    }

    public Boolean getProtected() {
        return underProtected;
    }

    public String getSubclassName() {
        return subclassName;
    }

    public List<CardprodsBean> getCardprodsBeans() {
        return cardprodsBeans;
    }

    public void setCardprodsBeans(List<CardprodsBean> cardprodsBeans) {
        this.cardprodsBeans = cardprodsBeans;
    }

    public String getFitnessHobby() {
        return fitnessHobby;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public String getHobby() {
        return hobby;
    }

    public List<String> getContractIds() {
        return contractIds;
    }


    public String getHistoryCourse() {
        return historyCourse;
    }

    public Long getDeadline() {
        return deadline;
    }

    public String getExpiryReason() {
        return expiryReason;
    }


    public String getBodybuildingHobby() {
        return bodybuildingHobby;
    }


    public String getInterestHobby() {
        return interestHobby;
    }

    public void setInterestHobby(String interestHobby) {
        this.interestHobby = interestHobby;
    }

    public String getUseCar() {
        return useCar;
    }

    public void setUseCar(String useCar) {
        this.useCar = useCar;
    }

    public String getIsIntentVip() {
        return isIntentVip;
    }

    public void setIsIntentVip(String isIntentVip) {
        this.isIntentVip = isIntentVip;
    }

    public Long getVisitTime() {
        return visitTime;
    }

    public Long getLeaveTime() {
        return leaveTime;
    }

    public Long getBirthday() {
        return birthday;
    }


    public String getBirthdayType() {
        return birthdayType;
    }

    public void setBirthdayType(String birthdayType) {
        this.birthdayType = birthdayType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getExperienceClassTimes() {
        return experienceClassTimes;
    }

    public String getSeller() {
        return seller;
    }

    public String getContractBalance() {
        return contractBalance;
    }

    public void setContractBalance(String contractBalance) {
        this.contractBalance = contractBalance;
    }

    public Long getContractDeadline() {
        return contractDeadline;
    }

    public void setContractDeadline(Long contractDeadline) {
        this.contractDeadline = contractDeadline;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getPrivateCourse() {
        return privateCourse;
    }

    public void setPrivateCourse(String privateCourse) {
        this.privateCourse = privateCourse;
    }

    public String getFavorCourse() {
        return favorCourse;
    }

    public void setFavorCourse(String favorCourse) {
        this.favorCourse = favorCourse;
    }

    public String getFavorTeacher() {
        return favorTeacher;
    }

    public void setFavorTeacher(String favorTeacher) {
        this.favorTeacher = favorTeacher;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public Long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public String getPrivateClass() {
        return privateClass;
    }

    public void setPrivateClass(String privateClass) {
        this.privateClass = privateClass;
    }

    public String getViperRole() {
        return viperRole;
    }

    public void setViperRole(String viperRole) {
        this.viperRole = viperRole;
    }


}

