package com.dreawer.customer.domain;

import java.io.Serializable;

public class SystemInfo implements Serializable{
	
	private static final long serialVersionUID = -5893750042640505248L;

	private String ip = null; //ip地址

    private String mac = null; //mac地址

    private String browser = null; //浏览器

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}
	
}
