package com.dreawer.customer.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dreawer.customer.domain.Sns;
import com.dreawer.customer.lang.BindingType;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;

@Repository
public class SnsDao extends MyBatisBaseDao<Sns> {

    /**
     * 通过uid查询社交媒体信息。
     * @param id 社交媒体的uid。
     * @param type 社交媒体的类型。
     * @return 社交媒体信息。如果存在返回社交媒体信息，否则返回null。
     */
	public Sns findSnsByUid(String id, BindingType type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		params.put("id", id);
		return selectOne("findSnsByUid", params);
	}

	/**
	 * 保存社交媒体信息。
	 * @param sns
	 * @return
	 */
	public int save(Sns sns) {
		return insert("save", sns);
	}

	/**
	 * 通过微信id查询社交媒体信息。
	 * @param wxid 微信企业唯一id。
	 * @return 社交媒体信息列表。如果存在返回社交媒体信息列表，否则返回长度为0的列表。
	 */
	public List<Sns> findSnsByWxid(String wxid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", BindingType.WEIXIN);
		params.put("id", wxid);
		return selectList("findSnsByWxid", params);
	}

	/**
	 * 删除sns信息。
	 * @param sns 媒体信息。
	 * @return
	 */
	public int deleteByType(Sns sns) {
		return delete("deleteByType", sns);
	}

}
