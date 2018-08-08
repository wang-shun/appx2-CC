package com.dreawer.customer.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class AddAddressForm {

	@NotEmpty(message="EntryError.EMPTY")
    @Length(min=2, max=20, message="EntryError.OVERRANGE")
	@Pattern(regexp="^([\u4E00-\u9FA5]+|[a-zA-Z]+)$", message="EntryError.FORMAT")
	private String name = null; // 姓名

	@NotEmpty(message="EntryError.EMPTY")
	@Pattern(regexp="^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$",
			message="EntryError.FORMAT")
	private String phone = null; // 手机号

	@NotEmpty(message="EntryError.EMPTY")
    @Length(min=1, max=20, message="EntryError.OVERRANGE")
	private String province = null; // 省份
	
	@NotEmpty(message="EntryError.EMPTY")
    @Length(min=1, max=20, message="EntryError.OVERRANGE")
    private String city = null; // 城市
	
	@NotEmpty(message="EntryError.EMPTY")
    @Length(min=1, max=20, message="EntryError.OVERRANGE")
    private String area = null; // 地区
	
	@NotEmpty(message="EntryError.EMPTY")
    @Length(min=2, max=200, message="EntryError.OVERRANGE")
    private String detail = null; // 详细地址

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
