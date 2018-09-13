package com.dreawer.customer.web;

import com.dreawer.customer.domain.Sequence;
import com.dreawer.customer.domain.SignInLog;
import com.dreawer.customer.domain.SystemInfo;
import com.dreawer.customer.domain.TokenUser;
import com.dreawer.customer.lang.ClientType;
import com.dreawer.customer.lang.SignInStatus;
import com.dreawer.customer.service.SequenceService;
import com.dreawer.customer.service.SigninLogService;
import com.dreawer.customer.service.TokenUserService;
import com.dreawer.customer.utils.PinyinUtils;
import com.dreawer.customer.utils.RedisUtil;
import com.dreawer.customer.utils.RestRequest;
import com.dreawer.customer.utils.SystemUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;


public class BaseController{
	
    @Autowired
    private RedisUtil redisUtil; // redis信息服务
	
    @Autowired
    private TokenUserService tokenuserService; // 用户信息服务
    
    @Autowired
    private SequenceService sequenceService; // 业务序列号服务
    
    @Autowired
    private SigninLogService signinLogService; // 登录日志服务

    @Autowired
    protected RestRequest restRequest;
    
    /**
     * 登录用户。
     * @param req 用户请求。
     */
    protected String signInUser(HttpServletRequest req, String userId) {
    	TokenUser tokenUser = tokenuserService.findTokenUserById(userId);
        String ip = getIpAddress(req);
        String mac = "unknow";
        String browser = SystemUtils.getBrowser(req);
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setIp(ip);
        systemInfo.setMac(mac);
        systemInfo.setBrowser(browser);
        String token = UUID.randomUUID().toString().replace("-", "");
    	redisUtil.setTokenUser(token, tokenUser);
    	redisUtil.setRedisSystemInfo(token, systemInfo);
    	
		// 保存登陆日志
		SignInLog log = new SignInLog();
		log.setUserId(tokenUser.getId());
		log.setIp(ip);
		log.setStatus(SignInStatus.SUCCESS);
		log.setType(ClientType.PAGE);
		signinLogService.save(log);
		return token;
    }
    
    /**
     * 更新用户登录信息。
     * @param req 用户请求。
     */
    protected void updateSignInUser(HttpServletRequest req) {
    	TokenUser oldUser = getSignInUser(req);
    	String token = req.getHeader("token");
    	if(oldUser!=null && StringUtils.isNotBlank(token)) {
        	TokenUser tokenUser = tokenuserService.findTokenUserById(oldUser.getId());
    		redisUtil.setTokenUser(token, tokenUser);
    	}
    }
    
	/**
	 * 获取登录的用户。
	 * @param req 用户请求。
	 * @return 用户信息。
	 */
	protected TokenUser getSignInUser(HttpServletRequest req) {
		TokenUser tokenUser = null;
		String token = req.getHeader("token");
		if(StringUtils.isNotBlank(token)){
			tokenUser =  redisUtil.getTokenUser(token);
		}else{
			token = req.getParameter("token");
			if(StringUtils.isNotBlank(token)){
				tokenUser =  redisUtil.getTokenUser(token);
			}
		}
        return tokenUser;
    }
	
	/**
	 * 删除登录的用户。
	 * @param req 用户请求。
	 */
	protected void removeSignInUser(HttpServletRequest req){
		String token = req.getHeader("token");
		redisUtil.deleteRedisUser(token);
	}
	
    /**
     * http的post请求。
     * @param url 请求的地址。
     * @param params 请求的参数。
     * @return 返回结果。
     */
    protected String httpPost(String url, Map<String, String> params) { 
    	String result = null;
    	RequestConfig config = RequestConfig.custom()
    			.setSocketTimeout(10000)
    			.setConnectTimeout(10000)
    			.setConnectionRequestTimeout(10000)
    			.build();
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);  
        httppost.setConfig(config);
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        //遍历参数
	    for(String key : params.keySet()){
	    	formparams.add(new BasicNameValuePair(key, params.get(key)));  
	    }
        try {  
        	UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
            	if(response.getStatusLine().getStatusCode()==200){
            		 HttpEntity entity = response.getEntity();  
                     if (entity != null) { 
                    	 result = EntityUtils.toString(entity, "UTF-8");
                     }  
            	}
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return result;
    } 
    
    /**
     * http的get请求。
     * @param url 请求地址。
     * @param query 请求参数。
     */
    protected String httpGet(String url, String query) { 
    	String result = null;
    	RequestConfig config = RequestConfig.custom()
    			.setSocketTimeout(10000)
    			.setConnectTimeout(10000)
    			.setConnectionRequestTimeout(10000)
    			.build();
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url +"?"+ query);
            httpget.setConfig(config);
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
            	if(response.getStatusLine().getStatusCode()==200){
            		 // 获取响应实体    
                    HttpEntity entity = response.getEntity();  
                    if (entity != null) {  
                        result =  EntityUtils.toString(entity);  
                    }  
            	}
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
        return result;
    } 
    
    /**
     * json格式的http请求。
     * @param url 请求地址。
     * @param json 请求数据。
     * @return
     */
    protected String httpJson(String url, JSONObject json) {
    	String result = null; 
    	RequestConfig config = RequestConfig.custom()
    			.setSocketTimeout(10000)
    			.setConnectTimeout(10000)
    			.setConnectionRequestTimeout(10000)
    			.build();
    	CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(config);
            httpPost.addHeader("Content-Type", "application/json");

            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");

            httpPost.setEntity(stringEntity);

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {  
            	if(response.getStatusLine().getStatusCode()==200){
            		 // 获取响应实体    
                    HttpEntity entity = response.getEntity();  
                    if (entity != null) {  
                        result =  EntityUtils.toString(entity);  
                    }  
            	}
            } finally {  
                response.close();  
            }  

        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
        return result;
    }
    
    // --------------------------------------------------------------------------------
    // getRole
    // --------------------------------------------------------------------------------
    
    /**
     * 获取指定昵称（简称）的序列号。
     * @param petName 昵称（简称）。 
     * @return 序号。
     * @author David Dai
     * @since 2.0
     */
    protected String getAlias(String petName) {
        Sequence sequence = sequenceService.getSequence(petName);
        if (sequence == null) {
            sequence = new Sequence();
            sequence.setName(petName);
            sequence.setValue(1);
            sequenceService.add(sequence);
        } else {
            sequence.setValue(sequence.getValue() + 1);
            sequence.setUpdateTime(getNow());
            sequenceService.update(sequence);
        }
        String name = PinyinUtils.spell(petName);
        if(StringUtils.isBlank(name)){
        	name = petName;
        }
        return name + "-" + sequence.getValue();
    }
    
    /**
     * 获取当前系统时间。
     * @return 当前系统时间。
     * @author David Dai
     * @since 2.0
     */
    protected Timestamp getNow() {
        return new Timestamp(System.currentTimeMillis());
    }
    
    /** 
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址; 
     * @param request 用户请求。
     * @return 用户真实IP
     * @author Sdanly
     * @since 2.0      
     * */  
    protected String getIpAddress(HttpServletRequest request) {
       // 获取请求主机IP地址，如果通过代理进来，则透过防火墙获取真实IP地址 
       String ip = new String();
       ip = request.getHeader("X-Forwarded-For");
       
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("Proxy-Client-IP");
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("WL-Proxy-Client-IP");
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("HTTP_CLIENT_IP");
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("HTTP_X_FORWARDED_FOR");
           }
           if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getRemoteAddr();
           }
       } else if (ip.length() > 15) {
           String[] ips = ip.split(",");
           for (int index = 0; index < ips.length; index++) {
               String strIp = (String) ips[index];
               if (!("unknown".equalsIgnoreCase(strIp))) {
                   ip = strIp;
                   break;
               }
           }
       }
       return ip;
    }
    
    /**
     * 转换user对象。
     * @return
     */
    protected Map<String, String> convertUser(TokenUser tokenUser){
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("id", tokenUser.getId());
    	params.put("email", tokenUser.getEmail());
    	params.put("phoneNumber", tokenUser.getPhoneNumber());
    	params.put("petName", tokenUser.getPetName());
    	params.put("mugshot", tokenUser.getMugshot());
    	return params;
    }
    
    /**
     * 转换user集合。
     * @param userList 用户列表。
     * @return
     */
    protected List<Map<String, String>> convertUserList(List<TokenUser> userList){
    	List<Map<String, String>> users = new ArrayList<Map<String, String>>();
    	if(userList!=null && userList.size()>0){
    		for(TokenUser tokenUser : userList){
    	    	Map<String, String> params = new HashMap<String, String>();
    			params.put("id", tokenUser.getId());
    	    	params.put("email", tokenUser.getEmail());
    	    	params.put("phoneNumber", tokenUser.getPhoneNumber());
    	    	params.put("petName", tokenUser.getPetName());
    	    	params.put("mugshot", tokenUser.getMugshot());
    	    	users.add(params);
    		}
    	}
    	return users;
    }
    
    /**
     * 获取用户昵称。
     * @param petName 昵称字符串。
     * @return 若昵称为空，返回默认昵称，否则返回原昵称。
     */
    protected String getPetName(String petName){
    	String reg = "[^0-9a-zA-Z\u4e00-\u9fa5]+";
    	petName = petName.replaceAll(reg,"");
    	if(StringUtils.isBlank(petName)){
    		petName = "极乐用户";
    	}
		return petName;
    }
    
    /**
     * 获取头像。
     * @param mugshot 头像。
     * @return
     */
    protected String getMugshot(String mugshot){
    	if(StringUtils.isBlank(mugshot)){
    		mugshot = "/images/default-photo.png";
    	}
		return mugshot;
    }
    
	/**
	 * 验证验证码是否正确。
	 * @param key 验证的key。
	 * @param captcha 输入的验证码。
	 * @return 校验结果。如果正确返回ture，否则返回false。
	 */
	/*public boolean isCaptchaValid(String key, String captcha) {
		String value = redisUtil.getString("captcha_"+key);
		if(StringUtils.isBlank(value) || StringUtils.isBlank(captcha) || !captcha.equals(value)) {
			return false;
		}
		return true;
	}*/

}
