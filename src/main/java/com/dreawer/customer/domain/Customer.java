package com.dreawer.customer.domain;

import com.dreawer.customer.lang.UserStatus;
import com.dreawer.domain.BaseDomain;

import java.util.Date;

import static org.apache.commons.lang.StringUtils.isBlank;

public class Customer extends BaseDomain{
	
    private static final long serialVersionUID = -3980205475027276614L;
    
    private String organizeId = null; // 组织id

    private String name = null; // 名字（它可以是：个人的姓名或企业、团队、产品的全名）
    
    private String petName = null; // 昵称

    private String mugshot = null; // 头像

    private String alias = null; // 别名（它可以是：昵称的拼音+编号、昵称的拼音+英文字母+编号两种形式，它在系统中保持唯一）
    
    private String slogan = null; // 品牌宣传口号（它可以是：个人品牌宣传口号，或企业、团队、产品品牌的宣传口号）
    
    private String intro = null; // 简介
    
    private String phoneNumber = null; // 手机号

    private String email = null; // 常用邮箱
    
    private String homepage = null; // 官方（个人）主页
    
    private String category = null; // 分类
    
    private UserStatus status = null; // 状态
    
    private String creater = null; // 创建者
    
    private Date createTime = null; // 创建时间
    
    private String updater = null; // 更新者
    
    private Date updateTime = null; // 更新时间
    
    // --------------------------------------------------------------------------------
    // 构造器
    // --------------------------------------------------------------------------------
    
    /**
     * 默认构造器。
     */
    public Customer() {
        super();
    }
    
    public Customer(String id) {
        super(id);
    }
    // --------------------------------------------------------------------------------
    // getter 和 setter 方法
    // --------------------------------------------------------------------------------
    
    /**
     * 获取属性 <TT>name</TT>（名字）的值。
     * @return <TT>name</TT> 名字。
     */
    public String getName() {
        return name;
    }

    /**
     * 设置属性 <TT>name</TT>（名字）的值。<br/>
     * 它可以是：个人的姓名或企业、团队、产品的全名。
     * @param name 名字。
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 获取属性 <TT>alias</TT>（别名）的值。
     * @return <TT>alias</TT> 别名。
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 设置属性 <TT>alias</TT>（别名）的值。<br/>
     * 它可以是：昵称的拼音+编号、昵称的拼音+英文字母+编号两种形式，它在系统中保持唯一。
     * @param alias 别名。
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    /**
     * 获取属性 <TT>slogan</TT>（品牌宣传口号）的值。
     * @return <TT>slogan</TT> 品牌宣传口号。
     */
    public String getSlogan() {
        return slogan;
    }
    
    /**
     * 设置属性 <TT>slogan</TT>（品牌宣传口号）的值。<br/>
     * 它可以是：个人品牌宣传口号，或企业、团队、产品品牌的宣传口号
     * @param slogan 品牌宣传口号。
     */
    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
    
    /**
     * 获取属性 <TT>intro</TT>（简介）的值。
     * @return <TT>intro</TT> 简介。
     */
    public String getIntro() {
        return intro;
    }
    
    /**
     * 设置属性 <TT>intro</TT>（简介）的值。
     * @param intro 简介。
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }
    
    /**
     * 获取属性 <TT>email</TT>（常用邮箱）的值。
     * @return <TT>email</TT> 常用邮箱。
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * 设置属性 <TT>email</TT>（常用邮箱）的值。
     * @param email 常用邮箱。
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * 获取属性 <TT>homepage</TT>（官方主页）的值。
     * @return <TT>homepage</TT> 官方主页。
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * 设置属性 <TT>homepage</TT>（官方主页）的值。
     * @param homepage 官方主页。
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
    
    /**
     * 获取属性 <TT>category</TT> （分类）的值。
     * @return <TT>category</TT> 分类。
     */
    public String getCategory() {
        if(isBlank(category)) {
            category = this.getClass().getSimpleName().toLowerCase();
        }
        return category;
    }

    /**
     * 设置属性 <TT>category</TT> （分类）的值。
     * @param <TT>category</TT> 分类。
     */
    public void setCategory(String category) {
        this.category = category;
    }

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
        Customer other = (Customer) obj;
        if (getId().equals(other.getId())) {
            return true;
        }
        return false;
    }
    
    /* 
     * (non-Javadoc)
     * @see com.dreawer.dream.domain.BaseDomain#toString()
     */
    @Override
    public String toString() {
        String id = getId();
        if (id != null) {
            return id;
        } else {
            return alias;
        }
    }

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getMugshot() {
		return mugshot;
	}

	public void setMugshot(String mugshot) {
		this.mugshot = mugshot;
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
