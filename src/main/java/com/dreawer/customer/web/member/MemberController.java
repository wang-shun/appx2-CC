package com.dreawer.customer.web.member;


import com.dreawer.customer.domain.Hierarchy;
import com.dreawer.customer.domain.Member;
import com.dreawer.customer.domain.PointRecord;
import com.dreawer.customer.exception.ResponseCodeException;
import com.dreawer.customer.form.DiscountQueryForm;
import com.dreawer.customer.form.EditMemberForm;
import com.dreawer.customer.form.RegisterMemberForm;
import com.dreawer.customer.lang.member.Status;
import com.dreawer.customer.lang.record.Source;
import com.dreawer.customer.lang.record.Type;
import com.dreawer.customer.manager.MemberManager;
import com.dreawer.customer.service.HierarchyService;
import com.dreawer.customer.service.MemberService;
import com.dreawer.customer.utils.RedisUtil;
import com.dreawer.customer.web.BaseController;
import com.dreawer.customer.web.form.GoodsInfoForm;
import com.dreawer.responsecode.rcdt.EntryError;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.dreawer.responsecode.rcdt.Success;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.dreawer.customer.ControllerConstants.*;
import static com.dreawer.customer.DomainConstants.*;

@Controller
@RequestMapping(REQ_MEMBER)
public class MemberController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器


	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private MemberManager memberManager;

	@Autowired
	private MemberService memberService;

	@Autowired
	private HierarchyService hierarchyService;
	
    /**
     * 注册会员
     * @param req 用户请求
     * @param form 注册会员表单
     * @return 注册结果
     * @author kael 
     * @since 1.0
     */
    @RequestMapping(value=REQ_REGISTER, method = RequestMethod.POST)
    public @ResponseBody
	ResponseCode register(HttpServletRequest req, @RequestBody @Valid RegisterMemberForm form) throws Exception {

        	//获取用户信息
			String userId = req.getHeader("userid");
			//判断店铺ID是否为空
        	String storeId = form.getStoreId();
        	if(storeId == null || StringUtils.isBlank(storeId)){
        		return EntryError.EMPTY(STORE_ID);
        	}
        	
        	//判断电话号码是否为空并且是否符合规则
        	String phoneNumber = form.getPhoneNumber();
        	if(phoneNumber == null){
        		return EntryError.EMPTY(PHONE_NUMBER);
        	}else{
    			//折扣为1.0-9.9之间
				Pattern pattern = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");
				Matcher matcher = pattern.matcher(phoneNumber);
			    if (!matcher.matches()) {
			        return EntryError.FORMAT(PHONE_NUMBER);
			    }
        	}
        	
        	//判断验证码是否正确
//        	String captcha = form.getCaptcha();
//        	if(captcha != null){
//        		boolean verifyPhone = verifyPhone(phoneNumber, captcha);
//        		if(!verifyPhone){
//        			return PermissionsError.CODE_ERROR(CAPTCHA);
//        		}
//        	}
        	
        	//判断性别是否为空
        	Integer sex = form.getSex();
        	if(sex == null){
        		return EntryError.EMPTY(SEX);
        	}else if(sex < 0 || sex > 2){
        		return EntryError.FORMAT(SEX);
        	}
        	
        	//判断会员名称长度是否合规
        	String username = form.getUserName();
        	if(username != null){
        		if(username.length() < 1){
            		return EntryError.TOO_SHORT(USER_NAME);
            	}else if(username.length() > 64){
            		return EntryError.TOO_LONG(USER_NAME);
            	}
        	}
        	
        	//判断会员头像是否为空
        	String mugshot = form.getMugshot();
        	if(mugshot == null || StringUtils.isBlank(mugshot)){
        		return EntryError.EMPTY(MUGSHOT);
        	}
        	
        	//判断会员昵称是否为空
        	String nickName = form.getNickName();
        	if(nickName == null || StringUtils.isBlank(nickName)){
        		return EntryError.EMPTY(NICK_NAME);
        	}
        	
        	//获取会员生日
        	String birthday = form.getBirthday();
        	
        	//封装消息数据
        	Map<String, Object> data = new HashMap<>();
        	data.put(STORE_ID, storeId);
        	data.put(SEX, sex);
        	data.put(PHONE_NUMBER, phoneNumber);
        	data.put(USER_NAME, username);
        	data.put(BIRTHDAY, birthday);
        	data.put(ID, userId);
        	data.put(MUGSHOT, mugshot);
        	data.put(NICK_NAME, nickName);

            memberManager.addMember(data,storeId);


			return Success.SUCCESS(data);


	}
    
    /**
     * 修改会员信息
     * @param req 用户请求
     * @param form 修改会员信息表单
     * @return 修改结果
     * @author kael 
     * @since 1.0
     */
    @RequestMapping(value=REQ_EDIT, method = RequestMethod.POST)
    public @ResponseBody ResponseCode edit(HttpServletRequest req, @RequestBody @Valid EditMemberForm form) throws Exception {

			//获取用户信息
			String userId = req.getHeader("userid");
			//判断店铺ID是否为空
        	String storeId = form.getStoreId();
        	if(storeId == null || StringUtils.isBlank(storeId)){
        		return EntryError.EMPTY(STORE_ID);
        	}
        	
        	//判断电话号码是否为空并且是否符合规则
        	String phoneNumber = form.getPhoneNumber();
        	if(phoneNumber == null){
        		return EntryError.EMPTY(PHONE_NUMBER);
        	}else{
    			//折扣为1.0-9.9之间
				Pattern pattern = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");
				Matcher matcher = pattern.matcher(phoneNumber);
			    if (!matcher.matches()) {
			        return EntryError.FORMAT(PHONE_NUMBER);
			    }
        	}
        	
        	//判断性别是否为空
        	Integer sex = form.getSex();
        	if(sex == null){
        		return EntryError.EMPTY(SEX);
        	}else if(sex < 0 || sex > 2){
        		return EntryError.FORMAT(SEX);
        	}
        	
        	//判断会员名称长度是否合规
        	String username = form.getUserName();
        	if(username != null){
        		if(username.length() < 1){
            		return EntryError.TOO_SHORT(USER_NAME);
            	}else if(username.length() > 64){
            		return EntryError.TOO_LONG(USER_NAME);
            	}
        	}
        	
        	//获取会员生日
        	String birthday = form.getBirthday();
        	
        	//封装消息数据
        	Map<String, Object> data = new HashMap<>();
        	data.put(STORE_ID, storeId);
        	data.put(SEX, sex);
        	data.put(PHONE_NUMBER, phoneNumber);
        	data.put(USER_NAME, username);
        	data.put(BIRTHDAY, birthday);
        	data.put(ID, userId);

        	memberManager.update(data,storeId);
        	return Success.SUCCESS(data);


    }
    
    /**
     * 查询会员信息
     * @param req 用户请求
     * @return 注册结果
     * @author kael 
     * @since 1.0
     */
    @ApiOperation(value = "根据登录信息和店铺ID查询会员信息")
	@ApiImplicitParams({@ApiImplicitParam(name = "storeId",value = "店铺ID",required = true,dataType = "String"),
	@ApiImplicitParam(name = "terminalType",value = "终端类型",required = true,defaultValue = "BROWSER")
	})
    @RequestMapping(value=REQ_DETAIL, method = RequestMethod.GET)
    public @ResponseBody ResponseCode detail(HttpServletRequest req, @RequestParam(STORE_ID)String storeId, @RequestParam(TERMINAL_TYPE)String terminalType){
        	
        	//获取用户信息
			String userId = req.getHeader("userid");
        	//判断店铺ID是否为空
        	if(StringUtils.isBlank(storeId)){
        		return EntryError.EMPTY(STORE_ID);
        	}
        	
        	JsonObject jsonObject = null;
        	
        	//判断终端类型
        	if(terminalType.equals(TERMINAL_TYPE_BROWSER)){
        		//判断用户ID是否为空
        		if(userId == null || StringUtils.isBlank(userId)){
        			return EntryError.EMPTY(USER_ID);
        		}
        		
        		jsonObject = redisUtil.getJsonObject("member_"+userId+"_"+storeId);
        	}else{
        		jsonObject = redisUtil.getJsonObject("member_"+userId+"_"+storeId);
        	}

        	Map<String, Object> result = new HashMap<>();
        	if (jsonObject==null){
				result.put("result", null);
			}else {
				result.put("result", jsonObject);
			}

        	
			return Success.SUCCESS(result);
        	

    }
    
    /**
     * 查询会员信息
     * @param req 用户请求
     * @return 注册结果
     * @author kael 
     * @since 1.0
     */
    @RequestMapping(value=REQ_LIST, method = RequestMethod.GET)
    public @ResponseBody ResponseCode list(HttpServletRequest req, @RequestParam(STORE_ID)String storeId, @RequestParam(value=PHONE_NUMBER,required=false)String phoneNumber, @RequestParam(value=USER_NAME,required=false)String userName,
    		@RequestParam(value=RANK_ID,required=false)String rankId, @RequestParam(value=NICK_NAME,required=false)String nickName, @RequestParam(value=APP_TYPE,required=false)String appType, @RequestParam(value=PAGE_NO, required=false, defaultValue="1")Integer pageNo,
    		@RequestParam(value=PAGE_SIZE, required=false, defaultValue="5")Integer pageSize){

        	//判断店铺ID是否为空
        	if(StringUtils.isBlank(storeId)){
        		return EntryError.EMPTY(STORE_ID);
        	}
        	Map<String, Object> data = new HashMap<>();
        	data.put(STORE_ID, storeId);
        	data.put(PHONE_NUMBER, phoneNumber);
        	data.put(USER_NAME, userName);
        	data.put(RANK_ID, rankId);
        	data.put(NICK_NAME, nickName);
        	data.put(APP_TYPE, appType);
        	data.put(PAGE_NO, pageNo);
        	data.put(PAGE_SIZE, pageSize);

			Map map = memberManager.memberQuery(data);

			return Success.SUCCESS(map);
        	

    }
    
    /**
     * 查询会员成长值记录
     * @param req 用户请求
     * @return 查询结果
     * @author kael 
     * @since 1.0
     */
    @RequestMapping(value=REQ_POINT_RECORD, method = RequestMethod.GET)
    public @ResponseBody ResponseCode pointRecord(HttpServletRequest req, @RequestParam(STORE_ID)String storeId, @RequestParam(TERMINAL_TYPE)String terminalType, @RequestParam(value=PAGE_NO, required=false, defaultValue="1")Integer pageNo, @RequestParam(value=PAGE_SIZE, required=false, defaultValue="5")Integer pageSize) throws Exception {

        	//获取用户信息
			String userId = req.getHeader("userid");
			//判断店铺ID是否为空
        	if(StringUtils.isBlank(storeId)){
        		return EntryError.EMPTY(STORE_ID);
        	}
        	
        	//封装消息数据
        	Map<String, Object> data = new HashMap<>();
        	data.put(STORE_ID, storeId);
        	
        	//判断终端类型
        	if(terminalType.equals(TERMINAL_TYPE_BROWSER)){
        		//判断用户ID是否为空
        		if(userId == null || StringUtils.isBlank(userId)){
        			return EntryError.EMPTY(USER_ID);
        		}
        		
        		data.put(USER_ID, userId);
        	}else{
        		data.put(USER_ID, userId);
        	}
        	
        	data.put(PAGE_NO, pageNo);
        	data.put(PAGE_SIZE, pageSize);

			Map<String, Object> map = memberManager.recordQuery(data, storeId);

			return Success.SUCCESS(map);

    }
    
//    /**
//     * 任务查询
//     * @param req 用户请求
//     * @return 查询结果
//     * @author kael
//     * @since 1.0
//     */
//    @RequestMapping(value=REQ_TASK_QUERY, method = RequestMethod.GET)
//    public @ResponseBody ResponseCode taskQuery(HttpServletRequest req, @RequestParam(TASK_ID)String taskId){
//        try {
//
//        	JsonObject jsonObject = redisUtil.getJsonObject("taskId_"+taskId);
//
//        	//判断查询结果是否为空
//        	if(jsonObject == null){
//        		return Warning.UN_GET_RESULT;
//        	}
//        	String string = jsonObject.toString();
//
//        	Gson gson = new Gson();
//        	ResponseMessage responseMessage = gson.fromJson(string, ResponseMessage.class);
//
//        	PublicResponseMessage publicMessage = responseMessage.getPublicMessage();
//        	if(publicMessage == null){
//        		return MessageError.NO_HEAD;
//        	}
//
//        	ResponseCode responseCode = publicMessage.getResponseCode();
//        	if(responseCode == null){
//        		return MessageError.NO_HEAD;
//        	}
//
//        	if(responseCode.equals(ResponseCode.C_FAILED)){
//        		return responseCode;
//        	}
//
//        	Object body = responseMessage.getBody(DATA);
//
//        	redisUtil.delete("taskId_"+taskId);
//			return Success.SUCCESS(body);
//
//		} catch (Exception e) {
//			Log.error(e);
//            return Error.DEFAULT;
//		}
//    }
    
    /**
     * 查询会员信息
     * @param req 用户请求
     * @return 注册结果
     * @author kael 
     * @since 1.0
     */
    @RequestMapping(value=REQ_CHECK, method = RequestMethod.GET)
    public @ResponseBody ResponseCode check(HttpServletRequest req, @RequestParam(STORE_ID)String storeId, @RequestParam(TERMINAL_TYPE)String terminalType){

        	
        	//获取用户信息
			String userId = req.getHeader("userid");
			//判断店铺ID是否为空
        	if(StringUtils.isBlank(storeId)){
        		return EntryError.EMPTY(STORE_ID);
        	}
        	
        	JsonObject jsonObject = null;
        	
        	//判断终端类型
        	if(terminalType.equals(TERMINAL_TYPE_BROWSER)){
        		//判断用户ID是否为空
        		if(userId == null || StringUtils.isBlank(userId)){
        			return EntryError.EMPTY(USER_ID);
        		}
        		jsonObject = redisUtil.getJsonObject("member_"+userId+"_"+storeId);
        	}else{
        		jsonObject = redisUtil.getJsonObject("member_"+userId+"_"+storeId);
        	}
        	
        	Map<String, Boolean> result = new HashMap<>();
        	
        	//判断查询结果是否为空
        	if(jsonObject == null){
        		result.put("isMember", false);
        	}else{
        		result.put("isMember", true);
        	}
        	
			return Success.SUCCESS(result);

    }


	/**
	 * 计算会员折扣
	 * @param form 商品表单
	 * @return 折扣金额
	 */
	@RequestMapping(value = REQ_DISCOUNT,method = RequestMethod.POST)
	public @ResponseBody ResponseCode discount(@RequestBody DiscountQueryForm form) throws IllegalAccessException {

			Boolean hasDiscount = false;
			Boolean freeShipping = false;
			String userId = form.getUserId();
			Map<String,Object> param = new HashMap<>();
			param.put("hasDiscount",hasDiscount);
			Member member = memberService.findById(userId);
			if (member==null){
				return Success.SUCCESS(param);
			}
			Hierarchy hierarchy = hierarchyService.findById(member.getHierarchyId());
			if (hierarchy==null){
				return Success.SUCCESS(param);
			}
			if (!hierarchy.getDiscount()||hierarchy.getStatus().equals(Status.DISABLE)){
				return Success.SUCCESS(param);
			}
			if (hierarchy.getFreeShipping()){
				freeShipping=true;
			}
			String discountAmount = hierarchy.getDiscountAmount();
			List<GoodsInfoForm> goodsInfo = form.getGoodsInfo();
			//遍历计算折扣金额
			for (GoodsInfoForm goodsInfoForm : goodsInfo) {
				BigDecimal price = goodsInfoForm.getPrice();
				BigDecimal discountPrice = price
						.divide(new BigDecimal(10),2,RoundingMode.HALF_UP)
						.multiply(new BigDecimal(discountAmount));
				goodsInfoForm.setPrice(discountPrice);
			}
			hasDiscount=true;
			param.put("hasDiscount",hasDiscount);
			param.put("goodsInfo",goodsInfo);
			param.put("freeShipping",freeShipping);
			return Success.SUCCESS(param);

	}

	/**
	 * 添加会员成长值
	 * @return 注册结果
	 * @author kael
	 * @since 1.0
	 */
	@RequestMapping(value=REQ_POINT_RECORD_ADD, method = RequestMethod.GET)
	public @ResponseBody ResponseCode addPoint(HttpServletRequest req,@RequestParam(STORE_ID)String storeId,@RequestParam("value")String value) throws Exception {

			//判断店铺ID是否为空
			if(StringUtils.isBlank(storeId)){
				return EntryError.EMPTY(STORE_ID);
			}
			if (StringUtils.isBlank(value)){
				return EntryError.EMPTY(VALUE);
			}

			//如果消费金额小于1则不计入成长值
			if (new BigDecimal(value).compareTo(new BigDecimal(1))==-1||
			new BigDecimal(value).compareTo(new BigDecimal(100000))==1) {
				throw new ResponseCodeException(EntryError.OVERRANGE(VALUE));
			}

			String userId = req.getHeader("userid");
			PointRecord pointRecord = new PointRecord();
			pointRecord.setCustomerId(userId);
			pointRecord.setSource(Source.SYSTEM.toString());
			pointRecord.setCreateTime(getNow());
			pointRecord.setStoreId(storeId);
			pointRecord.setValue(value);
			pointRecord.setType(Type.PURCHASE.toString());
			memberManager.updateRecord(pointRecord,storeId);

			return Success.SUCCESS(pointRecord);


	}


}
