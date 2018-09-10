package com.dreawer.customer.form;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <CODE>AddDecorationServiceForm</CODE>
 * @author kael
 * @since Dreawer 2.0
 * @version 1.0
 */
@ApiModel(value = "编辑会员表单")
public class EditMemberForm {

	@ApiModelProperty(name = "storeId",value = "店铺ID",dataType = "String")
	private String storeId = null; //店铺ID

	@ApiModelProperty(name = "phoneNumber",value = "电话号码",dataType = "String")
	private String phoneNumber  = null; // 电话号码

	@ApiModelProperty(name = "sex",value = "性别",dataType = "Integer",notes = "0-女性，1-男性，2-未知）")
	private Integer sex  = null; // 性别（0-女性，1-男性，2-未知）

	@ApiModelProperty(name = "userName",value = "用户名称",dataType = "String")
	private String userName = null; //用户名称

	@ApiModelProperty(name = "birthday",value = "生日",dataType = "String",notes = "时间戳")
	private String birthday = null; //生日（时间戳）

    // --------------------------------------------------------------------------------
    // getter 和 setter 方法
    // --------------------------------------------------------------------------------

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
}
