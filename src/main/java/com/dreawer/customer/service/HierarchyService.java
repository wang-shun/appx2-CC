package com.dreawer.customer.service;

import com.dreawer.customer.domain.Hierarchy;
import com.dreawer.customer.persistence.HierarchyMapper;
import com.dreawer.customer.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <CODE>HierarchyService</CODE>
 * 权益Service
 *
 * @author fenrir
 * @Date 18-3-26
 */

@Service
public class HierarchyService extends BaseService {


    @Autowired
    HierarchyMapper hierarchyMapper;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 根据店铺ID查询会员等级
     * @param storeId 店铺ID
     * @return 权益列表
     */
    public List<Hierarchy> findByStoreId(String storeId){
        List<Hierarchy> list = hierarchyMapper.findByStoreId(storeId);
        //Map<String, Object> map = convertMap(list);
        return list;
    }

    //@Transactional
    public Hierarchy addHierarchy(Hierarchy hierarchy){
        hierarchyMapper.insert(hierarchy);
        //Map<String, Object> map = convertMap(hierarchy);
        return hierarchy;
    }

    /**
     * 根据ID查询会员等级
     * @param id 等级ID
     * @return 权益列表
     */
    public Hierarchy findById(String id)  {
        return hierarchyMapper.findById(id);

    }



    /**
     * 更新会员等级
     * @param hierarchy 会员等级实体类
     */
    //@Transactional
    public Hierarchy update(Hierarchy hierarchy) {
        hierarchyMapper.update(hierarchy);
        return hierarchy;
    }

    /**
     * 查询所有开启会员的店铺ID
     * @return 店铺ID列表
     */
    public List<String> findAllStores() {
        return hierarchyMapper.findAllStores();
    }
}
