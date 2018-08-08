package com.dreawer.customer.persistence;

import com.dreawer.customer.domain.RememberKey;
import com.dreawer.customer.domain.User;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public class RememberKeyDao extends MyBatisBaseDao<RememberKey> {

    /**
     * 保存秘钥信息。
     * @param key 自动登录秘钥。
     * @return 成功保存的记录数。
     * @author David Dai
     * @since 1.0
     */
    public int save(RememberKey key) {
        return insert("save", key);
    }
    
    /**
     * 删除指定的秘钥。
     * @return 成功删除的记录数。
     * @author David Dai
     * @since 1.0
     */
    public int deleteKey(RememberKey rememberKey) {
        return delete("deleteKey", rememberKey);
    }

    /**
     * 删除指定用户的所有秘钥。
     * @param user 属主用户。
     * @return 成功删除的记录数。
     * @author David Dai
     * @since 1.0
     */
    public int deleteKeyByUser(User user) {
        return delete("deleteKeyByUser", user);
    }
    
    /**
     * 更新指定秘钥最近一次的使用时间。
     * @param key 自动登录秘钥。
     * @return 成功更新的记录数。
     * @author David Dai
     * @since 1.0
     */
    public int updateLastUseTime(RememberKey key) {
        return update("updateLastUseTime", key);
    }
    
    /**
     * 更新秘钥的客户信息。
     * @param key 自动登录秘钥。
     * @return 成功更新的记录数。
     * @author Sdanly
     * @since 1.0
     */
    public int updateCustomer(RememberKey key) {
        return update("updateCustomer", key);
    }
    
    /**
     * 查询指定秘钥。
     * @param value 秘钥值。
     * @return 秘钥。
     * @author David Dai
     * @since 1.0
     */
    public RememberKey findKey(String value) {
        return selectOne("findKeyByValue", value);
    }
    
}
