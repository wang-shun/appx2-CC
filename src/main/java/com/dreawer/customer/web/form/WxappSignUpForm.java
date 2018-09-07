package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class WxappSignUpForm {

	private String mugshot = null; // 头像

	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=1, max=20, message="EntryError.OVERRANGE")
	private String petName = null; // 昵称
	
	@NotEmpty(message="EntryError.EMPTY")
	private String appId = null;

	public String getMugshot() {
		return mugshot;
	}

	public void setMugshot(String mugshot) {
		this.mugshot = mugshot;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
