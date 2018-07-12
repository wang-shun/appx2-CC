package com.dreawer.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreawer.customer.domain.Verify;
import com.dreawer.customer.persistence.VerifyDao;

@Service
public class VerifyService {

    @Autowired
    private VerifyDao verifyDao; // 认证信息DAO

    /**
     * 保存认证信息。
     * @param verify 认证信息。
     * @return
     */
	public int save(Verify verify) {
		return verifyDao.save(verify);
	}

	/**
	 * 查询认证信息。
	 * @param verify 认证信息。
	 * @return
	 */
	public Verify findVerify(Verify verify) {
		return verifyDao.findVerify(verify);
	}
	
	/**
	 * 查询认证信息。
	 * @param verify 认证信息。
	 * @return
	 */
	public Verify findVerifyByPhone(String phone, String value) {
		return verifyDao.findVerifyByPhone(phone, value);
	}

	/**
	 * 查询认证信息。
	 * @param verify 认证信息。
	 * @return
	 */
	public Verify findVerifyByEmail(String email, String value) {
		return verifyDao.findVerifyByEmail(email, value);
	}
	
	/**
	 * 删除认证信息。
	 * @param verify 认证信息。
	 * @return
	 */
	public int delete(Verify verify) {
		return verifyDao.delete(verify);
	}

	/**
	 * 更新认证信息。
	 * @param verify 认证信息。
	 * @return
	 */
	public int update(Verify verify) {
		return verifyDao.update(verify);
	}
    
    
    
}
