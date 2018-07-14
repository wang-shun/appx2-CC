package com.dreawer.customer.constants;


public final class ControllerConstants {
    
    /**
     * 私有构造器。
     */
    private ControllerConstants() {
    	
    }
    
    // --------------------------------------------------------------------------------
    // 请求地址
    // --------------------------------------------------------------------------------

    /** 转向（释放原有参数） */
    public static final String REDIRECT = "redirect:";
    
    /** 跳转（不释放原有参数） */
    public static final String FORWARD = "forward:";
    
    /** 用户登录 */
    public static final String REQ_LOGIN = "/login";
    
    /** 用户登录页面 */
    public static final String REQ_LOGIN_PAGE = "/login.html";
    
    /** QQ用户请求授权 */
    public static final String REQ_AUTH_QQ = "/authorize/qq";
    
    /** 微信用户请求授权 */
    public static final String REQ_AUTH_WX = "/authorize/wx";
    
    /** 微博用户请求授权 */
    public static final String REQ_AUTH_WB = "/authorize/wb";
    
    /** github用户请求授权 */
    public static final String REQ_AUTH_GIT = "/authorize/git";
    
    /** 请求公众号授权 */
    public static final String REQ_AUTH_WXMP = "/authorize/wxmp";
    
    /** 请求第三方平台授权小程序 */
    public static final String REQ_AUTH_WXAPP = "/authorize/thirdPart/wxapp";
    
    /** 请求第三方平台授权公众号 */
    public static final String REQ_AUTH_THIRD = "/authorize/thirdPart/mp";
    
    /** QQ用户登录回调 */
    public static final String REQ_LOGIN_QQ = "/login/qq";

    /** 微信用户登录回调转发器 */
    public static final String REQ_LOGIN_WXFORWARD = "/login/wxforward";
    
    /** 微信用户登录回调 */
    public static final String REQ_LOGIN_WX = "/login/wx";
    
    /** 微博用户登录回调 */
    public static final String REQ_LOGIN_WB = "/login/wb";
    
    /** github用户登录回调 */
    public static final String REQ_LOGIN_GIT = "/login/git";
    
    /** 请求“公众号登录回调” */
    public static final String REQ_LOGIN_WXMP = "/login/wxmp";
    
    /** 请求微信小程序登录 */
    public static final String REQ_LOGIN_WXAPP = "/login/wxapp";
    
    /** 请求用户名登录 */
    public static final String REQ_LOGIN_USERNAME= "/login/username";
    
    /** 请求统一登录 */
    public static final String REQ_LOGIN_COMMON= "/login/common";
    
    /** 第三方登陆绑定 */
    public static final String REQ_SNS_BINDING = "/sns/binding";
    
    /** 第三方登陆解除绑定 */
    public static final String REQ_SNS_UNBINDING = "/sns/unbinding";
    
    /** 请求公众号验证token */
    public static final String REQ_MP_CHECK= "/mp/check";
    
    /** 请求媒体名称是否存在 */
    public static final String REQ_MP_CHECK_NAME= "/mp/checkName";
    
    /** 请求媒体信息详情 */
    public static final String REQ_MEDIA_DETAIL= "/media/{id}";
    
    /** 请求修改媒体信息 */
    public static final String REQ_MEDIA_UPDATEBASIC= "/media/updateBasic";
    
    /** 修改用户密码 */
    public static final String REQ_SET_PASSWORD = "/settings/password";
    
    /** 请求“设置用户基本信息” */
    public static final String REQ_SET_BASIC = "/settings/setBasic";
    
    /** 请求“设置用户邮箱” */
    public static final String REQ_SET_EMAIL = "/settings/setEmail";
    
    /** 请求“设置用户手机号” */
    public static final String REQ_SET_PHONE = "/settings/setPhone";
    
    /** 请求“设置管理员” */
    public static final String REQ_SET_ADMIN = "/settings/setAdmin";
    
    /** 请求“删除管理员” */
    public static final String REQ_REMOVE_ADMIN = "/settings/removeAdmin";
    
    /** 请求“查询用户” */
    public static final String REQ_SEARCH_USER = "/user/searchByPetName";
    
    /** 请求“查看管理员列表” */
    public static final String REQ_GET_ADMIN = "/user/getAdmins";
    
    /** 请求“添加管理员” */
    public static final String REQ_ADD_ADMIN = "/user/addAdmin";
    
    /** 获取用户信息 */
    public static final String REQ_USERINFO = "/user/getUserInfo";
    
    /** 获取用户身份信息 */
    public static final String REQ_IDENTITIES = "/user/getIdentities";
    
    /** 用户获取令牌 */
    public static final String REQ_GETTOKEN = "/user/getToken";
    
    /** 请求第三方回调 */
    public static final String REQ_CALLBACK_MP= "/thirdPart/callback/mp";
    
    /** 请求第三方回调 */
    public static final String REQ_CALLBACK_WXAPP= "/thirdPart/callback/wxapp";
    
    /** 请求刷新令牌 */
    public static final String REQ_REFRESH_TOKEN= "/thirdPart/refreshToken";
    
    /** 接受微信服务器推送的授权信息 */
    public static final String REQ_EVENT_AUTH= "/event/authorize";
    
    /** 接受微信服务器推送的公众号消息 */
    public static final String REQ_WXMP_MESSAGE= "/event/{appid}/messageCallback";
    
    /** 请求“登出” */
    public static final String REQ_SIGNOUT = "/signout";
    
    /** 用户未登录 */
    public static final String REQ_UNLOGIN = "/unlogin";
    
    /** 请求“通知” */
    public static final String REQ_NOTIFICATION = "/notification";
    
    /** 请求“添加” */
    public static final String REQ_ADD = "/add";
    
    /** 请求“批量添加” */
    public static final String REQ_ADD_BATCH = "/addBatch";
    
    /** 请求“删除” */
    public static final String REQ_DELETE = "/delete";
    
    /** 请求“更新” */
    public static final String REQ_UPDATE = "/update";
    
    /** 请求“被通知者更新” */
    public static final String REQ_NOTIFIED_UPDATE = "/notifiedUpdate";
    
    /** 请求“列表” */
    public static final String REQ_LIST = "/list";
    
    /** 请求“详情” */
    public static final String REQ_DETAIL = "/detail";
    
    /** 请求“总数” */
    public static final String REQ_COUNT = "/count";
    
    /** 用户邮箱注册 */
    public static final String REQ_SIGNUP_EMAIL = "/signup/email";
    
    /** 用户手机注册 */
    public static final String REQ_SIGNUP_PHONE = "/signup/phone";
    
    /** 用户验证邮箱*/
    public static final String REQ_VERIFY_EMAIL = "/verify/email";
    
    /** 用户验证手机*/
    public static final String REQ_VERIFY_PHONE = "/verify/phone";
    
    /** 用户忘记密码*/
    public static final String REQ_PASSWORD_FORGET = "/password/forget";
    
    /** 用户重设密码*/
    public static final String REQ_PASSWORD_RESET = "/password/reset";
    
    /** 用户登录名是否存在 */
    public static final String REQ_SIGNUP_USER_EXISTS = "/signup/isUserExists";
    
    /** 用户验证手机*/
    public static final String REQ_VERIFY_CAPTCHA_COMMEN = "/verify/captcha/commen";
    
    // --------------------------------------------------------------------------------
    // 常量信息
    // --------------------------------------------------------------------------------

    /** 用户请求 URL */
    public static final String REQ_URL = "requestUrl";
    
    /** 自动登录秘钥Cookie名 */
    public static final String REMEMBER_KEY = "rmbr";
    
    /** 短信应用key*/
    public static final String SMS_API_KEY = "LTAIZYvr8vfCa2vq";
    
    /** 短信应用密钥*/
    public static final String SMS_API_SECRET = "grkdCQhvWOCvcgjzGQdHdSVD35iRqY";
    
    /** sendCloud用户名*/
    public static final String SENDCLOUD_API_USER = "dreawer";
    
    /** sendCloud 密钥*/
    public static final String SENDCLOUD_API_KEY = "Nfh69HzHLtp82Qyl";
    
    /** 极乐发件账号*/
    public static final String SENDCLOUD_USER = "server@service.dreawer.com";
    
    /** 极乐发件用户名*/
    public static final String SENDCLOUD_SEND_NAME = "dreawer";
    
    /** 收件模板*/
    public static final String RECEIVE_TEMPLATE = "receiveTemplate";
    
    /** API_USER*/
    public static final String API_USER = "apiUser";
    
    /** 极乐发件账号类型*/
    public static final String SENDCLOUD_USERTYPE = "userType";
    
    /** 收件人邮箱*/
    public static final String RECEIVE_USER = "receiveUser";
    
    /** 极乐api用户名*/
    public static final String SENDCLOUD_USERNAME = "dreawer_test";
    
    /** 极乐_用户激活邮件*/
    public static final String SENDCLOUD_USER_ACTIVE = "account_user_activity";
    
    /** 极乐_用户重设密码*/
    public static final String SENDCLOUD_RESET_PASSWORD = "account_reset_password";
    
    /** 极乐_用户重设邮箱*/
    public static final String SENDCLOUD_RESET_EMAIL = "account_reset_email";
    
}
