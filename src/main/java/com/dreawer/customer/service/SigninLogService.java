package com.dreawer.customer.service;

import com.dreawer.customer.domain.SignInLog;
import com.dreawer.customer.persistence.SigninLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SigninLogService {
	
    @Autowired
    private SigninLogDao signinLogDao; // 登陆信息DAO
    
    
    /**
     * 保存登录日志。
     * @param log 用户登录成功日志。
     */
    public int save(SignInLog log){
    	return signinLogDao.save(log);
    }
    

}
