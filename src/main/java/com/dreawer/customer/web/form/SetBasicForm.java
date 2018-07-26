package com.dreawer.customer.web.form;

import org.hibernate.validator.constraints.Length;

public class SetBasicForm {

	//@NotEmpty(message="EntryError.EMPTY")
	@Length(min=2, max=20, message="EntryError.OVERRANGE")
	private String petName = null; // 昵称
	
	//@NotEmpty(message="EntryError.EMPTY")
	@Length(min=8, max=50, message="EntryError.OVERRANGE")
    String slogan = null; // 宣传语（品牌口号）
	
	@Length(min=2, max=250, message="EntryError.OVERRANGE")
	private String mugshot = null; // 头像
    
    /**
     * 获取属性 <TT>petName</TT>（昵称）的值。
     * @return <TT>petName</TT> 昵称。
     */
    public String getPetName() {
        return petName;
    }

    /**
     * 设置属性 <TT>petName</TT>（昵称）的值。
     * @param <TT>petName</TT> 昵称。
     */
    public void setPetName(String petName) {
        this.petName = petName;
    }

    /**
     * 获取 <TT>slogan</TT>（宣传语）的值。
     * @return <TT>slogan</TT> 宣传语。
     */
    public String getSlogan() {
        return slogan;
    }

    /**
     * 设置 <TT>slogan</TT>（宣传语）的值。<br/>
     * 它可以是：个人品牌宣传口号，或企业、团队、产品品牌的宣传口号
     * @param slogan 宣传语。
     */
    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

	public String getMugshot() {
		return mugshot;
	}

	public void setMugshot(String mugshot) {
		this.mugshot = mugshot;
	}


}
