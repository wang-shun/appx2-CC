package com.dreawer.customer.domain;

import com.dreawer.customer.lang.ClientType;
import com.dreawer.customer.lang.SignInFailCause;
import com.dreawer.customer.lang.SignInStatus;
import com.dreawer.domain.BaseDomain;

import java.util.Date;

public class SignInLog extends BaseDomain{
    private static final long serialVersionUID = -7788528035675069861L;

    private String userId = null; // 用户
    
	private ClientType type = null; // 登录方式
	
	private String ip = null; // IP 地址
	
	private SignInStatus status = null; // 登录状态
    
    private SignInFailCause cause = null; // 登录失败的原因
    
    private Date time = null; // 创建时间
	
	/**
	 * 默认构造器。
	 */
	public SignInLog() {
		super();
	}
	
	/**
	 * 获取属性 <TT>type</TT>（登录方式）的值。
     * @return <TT>type</TT> 登录方式。
	 */
	public ClientType getType() {
		return type;
	}

	/**
	 * 设置属性 <TT>type</TT>（登录方式）的值。
     * @param type 登录方式。
	 */
	public void setType(ClientType type) {
		this.type = type;
	}
	
	/**
     * 获取属性 <TT>ip</TT>（IP 地址）的值。
     * @return IP 地址。
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置 <TT>ip</TT>（IP 地址）的值。
     * @param ip IP 地址。
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
	
	/**
     * 获取属性 <TT>status</TT>（登录状态）的值。
     * @return <TT>status</TT> 登录状态。
     */
    public SignInStatus getStatus() {
        return status;
    }

    /**
     * 设置属性 <TT>status</TT>（登录状态）的值。
     * @param status 登录状态。
     */
    public void setStatus(SignInStatus status) {
        this.status = status;
    }

    /**
     * 获取属性 <TT>cause</TT>（登录失败的原因）的值。
     * @return <TT>cause</TT> 登录失败的原因。
     */
    public SignInFailCause getCause() {
        return cause;
    }

    /**
     * 设置属性 <TT>cause</TT>（登录失败的原因）的值。
     * @param cause 登录失败的原因。
     */
    public void setCause(SignInFailCause cause) {
        this.cause = cause;
    }
    
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
}
