package com.dreawer.customer.web;

import com.dreawer.customer.domain.Customer;
import com.dreawer.customer.domain.Organize;
import com.dreawer.customer.domain.TokenUser;
import com.dreawer.customer.domain.User;
import com.dreawer.customer.service.CustomerService;
import com.dreawer.customer.service.OrganizeService;
import com.dreawer.customer.service.TokenUserService;
import com.dreawer.customer.service.UserService;
import com.dreawer.customer.utils.MD5Utils;
import com.dreawer.customer.utils.RedisUtil;
import com.dreawer.customer.web.form.*;
import com.dreawer.responsecode.rcdt.*;
import com.dreawer.responsecode.rcdt.Error;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

import static com.dreawer.customer.constants.ControllerConstants.*;

@RestController
public class UserController extends BaseController {
	
    @Autowired
	private UserService userService; // 用户信息服务
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private OrganizeService organizeService; // 组织信息服务
    
	@Autowired
	private RedisUtil redisUtil;
    
    @Autowired
    private TokenUserService tokenUserService; // 用户信息服务
    
    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器
    
    /**
     * 修改用户密码。
     * @param req 用户请求。
     * @return 执行结果，成功为true，否则为false。
     */
    @RequestMapping(value=REQ_SET_PASSWORD, method=RequestMethod.POST)
    public ResponseCode setPassword(HttpServletRequest req,
    		@RequestBody @Valid SetPasswordForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
		try {
			String userId = req.getHeader("userid");
			User user = userService.findUserById(userId);
	    	if(StringUtils.isNotBlank(user.getPassword())){
	    		if(!form.getNewPassword().equals(form.getConfirmPassword())){
					return EntryError.EMPTY("password");
	    		}
	    		if(form.getNewPassword().equals(form.getOldPassword())){
					return Error.BUSINESS("password");
	    		}
	    		if(!user.getPassword().equals(MD5Utils.encrypt(form.getOldPassword(), "dreawer"))){
					return Error.BUSINESS("password");
	    		}
	    	}
	    	//修改用户信息
	    	user.setPassword(MD5Utils.encrypt(form.getNewPassword(), "dreawer"));
	    	user.setUpdater(user.getId());
	    	user.setUpdateTime(getNow());
	    	userService.updatePassword(user);
        	return Success.SUCCESS;
		}catch(Exception e){
			 e.printStackTrace();
	         logger.error(e);
		     return Error.APPSERVER;
		}
    }

    /**
	 * 修改个人基本信息
	 * @param req 用户请求。
	 * @param form 设置用户信息表单。
	 * @param result 表单校验结果。
	 * @return
	 */
	@RequestMapping(value=REQ_SET_BASIC, method=RequestMethod.POST)
	public ResponseCode setBasic(HttpServletRequest req, 
			@RequestBody @Valid SetBasicForm form, BindingResult result) {
		if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
		}
		
		try {
		    // 获取用户及客户信息
			String userId = req.getHeader("userid");
	        Customer customer = customerService.findCustomerById(userId);
	        if(customer==null) {
	        	return Error.BUSINESS("user");
	        }
	        if(!customer.getPetName().equals(form.getPetName())){
	        	customer.setPetName(form.getPetName());
	        	customer.setAlias(getAlias(form.getPetName()));
	        }
	        if(StringUtils.isNotBlank(form.getMugshot())){
	        	customer.setMugshot(form.getMugshot());
	        }
	        customer.setSlogan(form.getSlogan());
	        customer.setUpdater(userId);
	        customer.setUpdateTime(new Timestamp(System.currentTimeMillis()));
	        customerService.updateBasic(customer);
			// TODO 更新用户登录信息
			updateSignInUser(req);
        	return Success.SUCCESS;
		} catch (Exception e) {
            logger.error(e);
            // 返回失败标志及信息
		    return Error.APPSERVER;
        }
	}
	
	/**
	 * 用户设置邮箱。
	 * @param req 用户请求。
	 * @param form 更改邮箱表单。
	 * @param result 表单校验结果。
	 * @return
	 */
	@RequestMapping(value=REQ_SET_EMAIL, method=RequestMethod.POST)
	public ResponseCode setEmail(HttpServletRequest req, 
			@RequestBody @Valid SetEmailForm form, BindingResult result) {
		if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
		}
		try {
			String userId = req.getHeader("userid");

			// 检查组织是否存在
			Organize organize = organizeService.findOrganizeByAppId(form.getAppId());
			if(organize==null) {
				return Error.BUSINESS("appId");
			}
			
			// 判断邮箱是否已注册
			User existsUser = userService.findUserByEmail(form.getEmail(), organize.getId());
            if(existsUser!=null){
				return Error.BUSINESS("email");
            }
	        
			// TODO 校验验证码 换成通知中心校验
            /*if(isCaptchaValid(form.getEmail(), form.getCaptcha())) {
            	removeCaptcha(form.getEmail());
            }else {
				return Error.BUSINESS("captcha");
            }*/
			
			// 更新邮箱
			User user = new User();
	    	user.setId(userId);
			user.setEmail(form.getEmail());
			user.setUpdater(userId);
			user.setUpdateTime(getNow());
			userService.updateBasic(user);
			
			// TODO 更新用户登录信息
			updateSignInUser(req);
        	return Success.SUCCESS;
		} catch (Exception e) {
            logger.error(e);
            
            // 返回失败标志及信息
		    return Error.APPSERVER;
        }
	}
	
	/**
	 * 修改手机号。
	 * @param req 用户请求。
	 * @param form 验证手机号表单。
	 * @param result 表单校验结果。
	 * @return
	 */
	@RequestMapping(value=REQ_SET_PHONE, method=RequestMethod.POST)
	public ResponseCode setPhone(HttpServletRequest req, 
			@RequestBody @Valid SetPhoneForm form, BindingResult result) {
		if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
		}
		try {
			
			// 检查组织是否存在
			Organize organize = organizeService.findOrganizeByAppId(form.getAppId());
			if(organize==null) {
				return Error.BUSINESS("appId");
			}
			
			// 判断手机是否已注册
			User existsUser = userService.findUserByPhone(form.getPhone(), organize.getId());
            if(existsUser!=null){
				return Error.BUSINESS("phone");
            }
	        
			// TODO 校验验证码 换成通知中心校验
            /*if(isCaptchaValid(form.getPhone(), form.getCaptcha())) {
            	removeCaptcha(form.getPhone());
            }else {
				return Error.BUSINESS("captcha");
            }*/
			
			// 更新邮箱
			User user = new User();
	    	user.setId(form.getUserId());
			user.setPhoneNumber(form.getPhone());
			user.setUpdater(form.getUserId());
			user.setUpdateTime(getNow());
			userService.updateBasic(user);
			// TODO 更新用户登录信息
			updateSignInUser(req);
        	return Success.SUCCESS;
		} catch (Exception e) {
            logger.error(e);
            
            // 返回失败标志及信息
		    return Error.APPSERVER;
        }
	}
	
	/**
	 * 获取用户信息。
	 * @param req
	 * @return
	 */
	@RequestMapping(value=REQ_USERINFO, method=RequestMethod.GET)
	public ResponseCode userInfo(HttpServletRequest req) {
		try {
			String token = req.getHeader("token");
	        TokenUser tokenUser = redisUtil.getTokenUser(token);
        	return Success.SUCCESS(tokenUser);
		} catch (Exception e) {
            logger.error(e);
            
            // 返回失败标志及信息
		    return Error.APPSERVER;
        }
	}
	
	/**
	 * 获取用户信息。
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/manage/user", method=RequestMethod.GET)
	public ResponseCode userList(HttpServletRequest req, 
			@Valid UserQueryForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
		try {
			//String appId = req.getHeader("appid");
			
			Timestamp startTime = null;
    		Timestamp endTime = null;
    		if(form.getStartTime()!=null){
    			startTime = new Timestamp(form.getStartTime());
    		}
    		if(form.getEndTime()!=null){
    			endTime = new Timestamp(form.getEndTime());
    		}
    		int pageNo = 1;
    		int pageSize = 5;
    		if(form.getPageNo()!=null && form.getPageNo()>0){
    			pageNo = form.getPageNo();
    		}
    		if(form.getPageSize()!=null && form.getPageSize()>0){
    			pageSize = form.getPageSize();
    		}
    		int start = (pageNo-1)*pageSize;
    		
    		List<TokenUser> users = tokenUserService.findUsers(form.getAppId(), form.getQuery(), start, pageSize, startTime, endTime);
			return Success.SUCCESS(users);
		} catch (Exception e) {
            logger.error(e);
            
            // 返回失败标志及信息
		    return Error.APPSERVER;
        }
	}
}
