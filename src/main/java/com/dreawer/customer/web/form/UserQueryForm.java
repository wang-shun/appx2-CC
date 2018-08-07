package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class UserQueryForm {

	@NotEmpty(message="EntryError.EMPTY")
	private String appId = null;
	
	private Long startTime = null;
	
	private Long endTime = null;

	private String query = null;
	
	private Integer pageNo = null;
	
	private Integer pageSize = null;

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}
