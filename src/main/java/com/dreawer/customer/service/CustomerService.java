package com.dreawer.customer.service;

import com.dreawer.customer.domain.Customer;
import com.dreawer.customer.persistence.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao; // 客户信息DAO
    
    /**
     * 查询客户信息。
     * @param id 客户信息ID。
     * @return 客户信息。
     */
	public Customer findCustomerById(String id) {
		return customerDao.findCustomerById(id);
	}

	/**
	 * 更新客户基本信息。
	 * @param customer
	 * @return
	 */
	public int updateBasic(Customer customer) {
		return customerDao.updateBasic(customer);
	}
	
}
