package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UserBaseForm {

	@NotEmpty(message="EntryError.EMPTY")
    @Length(min=32, max=32, message="EntryError.OVERRANGE")
	private String userId = null;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
