package com.dreawer.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreawer.customer.domain.Sequence;
import com.dreawer.customer.persistence.SequenceDao;

@Service
public class SequenceService {
    
    @Autowired
    private SequenceDao sequenceDao; // 业务序列DAO

    /**
     * 添加序列。
     * @param sequence 序列。
     * @return 成功添加总数。
     * @author David Dai
     * @since 1.0
     */
    public int add(Sequence sequence) {
        return sequenceDao.save(sequence);
    }
    
    /**
     * 更新序列。
     * @param sequence 序列。
     * @return 成功更新总数。
     * @author David Dai
     * @since 1.0
     */
    public int update(Sequence sequence) {
        return sequenceDao.update(sequence);
    }
    
    /**
     * 获取指定名称的序列。
     * @param name 序列名称。
     * @return 序列。如果序列存在则返回序列对象，否则返回null。
     * @author David Dai
     * @since 1.0
     */
    public Sequence getSequence(String name) {
        return sequenceDao.findSequence(name);
    }
    
}
