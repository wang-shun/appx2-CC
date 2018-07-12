package com.dreawer.customer.domain;

import java.util.Date;

import com.dreawer.customer.lang.VerifyType;
import com.dreawer.domain.BaseDomain;

public class Verify extends BaseDomain{
	
	private static final long serialVersionUID = -1431998962774641623L;
	
	private User user = null; // 用户
	
	private VerifyType type = null; // 认证方式

	private String phoneNumber = null; // 手机号

	private String email = null; // 邮箱

	private String value = null; // 验证码

    private Date createTime = null; // 创建时间

    
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VerifyType getType() {
		return type;
	}

	public void setType(VerifyType type) {
		this.type = type;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
