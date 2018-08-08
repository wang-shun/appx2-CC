package com.dreawer.customer.form;

import com.dreawer.customer.lang.MemberRankStatus;

/**
 * <CODE>AddMemberRankForm</CODE> 添加会员等级表单。
 * @author kael
 * @since Dreawer 2.0
 * @version 1.0
 */
public class UpdateMemberRankStatusForm {
	
	private String id = null; //会员等级ID
	
	private String storeId = null; //店铺ID
	
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

	public MemberRankStatus getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = MemberRankStatus.get(status);
	}
    
}
