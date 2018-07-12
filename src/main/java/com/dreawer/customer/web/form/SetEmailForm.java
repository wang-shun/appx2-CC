package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class SetEmailForm {

	@NotEmpty(message="EntryError.EMPTY")
	@Email(message="EntryError.FORMAT")
    private String email = null; // 邮箱

	@NotEmpty(message="EntryError.EMPTY")
    private String captcha = null; // 验证码
	
	@NotEmpty(message="EntryError.EMPTY")
	private String appid = null;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

}
