package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class ResetPasswordForm extends VerifyForm{
	
	@NotEmpty(message="EntryError.EMPTY")
    private String password = null; // 密码

	@NotEmpty(message="EntryError.EMPTY")
    private String captcha = null; // 验证码

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
    
}
