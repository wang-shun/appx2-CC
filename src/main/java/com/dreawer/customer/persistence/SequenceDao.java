package com.dreawer.customer.persistence;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.dreawer.customer.domain.Sequence;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;

@Repository
public class SequenceDao extends MyBatisBaseDao<Sequence> {
    
    /**
     * 保存序列信息。
     * @param sequence 序列。
     * @return 成功保存的记录数。
     * @author David Dai
     * @since 1.0
     */
    public int save(Sequence sequence) {
        return insert("save", sequence);
    }
    
    /**
     * 更新序列信息。
     * @param sequence 序列。
     * @return 更新实例总数。如果更新成功则为1，否则为0。
     * @author David Dai
     * @since 1.0
     */
    public int update(Sequence sequence) {
        return update("update", sequence);
    }
    
    /**
     * 查询指定的序列实例。
     * @param name 序列名。
     * @return 序列实例。如果序列存在则返回相匹配的序列实例，否则返回 null。
     * @author David Dai
     * @since 1.0
     */
    public Sequence findSequence(String name) {
        Map<Object, Object> params = new HashMap<Object, Object>();
        params.put("name", name);
        return selectOne("findSequenceByName", params);
    }

}
