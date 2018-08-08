package com.dreawer.customer.form;

import org.hibernate.validator.constraints.NotEmpty;

public class EditAddressForm extends AddAddressForm{
	
	@NotEmpty(message="EntryError.EMPTY")
	private String id = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
