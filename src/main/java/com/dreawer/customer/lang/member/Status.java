package com.dreawer.customer.lang.member;

/**
 * 会员等级状态
 */
public enum Status {

    /**
     * 启用
     **/
    ENABLE,

    /**
     * 处理中
     **/
    SUSPEND,

    /**
     * 禁用
     **/
    DISABLE;


    /**
     * 获取会员等级状态
     *
     * @param name
     * @return 枚举对象
     */
    public static Status get(String name) {
        for (Status status : Status.values()) {
            if (status.toString().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
