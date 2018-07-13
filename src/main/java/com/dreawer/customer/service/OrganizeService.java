package com.dreawer.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreawer.customer.domain.Organize;
import com.dreawer.customer.persistence.OrganizeDao;

@Service
public class OrganizeService {
	
    @Autowired
    private OrganizeDao organizeDao;

	/***
	 * 保存组织信息。
	 * @param organize
	 * @return
	 */
    public int save(Organize organize) {
    	return organizeDao.save(organize);
    }
    
    /**
	 * 通过应用id查询组织信息。
	 * @param appId
	 * @return
	 */
	public Organize findOrganizeByAppId(String appId) {
		return organizeDao.findOrganizeByAppId(appId);
	}
    
}
