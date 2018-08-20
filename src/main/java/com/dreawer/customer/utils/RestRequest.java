package com.dreawer.customer.utils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestRequest {

    @Autowired
    private RestTemplate restTemplate;
    
    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器

    public boolean isCaptchaValid(String name, String value){
    	try {
    		Map<String, String> params = new HashMap<>();
        	params.put("address", name);
        	params.put("code", value);
            String response = restPost("http://nc/api/checkedVerifyCode", new Gson().toJson(params));
            JSONObject responseCode = new JSONObject(response);
            if (responseCode.getString("code").equals("000000")) {
                return true;
            }
            return false;
    	}catch(Exception e) {
    		logger.error(e);
            return false;
    	}
    	
    }
    
    public String restPost(String url, String data){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<>(data, headers);
        String response = restTemplate.postForObject(url, entity, String.class);
        logger.error(response);
        return response;
	}

    public String restGet(String url){
        String response = restTemplate.getForObject(url, String.class);
        logger.error(response);
        return response;
	}

}
