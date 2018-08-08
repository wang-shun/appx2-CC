package com.dreawer.customer.form;

import com.dreawer.customer.lang.MemberRankExpiration;
import com.dreawer.customer.lang.MemberRankStatus;

import java.math.BigDecimal;

/**
 * <CODE>AddMemberRankForm</CODE> 添加会员等级表单。
 * @author kael
 * @since Dreawer 2.0
 * @version 1.0
 */
public class EditMemberRankForm {
	
	private String id = null; //会员等级ID
	
	private String storeId = null; //店铺ID
	
	private String name = null; // 等级名称
	
	private Integer growthValue = null; // 成长值 
	
    private Boolean freeShipping  = false; // 有无包邮权益
    
    private Boolean discount  = false; // 有无会员折扣
	
    private BigDecimal discountAmount  = null; // 折扣
	
    private MemberRankExpiration expiration  = null; // 有效期类型
	
    private Integer period = null; // 有效期
	
    private Integer expireDeduction = null; // 过期后扣减成长值
    
    private MemberRankStatus status = null; //状态

	private String userId = null;

    // --------------------------------------------------------------------------------
    // getter 和 setter 方法
    // --------------------------------------------------------------------------------


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
    
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGrowthValue() {
		return growthValue;
	}

	public void setGrowthValue(Integer growthValue) {
		this.growthValue = growthValue;
	}

	public Boolean getFreeShipping() {
		return freeShipping;
	}

	public void setFreeShipping(Boolean freeShipping) {
		this.freeShipping = freeShipping;
	}

	public Boolean getDiscount() {
		return discount;
	}

	public void setDiscount(Boolean discount) {
		this.discount = discount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public MemberRankExpiration getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = MemberRankExpiration.get(expiration);
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getExpireDeduction() {
		return expireDeduction;
	}

	public void setExpireDeduction(Integer expireDeduction) {
		this.expireDeduction = expireDeduction;
	}

	public MemberRankStatus getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = MemberRankStatus.get(status);
	}

	public void setExpiration(MemberRankExpiration expiration) {
		this.expiration = expiration;
	}

	public void setStatus(MemberRankStatus status) {
		this.status = status;
	}
}
