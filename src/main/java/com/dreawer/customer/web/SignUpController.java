package com.dreawer.customer.web;

import static com.dreawer.customer.constants.ControllerConstants.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.dreawer.customer.domain.Customer;
import com.dreawer.customer.domain.Organize;
import com.dreawer.customer.domain.TokenUser;
import com.dreawer.customer.domain.User;
import com.dreawer.customer.lang.UserStatus;
import com.dreawer.customer.lang.VerifyType;
import com.dreawer.customer.service.OrganizeService;
import com.dreawer.customer.service.TokenUserService;
import com.dreawer.customer.service.UserService;
import com.dreawer.customer.utils.MD5Utils;
import com.dreawer.customer.web.form.EmailBaseForm;
import com.dreawer.customer.web.form.PhoneBaseForm;
import com.dreawer.customer.web.form.PhoneSignUpForm;
import com.dreawer.customer.web.form.ResetPasswordForm;
import com.dreawer.customer.web.form.SetPhoneForm;
import com.dreawer.customer.web.form.VerifyForm;
import com.dreawer.responsecode.rcdt.EntryError;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.dreawer.responsecode.rcdt.ResponseCodeRepository;
import com.dreawer.responsecode.rcdt.Success;

import io.jstack.sendcloud4j.mail.Result;

@RestController
public class SignUpController extends BaseController {
	
    @Autowired
	private UserService userService; // 用户信息服务

    @Autowired
    private TokenUserService tokenUserService; // 用户信息服务
    
    @Autowired
    private OrganizeService organizeService; // 用户信息服务

    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器

    /**
     * 用户手机号注册
     * @param req 用户请求。
     * @param form 手机注册表单。
     * @param result 表单校验结果。
     * @return
     */
    @RequestMapping(value=REQ_SIGNUP_PHONE, method=RequestMethod.POST)
    public ResponseCode phoneSignUp(HttpServletRequest req, 
    		@Valid PhoneSignUpForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
		try {
			// 检查组织是否存在
			Organize organize = organizeService.findOrganizeByAppId(form.getAppId());
			if(organize==null) {
				return Error.BUSINESS("appId");
			}
			
			User user = userService.findUserByPhone(form.getPhone(), organize.getAppId());
            if(user!=null){
				return Error.BUSINESS("phone");
            }
            if(isCaptchaValid(form.getPhone(), form.getCaptcha())) {
            	removeCaptcha(form.getPhone());
            }else {
				return Error.BUSINESS("captcha");
            }
            
            user = new User();
            user.setOrganizeId(organize.getId());
            user.setStatus(UserStatus.ACTIVATED);
            user.setPassword(MD5Utils.encrypt(form.getPassword(), "dreawer"));
            user.setPhoneNumber(form.getPhone());
            Customer customer = new Customer();
            customer.setOrganizeId(organize.getId());
            customer.setCategory("person");
            customer.setPetName(form.getPetName());
            customer.setAlias(getAlias(form.getPetName()));
            customer.setCreater(user.getId());
            customer.setPhoneNumber(form.getPhone());
            customer.setStatus(UserStatus.ACTIVATED);
            userService.addUser(user, customer);
        	return Success.SUCCESS;
		}catch(Exception e){
			 e.printStackTrace();
	         logger.error(e);
	         return Error.APPSERVER;
		}
    }
    
    /**
     * 发送验证邮件接口。
     * @param req 用户请求。
     * @return
     */
    @RequestMapping(value=REQ_VERIFY_EMAIL, method=RequestMethod.POST)
    public ResponseCode sendEmail(HttpServletRequest req, HttpServletResponse resp,
    		@Valid EmailBaseForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
		try {
			
			// 检查组织是否存在
			Organize organize = organizeService.findOrganizeByAppId(form.getAppId());
			if(organize==null) {
				return Error.BUSINESS("appId");
			}

	        Map<String, String> params = new HashMap<String, String>();
	        Map<String, String> map = new HashMap<String, String>();

	        TokenUser tokenUser = null;
			if("signup".equals(form.getType())){
		        params.put(RECEIVE_TEMPLATE, SENDCLOUD_USER_ACTIVE);
		        String email = form.getEmail();
		        String name = email.substring(0, email.lastIndexOf("@"));
		        map.put("petName", name);
			}else if("resetpass".equals(form.getType())){
		        params.put(RECEIVE_TEMPLATE, SENDCLOUD_RESET_PASSWORD);
		        tokenUser = tokenUserService.findTokenUserByEmail(form.getEmail(), organize.getId());
		        if(tokenUser==null){
					return Error.BUSINESS("email");
		        }
		        map.put("petName", tokenUser.getPetName());
		    	map.put("photo", tokenUser.getMugshot());
			}else if("resetemail".equals(form.getType())){
		        params.put(RECEIVE_TEMPLATE, SENDCLOUD_RESET_EMAIL);
		        tokenUser = getSignInUser(req);
		        if(tokenUser==null){
		        	req.getRequestDispatcher(REQ_UNLOGIN).forward(req, resp);
		        }
		        map.put("petName", tokenUser.getPetName());
		    	map.put("photo", tokenUser.getMugshot());
			}else{
				return EntryError.EMPTY("email");
			}
			
			// 发送邮件 邮件通道组装参数
	        params.put(RECEIVE_USER, form.getEmail());
	        params.put(API_USER, SENDCLOUD_USERNAME);
	        params.put(SENDCLOUD_USERTYPE, SENDCLOUD_USER);
            
	        map.put("domain", "https://account.dreawer.com");
	        map.put("value", createCaptcha(form.getEmail()));
	        Result emailResult = sendEmailBySendCloud(map, params);
	        if(emailResult.isSuccess()){
	        	return Success.SUCCESS;
	        }else{
		        logger.error(emailResult);
				return Error.EXT_REQUEST("email");
	        }
		}catch(Exception e){
			 e.printStackTrace();
	         logger.error(e);
	         return Error.APPSERVER;
		}
    }
    
    /**
     * 发送验证短信接口。
     * @param req 用户请求。
     * @return
     */
    @RequestMapping(value=REQ_VERIFY_PHONE, method=RequestMethod.POST)
    public ResponseCode sendSMS(HttpServletRequest req, HttpServletResponse resp, 
    		@Valid PhoneBaseForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
		try {
	        TokenUser tokenUser = null;
	        String templateCode = null;
			if("signup".equals(form.getType())){
				templateCode="SMS_116568043";
			}else if("resetphone".equals(form.getType())){
				templateCode="SMS_116593125";
		        tokenUser = getSignInUser(req);
		        if(tokenUser==null){
		        	req.getRequestDispatcher(REQ_UNLOGIN).forward(req, resp);
		        }
			}else if("registermember".equals(form.getType())){
				templateCode="SMS_134314240";
		    }else{
				return Error.BUSINESS("type");
			}
			
			SendSmsResponse resultJson = sendSMSByAliyun(form.getPhone(), templateCode, createCaptcha(form.getPhone()));
    		if(resultJson!=null){
    			if(!"OK".equals(resultJson.getCode())){
        			logger.error("发送短信失败，失败错误代码Code=" + resultJson.getCode() + 
        					"Message=" + resultJson.getMessage()+ "RequestId=" + resultJson.getRequestId());
    				return Error.EXT_REQUEST("sms");
    			}
    		}else{
				return Error.EXT_REQUEST("sms");
    		}
        	return Success.SUCCESS;
		}catch(Exception e){
			 e.printStackTrace();
	         logger.error(e);
	         return Error.APPSERVER;
		}
    }
    
    /**
     * 重新设置密码。
     * @param req 用户请求。
     * @param form 设置密码表单。
     * @param result 表单校验结果。
     * @return
     */
    @RequestMapping(value=REQ_PASSWORD_RESET, method=RequestMethod.POST)
    public ResponseCode reset(HttpServletRequest req, 
    		@Valid ResetPasswordForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
		try {
			// 检查组织是否存在
			Organize organize = organizeService.findOrganizeByAppId(form.getAppId());
			if(organize==null) {
				return Error.BUSINESS("appId");
			}
			
			// 校验验证码
            if(isCaptchaValid(form.getPhone(), form.getCaptcha())) {
            	removeCaptcha(form.getPhone());
            }else {
				return Error.BUSINESS("captcha");
            }
            
			User user = new User();
			if(VerifyType.EMAIL.equals(form.getType())){
				user = userService.findUserByEmail(form.getEmail(), organize.getId());
			}else if(VerifyType.PHONE.equals(form.getType())){
				user = userService.findUserByPhone(form.getPhone(), organize.getId());
			}else{
				return Error.BUSINESS("type");
			}
			
			if(user==null){
				return Error.BUSINESS("user");
			}
			
			// 修改密码
            user.setPassword(MD5Utils.encrypt(form.getPassword(), "dreawer"));
			userService.updatePassword(user);
        	return Success.SUCCESS;
		}catch(Exception e){
			 e.printStackTrace();
	         logger.error(e);
	         return Error.APPSERVER;
		}
    }
    
    /**
     * 验证登录名是否存在
     * @param req 用户请求。
     * @param form 验证表单。
     * @param result 表单校验结果。
     * @return
     */
    @RequestMapping(value=REQ_SIGNUP_USER_EXISTS, method=RequestMethod.POST)
    public ResponseCode isUserExists(HttpServletRequest req, 
    		@Valid VerifyForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
		try {
			// 检查组织是否存在
			Organize organize = organizeService.findOrganizeByAppId(form.getAppId());
			if(organize==null) {
				return Error.BUSINESS("appId");
			}
            
			User user = new User();
			if(VerifyType.EMAIL.equals(form.getType())){
				user = userService.findUserByEmail(form.getEmail(), organize.getId());
			}else if(VerifyType.PHONE.equals(form.getType())){
				user = userService.findUserByPhone(form.getPhone(), organize.getId());
			}else{
				return Error.BUSINESS("type");
			}
			if(user!=null){
				return Error.BUSINESS("user");
			}
        	return Success.SUCCESS;
		}catch(Exception e){
			 e.printStackTrace();
	         logger.error(e);
	         return Error.APPSERVER;
		}
    }
    
    /**
     * 用户校验手机号。
     * @param req 用户请求。
     * @param form 手机号校验表单。
     * @param result 表单校验结果。
     * @return
     */
    @RequestMapping(value=REQ_VERIFY_PHONE_COMMEN, method=RequestMethod.POST)
	public ResponseCode phoneVerify(HttpServletRequest req, 
	        @Valid SetPhoneForm form, BindingResult result) {
		if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
		}
		try {
			// 校验验证码
            if(isCaptchaValid(form.getPhone(), form.getCaptcha())) {
            	removeCaptcha(form.getPhone());
            }else {
				return Error.BUSINESS("captcha");
            }
        	return Success.SUCCESS;
		} catch (Exception e) {
            logger.error(e);
            // 返回失败标志及信息
	        return Error.APPSERVER;
        }
	}
    
}
