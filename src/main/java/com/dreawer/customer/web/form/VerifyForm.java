package com.dreawer.customer.web.form;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.dreawer.customer.lang.VerifyType;

public class VerifyForm {

	@NotEmpty(message="EntryError.EMPTY")
	private VerifyType type = null; // 类型

	@Pattern(regexp="^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$",
			message="EntryError.FORMAT")
	private String phone = null; // 手机号

	@Email(message="EntryError.FORMAT")
    private String email = null; // 邮箱

	@NotEmpty(message="EntryError.EMPTY")
	private String appId = null;
	
	public VerifyType getType() {
		return type;
	}

	public void setType(String type) {
		this.type = VerifyType.valueOf(type);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}


}
