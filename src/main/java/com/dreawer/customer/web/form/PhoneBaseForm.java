package com.dreawer.customer.web.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class PhoneBaseForm {

	@NotEmpty(message="EntryError.EMPTY")
	@Pattern(regexp="^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$",
		message="EntryError.FORMAT")    
	private String phone = null; // 手机号
	
	@NotEmpty(message="EntryError.EMPTY")
    private String type = null; // 类型

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
