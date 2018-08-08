package com.dreawer.customer.form;


/**
 * <CODE>AddDecorationServiceForm</CODE> 添加服务表单。
 * @author kael
 * @since Dreawer 2.0
 * @version 1.0
 */
public class RegisterMemberForm {
    
	private String storeId = null; //店铺ID
	
    private String phoneNumber  = null; // 电话号码
    
    private Integer sex = null; // 性别（0-女性，1-男性，2-未知）
	
    private String userName = null; //用户名称

    private String birthday = null; //生日（时间戳）

    private String captcha = null; //验证码
    
    private String mugshot = null; //验证码
    
    private String nickName = null; //昵称


    // --------------------------------------------------------------------------------
    // getter 和 setter 方法
    // --------------------------------------------------------------------------------
    
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


}
