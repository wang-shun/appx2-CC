package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class EditUserForm {
	
	//@NotEmpty(message=VAL_CONT_ID_NOTEMPTY)
    //@Length(min=2, max=20, message=VAL_CONT_ID_LENGTH)
	//@Pattern(regexp="^[\u4e00-\u9fa5]+$", message=VAL_NAME_WRONG)
    private String username = null; // 用户名

    private String oldPassword = null; // 原密码

	@NotEmpty(message="EntryError.EMPTY")
    private String password = null; // 密码

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
    
}
