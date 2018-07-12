package com.dreawer.customer.domain;

import java.io.Serializable;
import java.util.Comparator;

public class Address implements Serializable, Comparator<Address>{

	private static final long serialVersionUID = 1898170246617120678L;
	
	private String id = null;

	private String userId = null; // 用户
	
	private String name = null; // 姓名

	private String phoneNumber = null; // 手机号

	private String province = null; // 省份

	private String provinceName = null; // 省份

    private String city = null; // 城市
    
    private String cityName = null; // 城市

    private String area = null; // 地区
    
    private String areaName = null; // 地区

    private String detail = null; // 详细地址
	
    private String status = null; // 状态
    
    private Long createTime = null; // 创建时间
    
    private Long updateTime = null; // 更新时间

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int compare(Address addr1, Address addr2) {
		if("DEFAULT".equals(addr1.getStatus())){
			return -1;
		}
		if("DEFAULT".equals(addr2.getStatus())){
			return 1;
		}
		if(addr1.getUpdateTime()==null){
			return 1;
		}
		if(addr2.getUpdateTime()==null){
			return -1;
		}
		if(addr1.getUpdateTime()>addr2.getUpdateTime()){
			return -1;
		}else{
			return 1;
		}
	}

}
