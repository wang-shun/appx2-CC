package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class EditAddressForm extends AddAddressForm{
	
	@NotEmpty(message="EntryError.EMPTY")
	private String id = null;

	@NotEmpty(message="EntryError.EMPTY")
	private String userId = null;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
