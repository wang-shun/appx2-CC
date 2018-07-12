package com.dreawer.customer.web.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class PhoneSignUpForm {
	
	private String mugshot = null; // 头像

	@NotEmpty(message="EntryError.EMPTY")
	@Pattern(regexp="^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$",
		message="EntryError.FORMAT")    
	private String phone = null; // 手机号
	
	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=2, max=20, message="EntryError.OVERRANGE")
	private String petName = null; // 昵称
	
	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=6, max=20, message="EntryError.OVERRANGE")
	private String password = null; // 密码
	
	@NotEmpty(message="EntryError.EMPTY")
	private String appid = null;
	
	@NotEmpty(message="EntryError.EMPTY")
	private String captcha = null; // 验证码

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

	public String getMugshot() {
		return mugshot;
	}

	public void setMugshot(String mugshot) {
		this.mugshot = mugshot;
	}

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

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

}
