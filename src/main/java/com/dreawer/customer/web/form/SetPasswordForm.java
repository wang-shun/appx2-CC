package com.dreawer.customer.web.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class SetPasswordForm {
	
	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=6, max=20, message="EntryError.OVERRANGE")
    private String oldPassword = null; // 原密码

	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=6, max=20, message="EntryError.OVERRANGE")
	@Pattern(regexp="^(?!([a-zA-Z]+)$)[0-9\\x21-\\x2f\\x3a-\\x40\\x5b-\\x60\\x7B-\\x7F]{0,}[a-zA-Z]+[a-zA-Z0-9\\x21-\\x2f\\x3a-\\x40\\x5b-\\x60\\x7B-\\x7F]{0,}$", 
		message="EntryError.FORMAT")
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
