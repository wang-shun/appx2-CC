package com.dreawer.customer.persistence;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.dreawer.customer.domain.User;
import com.dreawer.customer.lang.UserStatus;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;

@Repository
public class UserDao extends MyBatisBaseDao<User> {
    
    /**
     * 保存用户信息。
     * @param user 用户。
     * @return 成功保存的记录数。
     */
    public int save(User user) {
        return insert("save", user);
    }
    
    /**
	 * 更新用户状态。
	 * @param user 用户信息。
	 * @param status 用户状态。
	 */
	public int updateStatus(User user, UserStatus status) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("user", user);
        params.put("status", status);
		return update("updateStatus", params);
	}
	
	/**
	 * 修改用户基本信息。
	 * @param user
	 * @return
	 */
	public int updatePassword(User user) {
		return update("updatePassword", user);
	}
	
	/**
	 * 修改用户基本信息。
	 * @param user
	 * @return
	 */
	public int updateBasic(User user) {
		return update("updateBasic", user);
	}
	
	/**
     * 查询用户信息。
     * @param id 用户ID号。
     * @return 用户实例。如果用户存在则返回相匹配的用户实例，否则返回null。
     */
    public User findUserById(String id) {
        return selectOne("findUserById", id);
    }
    
	/**
	 * 通过邮箱查找用户。
	 * @param email
	 * @return
	 */
	public User findUserByEmail(String email, String appId) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("appId", appId);
		return selectOne("findUserByEmail", params);
	}

	/**
	 * 通过手机号查找用户。
	 * @param phoneNumber 手机号。
	 * @param appId 
	 * @return
	 */
	public User findUserByPhone(String phoneNumber, String appId) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("phoneNumber", phoneNumber);
        params.put("appId", appId);
		return selectOne("findUserByPhone", params);
	}

}
