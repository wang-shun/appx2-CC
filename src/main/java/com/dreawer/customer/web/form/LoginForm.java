package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {
	
	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=3, max=20, message="EntryError.OVERRANGE")
	private String username = null;
	
	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=6, max=20, message="EntryError.OVERRANGE")
	private String password = null;
	
	@NotEmpty(message="EntryError.EMPTY")
	private String appname = null; // 名称

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

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}
	
}
