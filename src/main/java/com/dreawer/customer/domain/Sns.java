package com.dreawer.customer.domain;

import java.sql.Timestamp;

import com.dreawer.customer.lang.BindingType;
import com.dreawer.domain.BaseDomain;

public class Sns extends BaseDomain{

	private static final long serialVersionUID = -8634135835560941541L;
	
	private User user = null; // 用户
	
	private App app = null; // 应用

    private String uid = null; // 社交网站UID
    
    private String wxid = null; // 微信平台唯一id
    
    private String petName = null; // 用户昵称
    
    private BindingType type = null; // sns类型
    
    private String token = null; // sns令牌
    
    private Timestamp overdueTime = null; // 创建时间

    private Timestamp createTime = null; // 创建时间
    
    private Timestamp updateTime = null; // 更新时间
    
    private User creater = null; // 创建人

    private User updater = null; // 修改人

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public BindingType getType() {
		return type;
	}

	public void setType(BindingType type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getOverdueTime() {
		return overdueTime;
	}

	public void setOverdueTime(Timestamp overdueTime) {
		this.overdueTime = overdueTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public User getUpdater() {
		return updater;
	}

	public void setUpdater(User updater) {
		this.updater = updater;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

}
