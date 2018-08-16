package com.dreawer.customer.web.member;

import com.dreawer.customer.domain.Hierarchy;
import com.dreawer.customer.exception.ResponseCodeException;
import com.dreawer.customer.form.AddMemberRankForm;
import com.dreawer.customer.form.EditMemberRankForm;
import com.dreawer.customer.form.UpdateMemberRankStatusForm;
import com.dreawer.customer.lang.MemberRankExpiration;
import com.dreawer.customer.lang.MemberRankStatus;
import com.dreawer.customer.lang.member.Status;
import com.dreawer.customer.manager.HierarchyManager;
import com.dreawer.customer.utils.RedisUtil;
import com.dreawer.customer.web.BaseController;
import com.dreawer.responsecode.rcdt.EntryError;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.dreawer.responsecode.rcdt.RuleError;
import com.dreawer.responsecode.rcdt.Success;
import com.google.gson.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dreawer.customer.ControllerConstants.*;
import static com.dreawer.customer.DomainConstants.*;
import static com.dreawer.customer.constants.ControllerConstants.REQ_ADD;

@Controller
@RequestMapping(REQ_MEMBER_RANK)
public class MemberRankController extends BaseController {

    @Autowired
	private RedisUtil redisUtil;

	@Autowired
	private HierarchyManager hierarchyManager;
	

    /**
     * 添加会员等级
     * @param req 用户请求
     * @param form 添加会员表单
     * @return 添加结果
     * @author kael 
     * @since 1.0
     */
    @RequestMapping(value=REQ_ADD, method = RequestMethod.POST)
    public @ResponseBody
	ResponseCode add(HttpServletRequest req, @RequestBody @Valid AddMemberRankForm form) throws Exception {

        	//获取用户信息
        	//User user = getSignInUser(req);
			String userId = form.getUserId();
			//判断店铺ID是否为空
        	String storeId = form.getStoreId();
        	if(storeId == null || StringUtils.isBlank(storeId)){
        		return EntryError.EMPTY(STORE_ID);
        	}
        	
        	//判断会员等级名称是否为空以及长度是否合规
        	String name = form.getName();
        	if(name == null){
        		return EntryError.EMPTY(NAME);
        	}else if(name.length() < 1){
        		return EntryError.TOO_SHORT(NAME);
        	}else if(name.length() > 20){
        		return EntryError.TOO_LONG(NAME);
        	}
        	
        	//判断成长值是否为空以及取值范围是否合规
        	Integer growthValue = form.getGrowthValue();
        	if(growthValue == null){
        		return EntryError.EMPTY(GROWTH_VALUE);
        	}else if(growthValue < 0 || growthValue > 99999999){
        		return EntryError.OVERRANGE(GROWTH_VALUE);
        	}
        	
        	//获取是否有包邮权益
        	Boolean freeShipping = form.getFreeShipping();
        	
        	//判断当会员折扣开启时，折扣是否为空并且符合规则
        	Boolean discount = form.getDiscount();
        	BigDecimal discountAmount = form.getDiscountAmount();
        	if(discount){
        		if(discountAmount == null){
        			return EntryError.EMPTY(DISCOUNT_AMOUNT);
        		}else{
        			//折扣为1.0-9.9之间
					Pattern pattern = Pattern.compile("^(?=1\\.[1-9]|[1-9]\\.\\d).{3}$|^([1-9])$");
					Matcher matcher = pattern.matcher(discountAmount.toString());
				    if (!matcher.matches()) {
				        return EntryError.FORMAT(DISCOUNT_AMOUNT);
				    }
        		}
			}
        	
        	//判断有效期是否为空
        	MemberRankExpiration expiration = form.getExpiration();
        	Integer period = form.getPeriod();
        	Integer expireDeduction = form.getExpireDeduction();
        	if(expiration == null){
        		return EntryError.EMPTY(EXPIRATION);
        	}else{
        		if(expiration.equals(MemberRankExpiration.LIMITED)){
        			//判断有效期类型为LIMITED时，有效期是否为空并且符合规则
            		if(period == null){
            			return EntryError.EMPTY(PERIOD);
            		}else if(period < 1 || period > 96){
            			return EntryError.OVERRANGE(PERIOD);
            		}
            		
            		//判断有效期类型为LIMITED时，过期后扣减成长值是否为空并且符合规则
            		if(expireDeduction == null){
            			return EntryError.EMPTY(EXPIRE_DEDUCTION);
            		}else if(expireDeduction < 1 || expireDeduction > 99999999){
                		return EntryError.OVERRANGE(EXPIRE_DEDUCTION);
                	}
        		}
        	}
        	
        	//判断状态是否为空
        	MemberRankStatus status = form.getStatus();
        	if(status == null){
        		return EntryError.OVERRANGE(STATUS);
        	}
        	
        	//获取32位UUID
        	String id = UUID.randomUUID().toString().replace("-", "");
        	
        	//封装消息数据
        	Map<String, Object> data = new HashMap<>();
        	data.put(ID, id);
        	data.put(STORE_ID, storeId);
        	data.put(NAME, name);
        	data.put(GROWTH_VALUE, growthValue);
        	data.put(FREE_SHIPPING, freeShipping);
        	data.put(DISCOUNT, discount);
        	data.put(DISCOUNT_AMOUNT, discountAmount);
        	data.put(EXPIRATION, expiration);
        	data.put(PERIOD, period);
        	data.put(EXPIRE_DEDUCTION, expireDeduction);
        	data.put(STATUS, status);
        	data.put(USER_ID, userId);

			Hierarchy hierarchy = hierarchyManager.addHierarchy(data, storeId);
			return Success.SUCCESS(hierarchy);


    }
    
    /**
     * 修改会员等级
     * @param req 用户请求
     * @param form 修改会员表单
     * @return 修改结果
     * @author kael 
     * @since 1.0
     */
    @RequestMapping(value=REQ_EDIT, method = RequestMethod.POST)
    public @ResponseBody ResponseCode edit(HttpServletRequest req, @RequestBody @Valid EditMemberRankForm form) throws Exception {


        	//获取用户信息
        	//User user = getSignInUser(req);
			String userId = form.getUserId();
			//判断会员等级ID是否为空
        	String id = form.getId();
        	if(id == null || StringUtils.isBlank(id)){
        		return EntryError.EMPTY(ID);
        	}
        	
        	//判断店铺ID是否为空
        	String storeId = form.getStoreId();
        	if(storeId == null || StringUtils.isBlank(storeId)){
        		return EntryError.EMPTY(STORE_ID);
        	}
        	
        	//判断会员等级名称是否为空以及长度是否合规
        	String name = form.getName();
        	if(name == null){
        		return EntryError.EMPTY(NAME);
        	}else if(name.length() < 1){
        		return EntryError.TOO_SHORT(NAME);
        	}else if(name.length() > 20){
        		return EntryError.TOO_LONG(NAME);
        	}
        	
        	//判断成长值是否为空以及取值范围是否合规
        	Integer growthValue = form.getGrowthValue();
        	if(growthValue == null){
        		return EntryError.EMPTY(GROWTH_VALUE);
        	}else if(growthValue < 0 || growthValue > 99999999){
        		return EntryError.OVERRANGE(GROWTH_VALUE);
        	}
        	
        	//获取是否有包邮权益
        	Boolean freeShipping = form.getFreeShipping();
        	
        	//判断当会员折扣开启时，折扣是否为空并且符合规则
        	Boolean discount = form.getDiscount();
        	BigDecimal discountAmount = form.getDiscountAmount();
        	if(discount){
        		if(discountAmount == null){
        			return EntryError.EMPTY(DISCOUNT_AMOUNT);
        		}else{
        			//折扣为1.0-9.9之间
					Pattern pattern = Pattern.compile("^(?=1\\.[1-9]|[1-9]\\.\\d).{3}$|^([1-9])$");
					Matcher matcher = pattern.matcher(discountAmount.toString());
				    if (!matcher.matches()) {
				        return EntryError.FORMAT(DISCOUNT_AMOUNT);
				    }
        		}
			}
        	
        	//判断有效期是否为空
        	MemberRankExpiration expiration = form.getExpiration();
        	Integer period = form.getPeriod();
        	Integer expireDeduction = form.getExpireDeduction();
        	if(expiration == null){
        		return EntryError.EMPTY(EXPIRATION);
        	}else{
        		if(expiration.equals(MemberRankExpiration.LIMITED)){
        			//判断有效期类型为LIMITED时，有效期是否为空并且符合规则
            		if(period == null){
            			return EntryError.EMPTY(PERIOD);
            		}else if(period < 1 || period > 96){
            			return EntryError.OVERRANGE(PERIOD);
            		}
            		
            		//判断有效期类型为LIMITED时，过期后扣减成长值是否为空并且符合规则
            		if(expireDeduction == null){
            			return EntryError.EMPTY(EXPIRE_DEDUCTION);
            		}else if(expireDeduction < 1 || expireDeduction > 99999999){
                		return EntryError.OVERRANGE(EXPIRE_DEDUCTION);
                	}
        		}
        	}
        	
        	//判断状态是否为空
        	MemberRankStatus status = form.getStatus();
        	if(status == null){
        		return EntryError.OVERRANGE(STATUS);
        	}
        	
        	//封装消息数据
        	Map<String, Object> data = new HashMap<>();
        	data.put(ID, id);
        	data.put(STORE_ID, storeId);
        	data.put(NAME, name);
        	data.put(GROWTH_VALUE, growthValue);
        	data.put(FREE_SHIPPING, freeShipping);
        	data.put(DISCOUNT, discount);
        	data.put(DISCOUNT_AMOUNT, discountAmount);
        	data.put(EXPIRATION, expiration);
        	data.put(PERIOD, period);
        	data.put(EXPIRE_DEDUCTION, expireDeduction);
        	data.put(STATUS, status);
        	data.put(USER_ID, userId);

			Hierarchy hierarchy = hierarchyManager.update(data, storeId);
			return Success.SUCCESS(hierarchy);

    }
    
    /**
     * 禁用/启用会员等级
     * @param req 用户请求
     * @param form 添加会员表单
     * @return 修改结果
     * @author kael 
     * @since 1.0
     */
    @RequestMapping(value=REQ_UPDATE_STATUS, method = RequestMethod.POST)
    public @ResponseBody ResponseCode updateStatus(HttpServletRequest req, @RequestBody @Valid UpdateMemberRankStatusForm form) throws ResponseCodeException, IllegalAccessException {

        	//获取用户信息
        	//User user = getSignInUser(req);
			String userId = form.getUserId();
			//判断会员等级ID是否为空
        	String id = form.getId();
        	if(id == null || StringUtils.isBlank(id)){
        		return EntryError.EMPTY(ID);
        	}
        	
        	//判断店铺ID是否为空
        	String storeId = form.getStoreId();
        	if(storeId == null || StringUtils.isBlank(storeId)){
        		return EntryError.EMPTY(STORE_ID);
        	}
        	
        	//判断状态是否为空
		Status status = form.getStatus();
		if(status == null){
        		return EntryError.EMPTY(STATUS);
        	}
        	
        	//封装消息数据
        	Map<String, Object> data = new HashMap<>();
        	data.put(ID, id);
        	data.put(STORE_ID, storeId);
        	data.put(STATUS, status);
        	data.put(USER_ID, userId);
        	
        	hierarchyManager.updateStatus(data,storeId);
        	
			return Success.SUCCESS;
        	

    }
    
    /**
     * 查询会员等级列表
     * @param req 用户请求
     * @return 修改结果
     * @author kael 
     * @since 1.0
     */
    @RequestMapping(value=REQ_LIST, method = RequestMethod.GET)
    public @ResponseBody ResponseCode list(HttpServletRequest req, @RequestParam(STORE_ID)String storeId){

        	//判断店铺ID是否为空
        	if(StringUtils.isBlank(storeId)){
        		return EntryError.EMPTY(STORE_ID);
        	}
        	//从redis中取出查询结果
			List<Hierarchy> hierarchies = redisUtil.getJsonArray("hierarchy_list_" + storeId, Hierarchy.class);
			//判断查询结果是否为空
        	if(hierarchies == null){
        		return Success.SUCCESS;
        	}
        	Map<String, Object> result = new HashMap<>();
        	result.put("result", hierarchies);
        	return Success.SUCCESS(result);
        	

    }
    
    /**
     * 查询会员等级详情
     * @param req 用户请求
     * @return 修改结果
     * @author kael 
     * @since 1.0
     */
    @RequestMapping(value=REQ_DETAIL, method = RequestMethod.GET)
    public @ResponseBody ResponseCode detail(HttpServletRequest req, @RequestParam(ID)String id){

        	
        	//判断店铺ID是否为空
        	if(StringUtils.isBlank(id)){
        		return EntryError.EMPTY(ID);
        	}
        	
        	//从redis中取出查询结果
        	JsonObject jsonObject = redisUtil.getJsonObject("hierarchy_"+id);
        	
        	//判断查询结果是否为空
        	if(jsonObject == null){
        		return RuleError.NON_EXISTENT;
        	}
        	
        	Map<String, Object> result = new HashMap<>();
        	result.put("result", jsonObject.toString());
        	return Success.SUCCESS(result);
        	

    }


}
