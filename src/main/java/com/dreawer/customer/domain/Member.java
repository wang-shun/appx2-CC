package com.dreawer.customer.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * <CODE>Member</CODE>
 * 会员实体类
 *
 * @author fenrir
 * @Date 18-3-19
 */

@Data
@ApiModel("会员实体类")
public class Member implements Serializable {


    private static final long serialVersionUID = 4044203951388598001L;

    @ApiModelProperty(name = "id",value = "主键")
    public String id; //主键

    @ApiModelProperty(name = "storeId",value = "店铺Id")
    private String storeId; //店铺Id

    private String phoneNumber; //电话号码

    private String mugshot; //头像

    private String nickName; //昵称

    private String userName; //客户姓名

    private Integer sex; //性别

    private Timestamp birthday;//生日

    private Integer growthValue; //成长值

    private Timestamp dueDate; //到期日

    private String hierarchyId; //会员等级关系表id

    private Hierarchy hierarchy; //会员等级实体

    private Timestamp createTime; //创建时间

    private Timestamp updateTime; //更新时间

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMugshot() {
        return mugshot;
    }

    public void setMugshot(String mugshot) {
        this.mugshot = mugshot;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = new Timestamp(birthday);
    }

    public String getHierarchyId() {
        return hierarchyId;
    }

    public void setHierarchyId(String hierarchyId) {
        this.hierarchyId = hierarchyId;
    }


    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getGrowthValue() {
        return growthValue;
    }

    public void setGrowthValue(Integer growthValue) {
        this.growthValue = growthValue;
    }


    public Hierarchy getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", storeId='" + storeId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mugshot='" + mugshot + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", growthValue=" + growthValue +
                ", dueDate=" + dueDate +
                ", hierarchyId='" + hierarchyId + '\'' +
                ", hierarchy=" + hierarchy +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }


}
