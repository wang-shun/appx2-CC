package com.dreawer.customer.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

import static com.dreawer.customer.MessageConstants.*;


/**
 * <CODE>AddDecorationServiceForm</CODE> 添加服务表单。
 * @author kael
 * @since Dreawer 2.0
 * @version 1.0
 */
public class AddMemberForm {
    
	@NotEmpty(message=VAL_MEMBER_PHONE_NOTEMPTY)
	@Pattern(regexp="^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$", message=VAL_MEMBER_PHONE_NOTEMPTY)
    private String phoneNumber  = null; // 电话号码
    
	@NotEmpty(message=VAL_MEMBER_NICK_NAME_NOTEMPTY)
	@Length(min=1,max=100,message=VAL_MEMBER_NICK_NAME_NOTEMPTY)
    private String nickName  = null; // 昵称
	
	@NotEmpty(message=VAL_MEMBER_USER_NAME_NOTEMPTY)
	@Length(min=1,max=100,message=VAL_MEMBER_USER_NAME_NOTEMPTY)
    private String userName = null; //用户名称

    // --------------------------------------------------------------------------------
    // getter 和 setter 方法
    // --------------------------------------------------------------------------------
    
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
