package com.dreawer.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreawer.customer.domain.TokenUser;
import com.dreawer.customer.persistence.TokenUserDao;

@Service
public class TokenUserService {

	@Autowired
	private TokenUserDao tokenUserDao;
	
	public TokenUser findTokenUserById(String id) {
		return tokenUserDao.findTokenUserById(id);
	}
	
	public TokenUser findTokenUserByEmail(String email, String appId) {
		return tokenUserDao.findTokenUserByEmail(email, appId);
	}
	
	public TokenUser findTokenUserByPhone(String phoneNumber, String appId) {
		return tokenUserDao.findTokenUserByPhone(phoneNumber, appId);
	}
	
	
	
}
