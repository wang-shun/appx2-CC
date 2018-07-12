package com.dreawer.customer.utils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import com.dreawer.customer.domain.SystemInfo;
import com.dreawer.customer.domain.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@Component("redisUtil")
public class RedisUtil {

	@Resource(name="redisTemplate") 
    private StringRedisTemplate redisTemplate;//redis操作模板  
    
    private static final String userPrefix = "user_";

    
    /**
     * 存放数据。
     * @param key 存放数据的key。
     * @param value 存放的数据。
     */
    public void put(String key, Object value) {  
    	if (StringUtils.isBlank(key) || value==null) {  
            return;  
        } 
        redisTemplate.opsForValue().set(key, new Gson().toJson(value));  
    } 
    
    /**
     * 存放带有过期时间的数据。
     * @param key 存放数据的key。
     * @param value 存放的数据。
     * @param time 过期时间，单位为秒。
     */
    public void put(String key, Object value, Long time) {  
    	if (StringUtils.isBlank(key) || value==null || time==null) {  
            return;  
        } 
        redisTemplate.opsForValue().set(key, new Gson().toJson(value), time, TimeUnit.SECONDS);  
    } 
    
    /**
     * 获取String类型的数据。
     * @param key 要取出数据的主键。
     * @return json格式的字符串。
     */
    public String getString(String key){
    	if(StringUtils.isBlank(key)){
            return null;  
    	}
    	String value = redisTemplate.opsForValue().get(key);
    	if(StringUtils.isBlank(value)){  
            return null;  
        }
    	return new Gson().fromJson(value, String.class);
    }
    
    /**
     * 返回指定类型的数据。
     * @param key 要取出数据的主键。
     * @param clazz 要取出的数据class类型。
     * @return 取出的数据。
     */
    public <T> T get(String key, Class<T> clazz){
    	if(StringUtils.isBlank(key) || clazz==null){
            return null;  
    	}
    	String value = redisTemplate.opsForValue().get(key);
    	if(StringUtils.isBlank(value)){  
            return null;  
        }  
    	return new Gson().fromJson(value, clazz);
    }
    
    /**
     * 取出对象类型的数据。
     * @param key 要取出数据的主键。
     * @return 取出的数据。
     */
    public JsonObject getJsonObject(String key) {  
    	if(StringUtils.isBlank(key)){
            return null;  
    	}
        String value = redisTemplate.opsForValue().get(key);  
        if(StringUtils.isBlank(value)){  
            return null;  
        }  
		return new Gson().fromJson(value, new TypeToken<JsonObject>() {}.getType());
    }  
  
    /**
     * 取出数组类型的数据。
     * @param key 要取出数据的主键。
     * @param clazz 要取出的数据class类型。
     * @return 取出的数据。
     */
    public <T> List<T> getJsonArray(String key, Class<T> clazz) {  
    	if(StringUtils.isBlank(key) || clazz==null){
            return null;  
    	}
    	String value = redisTemplate.opsForValue().get(key);  
    	if(StringUtils.isBlank(value)){  
            return null;  
        } 
    	// 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        // 根据List<T>生成完整的Result<List<T>>
        //Type type = new ParameterizedTypeImpl(Result.class, new Type[]{listType});
		return new Gson().fromJson(value, listType);
    }
    
    /**
     * 从redis中获取用户信息。
     * @param key 要取出数据的主键。
     * @return 用户信息。
     */
    public User getRedisUser(String key){
    	if(StringUtils.isBlank(key)){
            return null;  
    	}
    	String value = redisTemplate.opsForValue().get(userPrefix + key);  
    	if(StringUtils.isBlank(value)){  
            return null;  
        } 
		return new Gson().fromJson(value, User.class);
    }
    
    
    /**
     * 把用户信息放到redis中。默认增加前缀"user_",过期时间24小时。
     * @param key 要存放数据的主键。
     * @param user 用户信息。
     */
    public void setRedisUser(String key, User user) {
    	if (StringUtils.isBlank(key) || user==null) {  
            return;  
        } 
        redisTemplate.opsForValue().set(userPrefix + key, new Gson().toJson(user), 20*60*60L, TimeUnit.SECONDS);  
	}
    
    /**
     * 设置系统信息。
     * @param key
     * @param systemInfo
     */
    public void setRedisSystemInfo(String key, SystemInfo systemInfo){
    	if (StringUtils.isBlank(key)) {  
            return;  
        } 
        redisTemplate.opsForValue().set("system_" + key, new Gson().toJson(systemInfo), 20*60*60L, TimeUnit.SECONDS);  
    }
    
    /**
     * 获取系统信息。
     * @param key
     * @return
     */
    public SystemInfo getRedisSystemInfo(String key){
    	if(StringUtils.isBlank(key)){
            return null;  
    	}
    	String value = redisTemplate.opsForValue().get("system_" + key);  
    	if(StringUtils.isBlank(value)){  
            return null;  
        } 
		return new Gson().fromJson(value, SystemInfo.class);
    }
    
    /**
     * 删除redis中的用户信息。
     * @param key 要删除数据的主键。
     */
    public void deleteRedisUser(String key) {
    	if (StringUtils.isBlank(key)) {  
            return;  
        } 
    	redisTemplate.delete(userPrefix + key);
    	redisTemplate.delete("system_" + key);
	}
    
    /**
     * 删除数据。
     * @param key 要删除数据的主键。
     */
    public void delete(String key){
    	if(StringUtils.isBlank(key)){  
            return;  
        }  
    	redisTemplate.delete(key);
    }

}
