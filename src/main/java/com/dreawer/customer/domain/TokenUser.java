package com.dreawer.customer.domain;

import com.dreawer.domain.BaseDomain;

public class TokenUser extends BaseDomain{

	private static final long serialVersionUID = 8650970185938005907L;

    private String organizeId = null; // 组织id

	private String password = null; // 登录密码，有效长度：6~20
    
    private String phoneNumber = null; // 手机号

    private String email = null; // E-Mail（注册邮箱）
	
    private String petName = null; // 昵称

    private String mugshot = null; // 头像

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getMugshot() {
		return mugshot;
	}

	public void setMugshot(String mugshot) {
		this.mugshot = mugshot;
	}

	public String getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
	}

	
}
