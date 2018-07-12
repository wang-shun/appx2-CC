package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class SetAdminForm {

	@NotEmpty(message="EntryError.EMPTY")
    @Length(min = 32, max = 32, message = "EntryError.OVERRANGE")
    private String id = null; // 设置管理员的id
	
	@NotEmpty(message="EntryError.EMPTY")
	private String appname = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}
	
}
