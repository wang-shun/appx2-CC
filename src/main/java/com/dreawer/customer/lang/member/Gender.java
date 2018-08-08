package com.dreawer.customer.lang.member;

public enum Gender {

    /**
     * 男
     **/
    MALE,

    /**
     * 女
     **/
    FEMALE;

    /**
     * 获取性别
     *
     * @param name
     * @return 枚举对象
     */
    public static Gender get(String name) {
        for (Gender sex : Gender.values()) {
            if (sex.toString().equalsIgnoreCase(name)) {
                return sex;
            }
        }
        return null;
    }

}
