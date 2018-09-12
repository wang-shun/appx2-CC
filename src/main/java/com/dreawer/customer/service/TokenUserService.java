package com.dreawer.customer.service;

import com.dreawer.customer.domain.TokenUser;
import com.dreawer.customer.persistence.TokenUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TokenUserService {

	@Autowired
	private TokenUserDao tokenUserDao;
	
	public TokenUser findTokenUserById(String id) {
		return tokenUserDao.findTokenUserById(id);
	}
	
	public TokenUser findTokenUserByEmail(String email, String organizeId) {
		return tokenUserDao.findTokenUserByEmail(email, organizeId);
	}
	
	public TokenUser findTokenUserByPhone(String phoneNumber, String organizeId) {
		return tokenUserDao.findTokenUserByPhone(phoneNumber, organizeId);
	}

	public List<TokenUser> findUsers(String organizeId, String query, int start, int pageSize, Timestamp startTime,
			Timestamp endTime) {
		return tokenUserDao.findUsers(organizeId, query, start, pageSize, startTime, endTime);
	}

	public int findUsersCount(String organizeId, String query, Timestamp startTime, Timestamp endTime) {
		return tokenUserDao.findUsersCount(organizeId, query, startTime, endTime);
	}
	
	
}
