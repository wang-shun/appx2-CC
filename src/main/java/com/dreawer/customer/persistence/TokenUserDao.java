package com.dreawer.customer.persistence;

import com.dreawer.customer.domain.TokenUser;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public List<TokenUser> findUsers(String organizeId, String query, int start, int pageSize, Timestamp startTime,
			Timestamp endTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("organizeId", organizeId);
		params.put("query", query);		
		params.put("start", start);		
		params.put("pageSize", pageSize);		
		params.put("startTime", startTime);		
		params.put("endTime", endTime);		
		return selectList("findUsers", params);
	}

	public int findUsersCount(String organizeId, String query, Timestamp startTime, Timestamp endTime) {
		Map<String, Object> params = new HashMap<>();
		params.put("organizeId", organizeId);
		params.put("query", query);		
		params.put("startTime", startTime);		
		params.put("endTime", endTime);		
		return count("findUsersCount", params);
	}
	

}
