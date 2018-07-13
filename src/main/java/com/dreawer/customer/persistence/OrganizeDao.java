package com.dreawer.customer.persistence;

import org.springframework.stereotype.Repository;

import com.dreawer.customer.domain.Organize;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;

@Repository
public class OrganizeDao extends MyBatisBaseDao<Organize> {

	/***
	 * 保存组织信息。
	 * @param organize
	 * @return
	 */
	public int save(Organize organize) {
		return insert("save", organize);
	}
	
	/**
	 * 通过应用id查询组织信息。
	 * @param appId
	 * @return
	 */
	public Organize findOrganizeByAppId(String appId) {
		return selectOne("findOrganizeByAppId", appId);
	}

}
