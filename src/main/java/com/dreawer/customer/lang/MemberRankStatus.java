package com.dreawer.customer.lang;

public enum  MemberRankStatus {

    /** 启用 **/
	ENABLE,

    /** 不启用 **/
	DISABLE;


    /**
     * 获取会员等级状态。
     * @param name 状态名称。
     * @return 枚举对象。
     * @since 1.0
     */
    public static MemberRankStatus get(String name) {
        for (MemberRankStatus status : MemberRankStatus.values()) {
            if (status.toString().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
