package com.dreawer.customer.web;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
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
import com.dreawer.customer.utils.SystemUtils;
import com.google.gson.Gson;
import io.jstack.sendcloud4j.SendCloud;
import io.jstack.sendcloud4j.mail.Email;
import io.jstack.sendcloud4j.mail.Result;
import io.jstack.sendcloud4j.mail.Substitution.Sub;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

import static com.dreawer.customer.constants.ControllerConstants.*;

public class BaseController{
	
    @Autowired
    private RedisUtil redisUtil; // redis信息服务
	
    @Autowired
    private TokenUserService tokenuserService; // 用户信息服务
    
    @Autowired
    private SequenceService sequenceService; // 业务序列号服务
    
    @Autowired
    private SigninLogService signinLogService; // 登录日志服务
    
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
    	TokenUser tokenUser = tokenuserService.findTokenUserById(oldUser.getId());
        String token = "";
    	redisUtil.setTokenUser(token, tokenUser);
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
    		mugshot = "/images/def_photo.jpg";
    	}
		return mugshot;
    }
    
	/**
     * 阿里云发送短信。
     * @param phoneNumber 手机号。
     * @param value 验证信息。
     * @throws ClientException
     */
    protected SendSmsResponse sendSMSByAliyun(String phoneNumber, String templateCode, String value) throws ClientException{
    	
   	 	//可自助调整超时时间
    	// System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
    	// System.setProperty("sun.net.client.defaultReadTimeout", "10000");

    	//初始化acsClient,暂不支持region化
    	IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", SMS_API_KEY, SMS_API_SECRET);
    	DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
    	IAcsClient acsClient = new DefaultAcsClient(profile);

    	//组装请求对象-具体描述见控制台-文档部分内容
    	SendSmsRequest request = new SendSmsRequest();
    	//必填:待发送手机号
    	request.setPhoneNumbers(phoneNumber);
    	//必填:短信签名-可在短信控制台中找到
    	request.setSignName("极乐科技");
    	//必填:短信模板-可在短信控制台中找到
    	request.setTemplateCode(templateCode);
    	//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    	request.setTemplateParam("{\"number\":\"" + value + "\"}");

    	//选填-上行短信扩展码(无特殊需求用户请忽略此字段)
    	//request.setSmsUpExtendCode("90997");

    	//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
    	request.setOutId("dreawer");

    	//hint 此处可能会抛出异常，注意catch
    	SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

    	return sendSmsResponse;
    }	
    
    /**
     * 发送邮件。
     * @param map 邮件内容参数。
     * @param params sendcloud发件参数。
     * @return
     */
    @SuppressWarnings("rawtypes")
	protected Result sendEmailBySendCloud(Map<String, String> map, Map<String, String> params) {
		//装载用户信息和密钥
    	String apiKey = SENDCLOUD_API_KEY;
    	SendCloud webapi = SendCloud.createWebApi(params.get(API_USER), apiKey);
    	Sub sub = new Sub();
		for (String key : map.keySet()) {
			sub.set(key, map.get(key));
		}
        Email email = Email.template(params.get(RECEIVE_TEMPLATE))
                .from(params.get(SENDCLOUD_USERTYPE))
                .fromName(SENDCLOUD_SEND_NAME)
                .substitutionVars(sub)
                .to(params.get(RECEIVE_USER));
        Result result = webapi.mail().send(email);
    	return result;
    }
    
    /**
	 * 生成四位数字验证码，并放置redis中。key为"captcha_"+手机号或者邮箱。
	 * @param key 
	 * @return
	 */
	public String createCaptcha(String key) {
		String value = (int) Math.round(Math.random() * (9999-1000) +1000)+"";
		redisUtil.put("captcha_"+key, value, 10*60L);
		return value;
	}
	
	/**
	 * 通过key删除redis中的验证码。
	 * @param key
	 */
	public void removeCaptcha(String key) {
		redisUtil.delete("captcha_"+key);
	}
	
	/**
	 * 验证验证码是否正确。
	 * @param key 验证的key。
	 * @param captcha 输入的验证码。
	 * @return 校验结果。如果正确返回ture，否则返回false。
	 */
	public boolean isCaptchaValid(String key, String captcha) {
		String value = redisUtil.getString("captcha_"+key);
		if(StringUtils.isBlank(value) || StringUtils.isBlank(captcha) || !captcha.equals(value)) {
			return false;
		}
		return true;
	}

    protected boolean verifyPhone(String phoneNumber, String captcha) throws JSONException {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phoneNumber);
        params.put("captcha", captcha);
        String result = httpPost("https://account.dreawer.com/verify/phone/commen", params);
        if(StringUtils.isNotBlank(result)){
            JSONObject json = new JSONObject(result);
            if(json!=null && json.getBoolean("status")){
                return true;
            }
        }
        return false;
    }

    /**
     * 将对象转换成json。
     * @param object 对象
     * @return json。
     * @author Kael
     * @since 2.0
     */
    protected String objectToJson(Object object){
        if (object == null) {
            return null;
        }

        String json = new Gson().toJson(object);

        return json;
    }



}
