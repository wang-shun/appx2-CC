package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class EmailBaseForm {

	@NotEmpty(message="EntryError.EMPTY")
	@Email(message="EntryError.FORMAT")
    private String email = null; // 邮箱

	@NotEmpty(message="EntryError.EMPTY")
    private String type = null; // 类型

	@NotEmpty(message="EntryError.EMPTY")
	private String appid = null;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
}
