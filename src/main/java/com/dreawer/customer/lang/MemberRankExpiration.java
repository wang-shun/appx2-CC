package com.dreawer.customer.lang;

public enum  MemberRankExpiration {

    /** 有限制 **/
	LIMITED,

    /** 无限制 **/
	UNLIMITED;


    /**
     * 获取有效期类型。
     * @param name 类型名称。
     * @return 枚举对象。
     * @since 1.0
     */
    public static MemberRankExpiration get(String name) {
        for (MemberRankExpiration expiration : MemberRankExpiration.values()) {
            if (expiration.toString().equalsIgnoreCase(name)) {
                return expiration;
            }
        }
        return null;
    }
}
