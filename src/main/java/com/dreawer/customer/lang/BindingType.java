package com.dreawer.customer.lang;

import java.io.Serializable;

public enum BindingType implements Serializable {
	
	/** qq */
    QQ,
    
    /** 微信 */
    WEIXIN,
    
    /** 微信小程序 */
    WXAPP,
    
    /** 微博 */
    WEIBO,
    
    /** github */
    GITHUB,
    
    /** 微信公众号 */
    WXMP;

}
