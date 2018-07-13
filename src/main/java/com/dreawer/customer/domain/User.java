package com.dreawer.customer.domain;

import java.util.Date;

import com.dreawer.customer.lang.UserStatus;
import com.dreawer.domain.BaseDomain;

public class User extends BaseDomain {
	
    private static final long serialVersionUID = 2955083100877772358L;
    
    // --------------------------------------------------------------------------------
    // 变量定义
    // --------------------------------------------------------------------------------
    
    private String username = null; // 用户名，由3~20位英文大小写字母、阿拉伯数字及下划线组成，以标明用户的客户。如：jone_2008
    
    private String password = null; // 登录密码，有效长度：6~20
    
    private String phoneNumber = null; // 手机号

    private String email = null; // E-Mail（注册邮箱）
    
    private String organizeId = null; // 组织id
    
    private UserStatus status = null; // 状态
    
    private Date createTime = null; // 创建时间
    
    private Date updateTime = null; // 更新时间

    private String updater = null; // 修改人
    
    /**
     * 获取属性 <TT>username</TT>（用户名）的值。
     * @return <TT>username</TT> 用户名。
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置属性 <TT>username</TT>（用户名）的值。它由3~20位英文大小写字母、阿拉伯数字及下划线组成，以标明用户的客户。如：jone_2008。
     * @param username 用户名。
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取属性 <TT>password</TT>（登录密码）的值。
     * @return <TT>password</TT> 登录密码。
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置属性 <TT>password</TT>（登录密码）的值。
     * @param password 登录密码。
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * 获取属性 <TT>email</TT>（注册邮箱）的值。
     * @return <TT>email</TT> 注册邮箱。
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置属性 <TT>email</TT>（注册邮箱）的值。
     * @param email 注册邮箱。
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * 获取属性 <TT>status</TT> （状态）的值。
     * @return <TT>status</TT> 状态。
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * 设置属性 <TT>status</TT> （状态）的值。
     * @param <TT>status</TT> 状态。
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }
	
	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    // --------------------------------------------------------------------------------
    // 其他方法
    // --------------------------------------------------------------------------------

	@Override
    public int hashCode() {
        // 防止拥有“相同基类”的“子类”之间进行比较，以降低equals方法调用频率
        final int prime = this.getClass().getName().hashCode();
        
        // 运算 Hash Code 值
        int hashValue = 0;
        if(getId() != null) {
            hashValue += getId().hashCode();
        }
        hashValue *= prime;
        return hashValue;
    }
    
    /*(non-Javadoc)
     * @see com.dreawer.dream.domain.BaseDomain#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        // 短路处理（空对象）
        if (obj == null) {
            return false;
        }
        
        // 短路处理（类型不一致）
        if (!(this.getClass() == obj.getClass())) {
            return false;
        }
        
        // “用户名”相同，则认为两个对象相等
        User other = (User) obj;
        if (getId().equals(other.getId())) {
            return true;
        }
        return false;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
	}

}
