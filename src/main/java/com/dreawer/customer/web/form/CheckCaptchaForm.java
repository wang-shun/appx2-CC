package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class CheckCaptchaForm {
	
	@NotEmpty(message="EntryError.EMPTY")
	private String value = null;
	
	@NotEmpty(message="EntryError.EMPTY")
	private String captcha = null;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
}
