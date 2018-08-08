package com.dreawer.customer.lang.member;

/**
 * 会员有效期限
 */
public enum Expiration {

    /**
     * 不限期
     **/
    UNLIMITED,

    /**
     * 限期
     **/
    LIMITED;


    /**
     * 获取有效期限
     *
     * @param name
     * @return 枚举对象
     */
    public static Expiration get(String name) {
        for (Expiration expiration : Expiration.values()) {
            if (expiration.toString().equalsIgnoreCase(name)) {
                return expiration;
            }
        }
        return null;
    }


}
