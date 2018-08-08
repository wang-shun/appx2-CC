package com.dreawer.customer.domain;

import com.dreawer.domain.BaseDomain;

import java.util.Date;

public class Sequence extends BaseDomain {
    private static final long serialVersionUID = -4849587983674256999L;

    private String name = null; // 名称
    
	private Integer value = 0; // 值
	
    private Date createTime = null; // 创建时间
    
    private Date updateTime = null; // 更新时间
    
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

}
