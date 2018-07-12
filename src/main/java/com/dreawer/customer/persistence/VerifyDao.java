package com.dreawer.customer.persistence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dreawer.customer.domain.Verify;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;

@Repository
public class VerifyDao extends MyBatisBaseDao<Verify> {

    /**
     * 保存认证信息。
     * @param verify 认证信息。
     * @return
     */
	public int save(Verify verify) {
		return insert("save", verify);
	}

	/**
	 * 查询认证信息。
	 * @param verify 认证信息。
	 * @return
	 */
	public Verify findVerify(Verify verify) {
		return selectOne("findVerify", verify);
	}
	
	/**
	 * 查询认证信息。
	 * @param verify 认证信息。
	 * @return
	 */
	public Verify findVerifyByPhone(String phone, String value) {
		Map<String, Object> params = new HashMap<>();
		params.put("phone", phone);
		params.put("value", value);
		return selectOne("findVerifyByPhone", params);
	}
	
	/**
	 * 查询认证信息。
	 * @param verify 认证信息。
	 * @return
	 */
	public Verify findVerifyByEmail(String email, String value) {
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		params.put("value", value);
		return selectOne("findVerifyByEmail", params);
	}
	
	/**
	 * 删除认证信息。
	 * @param verify 认证信息。
	 * @return
	 */
	public int delete(Verify verify) {
		return delete("delete", verify);
	}

	/**
	 * 更新认证信息。
	 * @param verify 认证信息。
	 * @return
	 */
	public int update(Verify verify) {
		return update("update", verify);
	}

	
	
}
