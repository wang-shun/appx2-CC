package com.dreawer.customer.domain;

import com.dreawer.customer.lang.UserStatus;
import com.dreawer.domain.BaseDomain;

import java.util.Date;

public class Organize extends BaseDomain{

	private static final long serialVersionUID = 1411992332984211352L;

    private String appId = null; // 应用id

    private String name = null; // 名字
	
    private String category = null; // 分类

    private UserStatus status = null; // 状态

    private Date createTime = null; // 创建时间

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

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
