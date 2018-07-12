package com.dreawer.customer.domain;

import java.sql.Timestamp;

import com.dreawer.domain.BaseDomain;

public class Sequence extends BaseDomain {
    private static final long serialVersionUID = -4849587983674256999L;

    private String name = null; // 名称
    
	private Integer value = 0; // 值
	
    private Timestamp createTime = null; // 创建时间
    
    private Timestamp updateTime = null; // 更新时间
    
	// --------------------------------------------------------------------------------
	// 构造器
	// --------------------------------------------------------------------------------
	
	/**
	 * 默认构造器。
	 */
	public Sequence() {
		super();
	}
	
	// --------------------------------------------------------------------------------
	// getter 和 setter 方法
	// --------------------------------------------------------------------------------
	
	/**
	 * 获取属性 <TT>name</TT>（名称）的值。
	 * @return <TT>name</TT> 名称。
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置属性 <TT>name</TT>（名称）的值。
	 * @param name 名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取属性 <TT>value</TT>（值）的值。
	 * @return <TT>value</TT> 值。
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * 设置属性 <TT>value</TT>（值）的值。
	 * @param value 值。
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/**
     * 获取属性 <TT>createTime</TT>（创建时间）的值。
     * @return <TT>createTime</TT> 创建时间。
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * 设置属性 <TT>createTime</TT>（创建时间）的值。
     * @param createTime 创建时间。
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取属性 <TT>updateTime</TT>（更新时间）的值。
     * @return <TT>updateTime</TT> 更新时间。
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置属性 <TT>updateTime</TT>（更新时间）的值。
     * @param updateTime 更新时间。
     */
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
    
}
