package com.dreawer.customer.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreawer.customer.domain.Sns;
import com.dreawer.customer.lang.BindingType;
import com.dreawer.customer.persistence.SnsDao;

@Service
public class SnsService {
	
    @Autowired
    private SnsDao snsDao; // snsDAO

    /**
     * 通过uid查询社交媒体信息。
     * @param id 社交媒体的uid。
     * @param type 社交媒体的类型。
     * @return 社交媒体信息。如果存在返回社交媒体信息，否则返回null。
     */
	public Sns findSnsByUid(String id, BindingType type) {
		return snsDao.findSnsByUid(id, type);
	}
    
	/**
	 * 通过微信id查询社交媒体信息。
	 * @param wxid 微信企业唯一id。
	 * @return 社交媒体信息列表。如果存在返回社交媒体信息列表，否则返回长度为0的列表。
	 */
	public List<Sns> findSnsByWxid(String wxid) {
		return snsDao.findSnsByWxid(wxid);
	}

	/**
	 * 保存社交媒体信息。
	 * @param sns
	 * @return
	 */
	public int save(Sns sns) {
		return snsDao.save(sns);
	}

	/**
	 * 删除sns信息。
	 * @param sns
	 * @return
	 */
	public int deleteByType(Sns sns) {
		return snsDao.deleteByType(sns);
	}
    
}
