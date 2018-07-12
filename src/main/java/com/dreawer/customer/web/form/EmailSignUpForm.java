package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class EmailSignUpForm {
	
	private String mugshot = null; // 头像

	@NotEmpty(message="EntryError.EMPTY")
	@Email(message="EntryError.FORMAT")
    private String email = null; // 邮箱
	
	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=2, max=20, message="EntryError.OVERRANGE")
	private String petName = null; // 昵称
	
	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=6, max=20, message="EntryError.OVERRANGE")
	private String password = null; // 密码
	
	@NotEmpty(message="EntryError.EMPTY")
	private String appname = null; // 名称

	@NotEmpty(message="EntryError.EMPTY")
	private String captcha = null; // 验证码
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getMugshot() {
		return mugshot;
	}

	public void setMugshot(String mugshot) {
		this.mugshot = mugshot;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
}
