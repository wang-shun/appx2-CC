package com.dreawer.customer.form;

import org.hibernate.validator.constraints.NotEmpty;

public class WxLoginForm {
	
	@NotEmpty(message="EntryError.EMPTY")
	private String encryptedData = null; // 加密信息
	
	@NotEmpty(message="EntryError.EMPTY")
	private String iv = null; // 加密算法的初始向量
	
	@NotEmpty(message="EntryError.EMPTY")
	private String code = null; // 授权码
	
	@NotEmpty(message="EntryError.EMPTY")
	private String appid= null; // 名称

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	
}
