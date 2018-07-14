package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class SetPasswordForm {
	
    private String oldPassword = null; // 原密码

	@NotEmpty(message="EntryError.EMPTY")
    private String password = null; // 密码

	@NotEmpty(message="EntryError.EMPTY")
	private String userId = null;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
    
}
