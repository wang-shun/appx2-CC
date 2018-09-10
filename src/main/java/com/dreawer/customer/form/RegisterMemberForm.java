package com.dreawer.customer.form;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <CODE>AddDecorationServiceForm</CODE> 添加服务表单。
 * @author kael
 * @since Dreawer 2.0
 * @version 1.0
 */
@ApiModel(value = "注册会员表单")
public class RegisterMemberForm {

	@ApiModelProperty(value = "店铺ID",dataType = "String",required = true,notes = "店铺ID")
	private String storeId = null; //店铺ID

	@ApiModelProperty(value = "电话号码",dataType = "String",required = true)
	private String phoneNumber  = null; // 电话号码

	@ApiModelProperty(value = "性别",dataType = "Integer",required = true,notes = "（0-女性，1-男性，2-未知）")
	private Integer sex = null; // 性别（0-女性，1-男性，2-未知）

	@ApiModelProperty(value = "用户名称",dataType = "String",required = true)
	private String userName = null; //用户名称

	@ApiModelProperty(value = "生日",dataType = "String",required = true,notes = "时间戳")
	private String birthday = null; //生日（时间戳）

	@ApiModelProperty(value = "头像",dataType = "String",required = true)
	private String mugshot = null; //头像

	@ApiModelProperty(value = "昵称",dataType = "String",required = true)
    private String nickName = null; //昵称


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

	public String getMugshot() {
		return mugshot;
	}

	public void setMugshot(String mugshot) {
		this.mugshot = mugshot;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


}
