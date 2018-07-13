package com.dreawer.customer.domain;

import java.util.Date;

import com.dreawer.domain.BaseDomain;

public class RememberKey extends BaseDomain {
    private static final long serialVersionUID = -1595038686036008371L;

    private User user = null; // 用户信息
    
    private String value = null; // 秘钥值
    
    private Date createTime = null; // 生成时间
    
    private Date lastUseTime = null; // 最近使用时间
    
    /**
     * 默认构造器。
     */
    public RememberKey() {
        super();
    }
    
    /**
     * 获取属性 <TT>user</TT>（用户信息）的值。
     * @return <TT>user</TT> 用户信息。
     */
    public User getUser() {
        return user;
    }

    /**
     * 设置属性 <TT>user</TT>（用户信息）的值。
     * @param user 用户信息。
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 获取属性 <TT>value</TT>（秘钥值）的值。
     * @return <TT>value</TT> 秘钥值。
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置属性 <TT>value</TT>（秘钥值）的值。
     * @param value 秘钥值。
     */
    public void setValue(String value) {
        this.value = value;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUseTime() {
		return lastUseTime;
	}

	public void setLastUseTime(Date lastUseTime) {
		this.lastUseTime = lastUseTime;
	}

    
}
