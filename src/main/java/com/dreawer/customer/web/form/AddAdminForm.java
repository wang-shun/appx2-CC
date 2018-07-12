package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class AddAdminForm {
	
	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=2, max=20, message="EntryError.OVERRANGE")
	private String name = null;
	
	@NotEmpty(message="EntryError.EMPTY")
	@Length(min=3, max=20, message="EntryError.OVERRANGE")
	private String username = null;
	
	private String photo = null;
	
	@Length(min=6, max=20, message="EntryError.OVERRANGE")
	private String password = null;
	
	@NotEmpty(message="EntryError.EMPTY")
	private String appname = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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
