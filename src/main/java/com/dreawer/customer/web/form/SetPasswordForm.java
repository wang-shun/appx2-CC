package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class SetPasswordForm {
	
	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=6, max=20, message="EntryError.OVERRANGE")
    private String oldPassword = null; // 原密码

	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=6, max=20, message="EntryError.OVERRANGE")
    private String newPassword = null; // 密码

	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=6, max=20, message="EntryError.OVERRANGE")
    private String confirmPassword = null; // 确认密码
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
