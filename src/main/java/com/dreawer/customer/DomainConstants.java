package com.dreawer.customer;


/**
 * <CODE>DAOConstants</CODE> 领域模型常量。
 * 该类用于定义“领域对象”和“对象属性”的代码规范性常量，以统一工程中有关于对象、属性名称的代码规范。
 * @author David Dai
 * @since Dreawer 1.0
 * @version 2.0
 */
public abstract class DomainConstants {
    
    // --------------------------------------------------------------------------------
    // 对象
    // --------------------------------------------------------------------------------
    
	/** 会员 */
    public static final String MEMBER = "member";
	
	/** 会员等级 */
    public static final String MEMBER_RANK = "memberRank";
    
	/** 数据 */
    public static final String DATA = "data";
    
    /** 用户 */
    public static final String USER = "user";
    
    // --------------------------------------------------------------------------------
    // 属性
    // --------------------------------------------------------------------------------
    
    /** 店铺ID */
    public static final String STORE_ID = "storeId";

    /** 类型 */
    public static final String TYPE = "type";

    /** 来源 */
    public static final String SOURCE = "source";

    /** 成长值 */
    public static final String VALUE = "value";
    
    /** 名称 */
    public static final String NAME = "name";
    
    /** 成长值 */
    public static final String GROWTH_VALUE = "growthValue";
    
    /** 包邮 */
    public static final String FREE_SHIPPING = "freeShipping";
    
    /** 折扣 */
    public static final String DISCOUNT = "discount";
    
    /** 折扣（额度） */
    public static final String DISCOUNT_AMOUNT = "discountAmount";
    
    /** 有效期类型 */
    public static final String EXPIRATION = "expiration";
    
    /** 有效期 */
    public static final String PERIOD = "period";
    
    /** 过期后扣减成长值 */
    public static final String EXPIRE_DEDUCTION = "expireDeduction";
    
    /** 状态 */
    public static final String STATUS = "status";
    
    /** ID号 */
    public static final String ID = "id";
    
    /** 终端类型 */
    public static final String TERMINAL_TYPE = "terminalType";
    
    /** 用户名称 */
    public static final String USER_NAME = "userName";
    
    /** 头像 */
    public static final String MUGSHOT = "mugshot";
    
    /** 性别 */
    public static final String SEX = "sex";
    
    /** 手机号码 */
    public static final String PHONE_NUMBER = "phoneNumber";
    
    /** 手机号码 */
    public static final String CAPTCHA = "captcha";
    
    /** 等级ID */
    public static final String RANK_ID = "rankId";
    
    /** 用户名称 */
    public static final String BIRTHDAY = "birthday";
    
    /** 用户ID */
    public static final String USER_ID = "userId";
    
    /** 昵称 */
    public static final String NICK_NAME = "nickName";
    
    /** 第三方平台类型 */
    public static final String APP_TYPE = "appType";
    
    /** 任务ID */
    public static final String TASK_ID = "taskId";
    
    // --------------------------------------------------------------------------------
    // 其他
    // --------------------------------------------------------------------------------
    
    /** 服务版本（1.0） */
    public static final String SERVICE_VERSION_1 = "1.0";
    
    /** 终端类型（微信小程序） */
    public static final String TERMINAL_TYPE_WXAPP = "WXAPP";
    
    /** 终端类型（浏览器） */
    public static final String TERMINAL_TYPE_BROWSER = "BROWSER";
    
    /** 终端应用版本号（1.0） */
    public static final String TERMINAL_APP_VERSION_1 = "1.0";
    
    /** 客户端应用号（member-web） */
    public static final String CLIENT_APP_ID_MEMBER_WEB = "member-web";
    
    /** 客户端应用版本号（1.0） */
    public static final String CLIENT_APP_VERSION_1 = "1.0";
    
    /** 第三方用户登记 */
    public static final String MEMS_0001 = "mems-0001";
    
    /** 添加新客户 */
    public static final String MEMS_0002 = "mems-0002";
    
    /** 会员注册 */
    public static final String MEMS_0003 = "mems-0003";
    
    /** 添加收货地址 */
    public static final String MEMS_0004 = "mems-0004";
    
    /** 添加商家 */
    public static final String MEMS_0005 = "mems-0005";
    
    /** 修改商家名称、会员控制开关 */
    public static final String MEMS_0006 = "mems-0006";
    
    /** 添加会员等级 */
    public static final String MEMS_0007 = "mems-0007";
    
    /** 启用、禁用会员等级 */
    public static final String MEMS_0008 = "mems-0008";
    
    /** 修改会员等级 */
    public static final String MEMS_0009 = "mems-0009";
    
    /** 增加会员成长值 */
    public static final String MEMS_0010 = "mems-0010";
    
    /** 客户、会员信息列表 */
    public static final String MEMS_0011 = "mems-0011";
    
    /** 客户、会员信息详情 */
    public static final String MEMS_0012 = "mems-0012";
    
    /** 客户收货地址信息列表 */
    public static final String MEMS_0013 = "mems-0013";
    
    /** 客户切换常用收货地址 */
    public static final String MEMS_0014 = "mems-0014";
    
    /** 修改会员信息 */
    public static final String MEMS_0015 = "mems-0015";
    
    /** 查询会员成长值记录 */
    public static final String MEMS_0016 = "mems-0016";
    
    // --------------------------------------------------------------------------------
    // 分页
    // --------------------------------------------------------------------------------
    
    /** 起始页 */
    public static final String PAGE_NO = "pageNo";
    
    /** 每页显示记录数 */
    public static final String PAGE_SIZE = "pageSize";
}
