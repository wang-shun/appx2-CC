package com.dreawer.customer.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dreawer.customer.domain.Address;
import com.dreawer.customer.utils.RedisUtil;
import com.dreawer.customer.web.form.AddAddressForm;
import com.dreawer.customer.web.form.EditAddressForm;
import com.dreawer.responsecode.rcdt.Error;
import com.dreawer.responsecode.rcdt.ResponseCode;
import com.dreawer.responsecode.rcdt.ResponseCodeRepository;
import com.dreawer.responsecode.rcdt.RuleError;
import com.dreawer.responsecode.rcdt.Success;

@RestController
public class AddressController extends BaseController{

	@Autowired
	private RedisUtil redisUtil;
    
    /**
     * 用户添加地址信息。
     * @param req 用户请求。
     * @return
     */
    @RequestMapping(value="/address/add", method=RequestMethod.POST)
	public ResponseCode addAddress(HttpServletRequest req, 
			@RequestBody @Valid AddAddressForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
		try {
			// 检查地址参数信息
			String query = new StringBuilder().append("provinceId=").append(form.getProvince())
					.append("&cityId=").append(form.getCity()).append("&areaId=").append(form.getArea()).toString();
        	String addressResult = httpGet("https://basedata.dreawer.com/district/check", query);
        	if(StringUtils.isBlank(addressResult)){
				return Error.EXT_REQUEST("account");
			}
        	JSONObject json = new JSONObject(addressResult);
			if(!json.getBoolean("status")){
				return Error.EXT_REQUEST("account");
			}
			JSONObject data = json.getJSONObject("data");
			JSONObject province = data.getJSONObject("province");
			JSONObject city = data.getJSONObject("city");
			JSONObject area = data.getJSONObject("area");
			
            String id = UUID.randomUUID().toString().replace("-", "");
            Address address = new Address();
            address.setId(id);
            address.setName(form.getName());
            address.setPhoneNumber(form.getPhone());
            address.setProvince(province.getString("id"));
            address.setProvinceName(province.getString("name"));
            address.setCity(city.getString("id"));
            address.setCityName(city.getString("name"));
            address.setArea(area.getString("id"));
            address.setAreaName(area.getString("name"));
            address.setDetail(form.getDetail());
            address.setUserId(form.getUserId());
            Long now = System.currentTimeMillis();
            address.setCreateTime(now);
            address.setUpdateTime(now);
            List<Address> addresses =  redisUtil.getJsonArray("addr_"+form.getUserId(), Address.class);
            if(addresses==null || addresses.size()<=0){
            	// 把地址添加到缓存
                address.setStatus("DEFAULT");
            	addresses = new ArrayList<>();
            	addresses.add(address);
            	redisUtil.put("addr_"+form.getUserId(), addresses);
            }else{
            	// 同步缓存 
                address.setStatus("USED");
            	addresses.add(address);
            	sort(addresses);
            	redisUtil.put("addr_"+form.getUserId(), addresses);
            }
			return Success.SUCCESS;
		} catch (Exception e) {
	    	e.printStackTrace();
            return Error.APPSERVER;
		}
	}
    
    /**
     * 用户修改地址信息。
     * @param req 用户请求。
     * @return
     */
    @RequestMapping(value="/address/edit", method=RequestMethod.POST)
	public ResponseCode editAddress(HttpServletRequest req, 
			@RequestBody @Valid EditAddressForm form, BindingResult result) {
    	if (result.hasErrors()) {
            return ResponseCodeRepository.fetch(result.getFieldError().getDefaultMessage(), result.getFieldError().getField(), Error.ENTRY);
        }
		try {
			// 校验收货地址
			String key = "addr_"+form.getUserId();
            List<Address> addresses =  redisUtil.getJsonArray(key, Address.class);
            if(addresses==null){
				return RuleError.NON_EXISTENT("address"); 
            }
            Address address = null;
            int index = 0;
            for(int i=0; i< addresses.size(); i++){
            	Address add = addresses.get(i);
            	if(add.getId().equals(form.getId())){
            		address = add;
            		index = i; 
            	}
            }
            if(address==null){
				return RuleError.NON_EXISTENT("address"); 
            }
            
            // 检查地址参数信息
 			String query = new StringBuilder().append("provinceId=").append(form.getProvince())
 					.append("&cityId=").append(form.getCity()).append("&areaId=").append(form.getArea()).toString();
         	String addressResult = httpGet("https://basedata.dreawer.com/district/check", query);
         	if(StringUtils.isBlank(addressResult)){
				return Error.EXT_REQUEST("account");
 			}
         	JSONObject json = new JSONObject(addressResult);
 			if(!json.getBoolean("status")){
				return RuleError.NON_EXISTENT("district"); 
 			}
 			JSONObject data = json.getJSONObject("data");
 			JSONObject province = data.getJSONObject("province");
 			JSONObject city = data.getJSONObject("city");
 			JSONObject area = data.getJSONObject("area");
 			address.setName(form.getName());
            address.setPhoneNumber(form.getPhone());
            address.setProvince(province.getString("id"));
            address.setProvinceName(province.getString("name"));
            address.setCity(city.getString("id"));
            address.setCityName(city.getString("name"));
            address.setArea(area.getString("id"));
            address.setAreaName(area.getString("name"));
            address.setDetail(form.getDetail());
            Long now = System.currentTimeMillis();
            address.setUpdateTime(now);
            addresses.set(index, address);
        	// 同步缓存 
        	sort(addresses);
        	redisUtil.put(key, addresses);
			return Success.SUCCESS;
		} catch (Exception e) {
	    	e.printStackTrace();
            return Error.APPSERVER;
		}
	}
    
    /**
     * 用户删除地址信息。
     * @param req 用户请求。
     * @return
     */
    @RequestMapping(value="/address/delete", method=RequestMethod.GET)
	public ResponseCode deleteAddress(HttpServletRequest req, 
    		@RequestParam("id") String id, @RequestParam("userId") String userId) {
		try {
			// 校验收货地址
			String key = "addr_"+userId;
            List<Address> addresses =  redisUtil.getJsonArray(key, Address.class);
            if(addresses==null){
				return RuleError.NON_EXISTENT("address"); 
            }
            Address address = null;
            int index = 0;
            for(int i=0; i< addresses.size(); i++){
            	Address add = addresses.get(i);
            	if(add.getId().equals(id)){
            		address = add;
            		index = i; 
            	}
            }
            if(address==null){
				return RuleError.NON_EXISTENT("address"); 
            }
            addresses.remove(index);
            // 同步缓存 
            if(addresses.size()<=0){
            	redisUtil.delete(key);
            }else{
            	redisUtil.put(key, addresses);
            }
			return Success.SUCCESS;
		} catch (Exception e) {
	    	e.printStackTrace();
            return Error.APPSERVER;
		}
	}

    /**
     * 查询用户地址列表。
     * @param req 用户请求。
     * @return
     */
    @RequestMapping(value="/address/list", method=RequestMethod.GET)
	public ResponseCode list(HttpServletRequest req, @RequestParam("userId") String userId) {
		try {
			String key = "addr_"+userId;
            List<Address> addresses =  redisUtil.getJsonArray(key, Address.class);
			Map<String, Object> params = new HashMap<>();
			params.put("addresses", addresses);
			return Success.SUCCESS(params);
		} catch (Exception e) {
	    	e.printStackTrace();
            return Error.APPSERVER;
		}
	}
    
    /**
     * 查询用户地址列表。
     * @param req 用户请求。
     * @return
     */
    @RequestMapping(value="/address/detail", method=RequestMethod.GET)
	public ResponseCode detail(HttpServletRequest req, 
    		@RequestParam("id") String id, @RequestParam("userId") String userId) {
		try {
			String key = "addr_"+userId;
            List<Address> addresses =  redisUtil.getJsonArray(key, Address.class);
            if(addresses==null){
				return RuleError.NON_EXISTENT("address"); 
            }
            Address address = null;
            for(int i=0; i< addresses.size(); i++){
            	Address add = addresses.get(i);
            	if(add.getId().equals(id)){
            		address = add;
            	}
            }
            if(address==null){
				return RuleError.NON_EXISTENT("address"); 
            }
			Map<String, Object> params = new HashMap<>();
			params.put("address", address);
			return Success.SUCCESS(params);
		} catch (Exception e) {
	    	e.printStackTrace();
            return Error.APPSERVER;
		}
	}
    
    /**
     * 用户设置默认地址。
     * @param req 用户请求。
     * @return
     */
    @RequestMapping(value="/address/setDefault", method=RequestMethod.GET)
	public ResponseCode setDefault(HttpServletRequest req, 
    		@RequestParam("id") String id, @RequestParam("userId") String userId) {
		try {
			String key = "addr_"+userId;
            List<Address> addresses =  redisUtil.getJsonArray(key, Address.class);
            if(addresses==null){
				return RuleError.NON_EXISTENT("address"); 
            }
            Address address = null;
            int index = 0;
            for(int i=0; i< addresses.size(); i++){
            	Address add = addresses.get(i);
            	if(add.getId().equals(id)){
            		address = add;
            		index = i;
            	}
            }
            if(address==null){
				return RuleError.NON_EXISTENT("address"); 
            }
			if("DEFAULT".equals(address.getStatus())){
				return Success.SUCCESS;
			}
			for(int i=0; i< addresses.size(); i++){
            	Address add = addresses.get(i);
            	add.setStatus("USED");
            }
			address.setStatus("DEFAULT");
			addresses.set(index, address);
            // 同步缓存
        	sort(addresses);
        	redisUtil.put(key, addresses);
			return Success.SUCCESS;
		} catch (Exception e) {
	    	e.printStackTrace();
            return Error.APPSERVER;
		}
	}
    
    /**
     * 用户获取默认地址。
     * @param req 用户请求。
     * @return
     */
    @RequestMapping(value="/address/getDefault", method=RequestMethod.GET)
	public ResponseCode getDefault(HttpServletRequest req, @RequestParam("userId") String userId) {
		try {
			String key = "addr_"+userId;
            List<Address> addresses =  redisUtil.getJsonArray(key, Address.class);
            if(addresses==null){
    			return Success.SUCCESS;
            }
            Address address = null;
            for(int i=0; i< addresses.size(); i++){
            	Address add = addresses.get(i);
            	if("DEFAULT".equals(add.getStatus())){
            		address = add;
            	}
            }
            if(address==null){
    			return Success.SUCCESS;
            }
			Map<String, Object> params = new HashMap<>();
			params.put("address", address);
			return Success.SUCCESS(params);
		} catch (Exception e) {
	    	e.printStackTrace();
            return Error.APPSERVER;
		}
	}
    
    /**
     * 地址列表排序。
     * @param addresses 地址列表。
     * @return
     */
    private List<Address> sort(List<Address> addresses){
    	if(CollectionUtils.isEmpty(addresses)){
        	return addresses;
    	}
    	// 排序 默认1，其余按照更新时间排序
    	Collections.sort(addresses, new Address());
    	return addresses;
    }
}
