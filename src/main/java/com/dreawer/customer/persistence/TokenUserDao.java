package com.dreawer.customer.persistence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dreawer.customer.domain.TokenUser;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;

@Repository
public class TokenUserDao extends MyBatisBaseDao<TokenUser>{

	public TokenUser findTokenUserById(String id) {
		return selectOne("findTokenUserById", id);
	}

	public TokenUser findTokenUserByEmail(String email, String organizeId) {
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		params.put("organizeId", organizeId);
		return selectOne("findTokenUserByEmail", params);
	}

	public TokenUser findTokenUserByPhone(String phoneNumber, String organizeId) {
		Map<String, Object> params = new HashMap<>();
		params.put("phoneNumber", phoneNumber);
		params.put("organizeId", organizeId);
		return selectOne("findTokenUserByPhone", params);
	}
	

}
