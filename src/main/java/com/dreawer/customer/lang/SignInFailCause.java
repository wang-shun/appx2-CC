package com.dreawer.customer.lang;

import java.io.Serializable;

/**
 * <CODE>SignInFailCause</CODE> 登录失败的原因。
 * @author David Dai
 * @since Dreawer 1.0
 * @version 1.0
 */
public enum SignInFailCause implements Serializable {

    /** 用户未注册 */
    ERROR_USERNAME,
    
    /** 密码错误 */
    ERROR_PASSWORD;
    
}
