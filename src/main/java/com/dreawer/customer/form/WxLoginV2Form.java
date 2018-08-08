package com.dreawer.customer.form;

import org.hibernate.validator.constraints.NotEmpty;

public class WxLoginV2Form {
	
	  @NotEmpty(message="EntryError.EMPTY")
	  private String code = null;

	  @NotEmpty(message="EntryError.EMPTY")
	  private String appid = null;

	  public String getCode()
	  {
	    return this.code;
	  }

	  public void setCode(String paramString)
	  {
	    this.code = paramString;
	  }

	  public String getAppid()
	  {
	    return this.appid;
	  }

	  public void setAppid(String paramString)
	  {
	    this.appid = paramString;
	  }
}
