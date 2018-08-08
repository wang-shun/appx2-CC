package com.dreawer.customer.lang.record;

/**
 * 成长值来源
 */
public enum Source {

    /**
     * 系统
     **/
    SYSTEM;

    /**
     * 获取成长值来源
     *
     * @param name
     * @return 枚举对象
     */
    public static Source get(String name) {
        for (Source source : Source.values()) {
            if (source.toString().equalsIgnoreCase(name)) {
                return source;
            }
        }
        return null;
    }

}
