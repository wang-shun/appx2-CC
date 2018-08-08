package com.dreawer.customer.form;

import org.hibernate.validator.constraints.NotEmpty;

public class EditUserInfoForm {

	  @NotEmpty(message="EntryError.EMPTY")
	  private String petName = null;

	  @NotEmpty(message="EntryError.EMPTY")
	  private String mugshot = null;

	  @NotEmpty(message="EntryError.EMPTY")
	  private String appid = null;

	  //@NotEmpty(message="EntryError.EMPTY")
	  private String openid = null;
	  
	  public String getPetName()
	  {
	    return this.petName;
	  }

	  public void setPetName(String paramString)
	  {
	    this.petName = paramString;
	  }

	  public String getMugshot()
	  {
	    return this.mugshot;
	  }

	  public void setMugshot(String paramString)
	  {
	    this.mugshot = paramString;
	  }

	  public String getAppid()
	  {
	    return this.appid;
	  }

	  public void setAppid(String paramString)
	  {
	    this.appid = paramString;
	  }

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	  
	  
}
