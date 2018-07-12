package com.dreawer.customer.lang;

import java.io.Serializable;

public enum UserStatus implements Serializable  {
    
    /** 已签约 */
    SIGNUPED,
    
    /** 已激活 */
    ACTIVATED,
    
    /** 已冻结 */
    BLOCKED,
    
    /** 黑名单 */
    BLACKED,
    
    /** 已删除 */
    DELETED;

}
