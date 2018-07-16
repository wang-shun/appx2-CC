package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class AddOrganizeForm {
	
	@NotEmpty(message="EntryError.EMPTY")
    @Length(min=32, max=32, message="EntryError.OVERRANGE")
    private String appId = null; // 应用id

    @Length(min=2, max=20, message="EntryError.OVERRANGE")
    private String name = null; // 名字
	
    @Length(min=2, max=20, message="EntryError.OVERRANGE")
    private String category = null; // 分类

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
