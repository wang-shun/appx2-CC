package com.dreawer.customer;

/**
 * <CODE>RequestConstants</CODE> 控制器层常量类。
 * 该类用于定义“请求链接”和“页面地址”的代码规范性常量，以统一工程中有关于对象、属性名称的代码规范。
 * @author David Dai
 * @since Dreawer 1.0
 * @version 1.0
 */
public final class ControllerConstants {
    
    /**
     * 私有构造器。
     */
    private ControllerConstants() {
    }
    
    // --------------------------------------------------------------------------------
    // 控制器
    // --------------------------------------------------------------------------------

    /** 类目控制器 */
    public static final String MEMBER_CONTROLLER = "categoryController";
    
    /** 类目控制器 */
    public static final String REQ_MEMBER = "/member";
    
    /** 类目控制器 */
    public static final String REQ_ADD = "/add";
    
    /** 类目控制器 */
    public static final String REQ_QUERY = "/query";


    /** 请求“会员等级” */
    public static final String REQ_MEMBER_RANK = "/member/rank";


    /** 请求“修改” */
    public static final String REQ_EDIT = "/edit";

    /** 请求“更新状态” */
    public static final String REQ_UPDATE_STATUS = "/updateStatus";

    /** 请求“列表” */
    public static final String REQ_LIST = "/list";

    /** 请求“添加成长值” */
    public static final String REQ_POINT_RECORD_ADD = "/pointRecord/add";

    /** 请求“详情” */
    public static final String REQ_DETAIL = "/detail";


    /** 请求“折扣” */
    public static final String REQ_DISCOUNT = "/discount";

    /** 请求“注册” */
    public static final String REQ_REGISTER = "/register";

    /** 请求“成长值记录” */
    public static final String REQ_POINT_RECORD = "/pointRecord";

    /** 请求“任务查询” */
    public static final String REQ_TASK_QUERY = "/taskQuery";

    /** 请求“校验” */
    public static final String REQ_CHECK = "/check";


    // --------------------------------------------------------------------------------
    // 其他常量
    // --------------------------------------------------------------------------------
    
    /** 用户请求 URL */
    public static final String REQ_URL = "requestUrl";
    
    /** 错误信息 */
    public static final String ERRORS = "errors";
    
    /** 错误信息 */
    public static final String ERROR = "error";
    
}
