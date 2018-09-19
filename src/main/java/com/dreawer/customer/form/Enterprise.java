package com.dreawer.customer.form;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Enterprise {

    private String id = null;

	private static final long serialVersionUID = 8940480431090127598L;

	private String appid = null;
	
	private String category = null;

	private String name = null;

	private String appName = null;

    private String businessLicense = null;

    private String certificate = null;

    private String certType = null;
	
	private String logo = null;
	
	private String intro = null;
	
	private String coordinate = null; // 位置坐标

	private String province = null;
	
	private String city = null;
	
	private String area = null;

	private String detail = null;
	
	private String email = null;

	private String phone = null;

	private String mobile = null;

	private String bootImage = null;

	private Integer bootTime = null;

	private String posters = null;

	private String telephone = null;

	private String merchanCategories = null;

	private Boolean memberRegisterPort = true; //是否在商品详情显示会员注册通道

	private Boolean memberDisplay = true; //非会员是否显示会员权益

	private String growthRules = null; //会员成长值规则JSON

	private String url = null;

	private String userId = null; // 创建者
    
    private Timestamp createTime = null; // 创建时间

    private Timestamp updateTime = null; // 更新时间
	

}
