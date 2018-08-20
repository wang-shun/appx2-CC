package com.dreawer.customer.web;

import com.dreawer.customer.domain.Customer;
import com.dreawer.customer.domain.Organize;
import com.dreawer.customer.domain.User;
import com.dreawer.customer.lang.UserStatus;
import com.dreawer.customer.lang.VerifyType;
import com.dreawer.customer.service.OrganizeService;
import com.dreawer.customer.service.UserService;
import com.dreawer.customer.utils.MD5Utils;
import com.dreawer.customer.utils.RestRequest;
import com.dreawer.customer.web.form.*;
import com.dreawer.responsecode.rcdt.*;
import com.dreawer.responsecode.rcdt.Error;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.dreawer.customer.constants.ControllerConstants.*;

@RestController
public class SignUpController extends BaseController {
	
    @Autowired
	private UserService userService; // 用户信息服务

    @Autowired
    private OrganizeService organizeService; // 用户信息服务

    @Autowired
    private RestRequest restRequest;
    
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
    		@RequestBody @Valid PhoneSignUpForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
		try {
			// 检查组织是否存在
			Organize organize = organizeService.findOrganizeByAppId(form.getAppId());
			if(organize==null) {
				return Error.BUSINESS("appId");
			}
			
			User user = userService.findUserByPhone(form.getPhone(), organize.getId());
            if(user!=null){
				return Error.BUSINESS("phone");
            }
			// 校验验证码
            if(!restRequest.isCaptchaValid(form.getPhone(), form.getCaptcha())) {
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
            customer.setMugshot(getMugshot(form.getMugshot()));
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
     * 小程序用户注册。
     * @param req 用户请求。
     * @param form 用户注册表单。
     * @param result 表单校验结果。
     * @return 注册成功，返回令牌信息和用户id。失败返回错误原因。
     */
    @RequestMapping(value=REQ_SIGNUP_WXAPP, method=RequestMethod.POST)
    public ResponseCode wxappSignUp(HttpServletRequest req, 
    		@RequestBody @Valid WxappSignUpForm form, BindingResult result) {
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
            user.setOrganizeId(organize.getId());
            user.setStatus(UserStatus.ACTIVATED);
            Customer customer = new Customer();
            customer.setOrganizeId(organize.getId());
            customer.setCategory("person");
            customer.setPetName(form.getPetName());
            customer.setAlias(getAlias(form.getPetName()));
            customer.setMugshot(getMugshot(form.getMugshot()));
            customer.setCreater(user.getId());
            customer.setStatus(UserStatus.ACTIVATED);
            userService.addUser(user, customer);
            Map<String, String> params = new HashMap<>();
            params.put("id", user.getId());
            params.put("token", signInUser(req, user.getId()));
        	return Success.SUCCESS(params);
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
    		@RequestBody @Valid ResetPasswordForm form, BindingResult result) {
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
            if(!restRequest.isCaptchaValid(form.getPhone(), form.getCaptcha())) {
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
    		@RequestBody @Valid VerifyForm form, BindingResult result) {
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
    
}
