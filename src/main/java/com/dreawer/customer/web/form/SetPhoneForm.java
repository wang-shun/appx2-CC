package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class SetPhoneForm {

	@NotEmpty(message="EntryError.EMPTY")
	@Pattern(regexp="^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$",
		message="EntryError.FORMAT")    
	private String phone = null; // 手机号

	@NotEmpty(message="EntryError.EMPTY")
    private String captcha = null; // 验证码

	@NotEmpty(message="EntryError.EMPTY")
	private String appId = null;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}
