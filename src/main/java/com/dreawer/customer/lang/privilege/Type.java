package com.dreawer.customer.lang.privilege;

/**
 * 会员特权类型
 */
public enum Type {

    /**
     * 包邮
     **/
    FREESHIPPING,

    /**
     * 会员折扣
     **/
    DISCOUNT;


    /**
     * 获取会员特权类型
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
