package com.dreawer.customer.form;

import java.sql.Date;

/**
 * <CODE>MemberConverter</CODE>
 * 数据绑定消息实体类
 * @author fenrir
 * @Date 18-4-18
 */
public class MemberConverter {


    public String id; //主键

    private String storeId; //店铺Id

    private String phoneNumber; //电话号码

    private String mugshot; //头像

    private String nickName; //昵称

    private String userName; //客户姓名

    private Integer sex; //性别

    private Date birthday;//生日


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMugshot() {
        return mugshot;
    }

    public void setMugshot(String mugshot) {
        this.mugshot = mugshot;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    /**
     * 转换毫秒值类型为Date
     *
     * @param birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = new Date(Long.valueOf(birthday));
    }


}


