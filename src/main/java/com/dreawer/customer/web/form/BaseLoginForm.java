package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class BaseLoginForm {

	@NotEmpty(message="EntryError.EMPTY")
    private String loginName = null; // 登录名
	
	@Length(min=6, max=20, message="EntryError.OVERRANGE")
	private String password = null; // 密码
	
	@NotEmpty(message="EntryError.EMPTY")
	private String appid = null; // 名称

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
}
