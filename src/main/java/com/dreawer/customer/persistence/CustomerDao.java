package com.dreawer.customer.persistence;

import com.dreawer.customer.domain.Customer;
import com.dreawer.customer.lang.UserStatus;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomerDao extends MyBatisBaseDao<Customer> {
    
    
    /**
     * 保存客户信息。
     * @param customer 客户。
     * @return 成功保存的记录数。
     */
    public int save(Customer customer) {
        return insert("save", customer);
    }
    
    /**
     * 更新客户基本信息。
     * @param customer 待更新的客户。
     * @return 更新实例总数。如果更新成功则为1，否则为0。
     */
    public int updateBasic(Customer customer) {
        return update("updateBasic", customer);
    }
    
    /**
     * 查询客户信息。
     * @param id 客户信息ID。
     * @return 客户信息。
     */
	public Customer findCustomerById(String id) {
		return selectOne("findCustomerById", id);
	}
	
	/**
	 * 更新用户状态。
	 * @param status 用户状态。
	 */
	public int updateStatus(Customer customer, UserStatus status) {
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("customer", customer);
        params.put("status", status);
		return update("updateStatus", params);
	}

}
