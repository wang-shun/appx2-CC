package com.dreawer.customer.lang.record;

/**
 * 会员成长值类型
 */
public enum Type {

    /**
     * 消费获取
     **/
    PURCHASE,

    /**
     * 到期扣减
     **/
    EXPIRE;


    /**
     * 获取成长值类型
     *
     * @param name
     * @return 枚举对象
     */
    public static Type get(String name) {
        for (Type type : Type.values()) {
            if (type.toString().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
