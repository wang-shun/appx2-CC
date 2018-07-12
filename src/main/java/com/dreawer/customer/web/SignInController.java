package com.dreawer.customer.web;

import static com.dreawer.customer.constants.ControllerConstants.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.dreawer.customer.domain.User;
import com.dreawer.customer.service.UserService;
import com.dreawer.customer.utils.MD5Utils;
import com.dreawer.customer.web.form.BaseLoginForm;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.dreawer.responsecode.rcdt.ResponseCodeRepository;
import com.dreawer.responsecode.rcdt.RuleError;
import com.dreawer.responsecode.rcdt.Success;

@RestController
public class SignInController extends BaseController {
	
    @Autowired
	private UserService userService; // 用户信息服务
    
    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器
	

	/**
	 * 请求登录功能。
	 * @param req 用户请求。
	 * @param resp 服务器响应。
	 */
	@RequestMapping(REQ_LOGIN)
	public void login(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String url = req.getQueryString();
			if(StringUtils.isNotBlank(url)&&url.startsWith("url=")){
				url = url.substring(4);
				req.getSession().setAttribute(REQ_URL, url);
			}
			// 进入登录授权流程
			resp.sendRedirect(REQ_LOGIN_PAGE);
		} catch (Exception e) {
            logger.error(e);
		}
	}
	
    /**
     * 通用登录。
     * @param req 用户请求。
     * @param form 登录校验表单。
     * @param result 表单校验结果。
     * @return
     */
    @RequestMapping(value=REQ_LOGIN_COMMON, method=RequestMethod.POST)
    public ResponseCode loginByEmail(HttpServletRequest req, 
    		@Valid BaseLoginForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
		try {
			
			// 检查应用是否存在
        	String mediaResult = httpGet("https://account.dreawer.com/app/detail", "appid="+form.getAppid());
			if(StringUtils.isBlank(mediaResult)){
				return Error.EXT_REQUEST("app");
			}
			
			JSONObject appJson = new JSONObject(mediaResult);
			if(!appJson.getBoolean("status")){
				return RuleError.NON_EXISTENT("app"); 
			}
            String appId = appJson.getJSONObject("data").getString("id");

            String flag = null;
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	        Pattern regex = Pattern.compile(check);
	        Matcher matcher = regex.matcher(form.getLoginName());
	        if(matcher.matches()){
	        	flag = "email";
	        }else{
	        	check = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";
	 	        regex = Pattern.compile(check);
	 	        matcher = regex.matcher(form.getLoginName());
	 	        if(matcher.matches()){
	 	        	flag = "phone";
	 	        }
	        }
	        
	        User user = null;
	        if("email".equals(flag)){
				user = userService.findUserByEmail(form.getLoginName(), appId);
	        }else if("phone".equals(flag)){
				user = userService.findUserByPhone(form.getLoginName(), appId);
	        }else{
				return Error.BUSINESS("loginName");
	        }
			if(user==null){
				return Error.BUSINESS("user");
			}
			if(!MD5Utils.encrypt(form.getPassword(), "dreawer").equals(user.getPassword())){
				return Error.BUSINESS("password");
			}
        	return Success.SUCCESS(signInUser(req, user));
		}catch(Exception e){
			 e.printStackTrace();
	         logger.error(e);
	         return Error.APPSERVER;
		}
    }
    
}
