package com.dreawer.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreawer.customer.domain.RememberKey;
import com.dreawer.customer.domain.User;
import com.dreawer.customer.persistence.RememberKeyDao;

@Service
public class RememberKeyService {
    
    @Autowired
    private RememberKeyDao rememberKeyDao; // 自动登录秘钥DAO
    
    /**
     * 添加秘钥。
     * @param key 秘钥。
     * @return 自动登录秘钥。
     * @author David Dai
     * @since 2.0
     */
    public void add(RememberKey key) {
        // 删除该用户其他秘钥，以更新秘钥
        removeRememberKey(key.getUser());
        
        // 保存秘钥
        rememberKeyDao.save(key);
    }
    
    /**
     * 删除指定的秘钥。
     * @param rememberKey 自动登录秘钥。
     * @author David Dai
     * @since 2.0
     */
    public void removeRememberKey(RememberKey rememberKey) {
        rememberKeyDao.deleteKey(rememberKey);
    }
    
    /**
     * 删除指定用户的所有秘钥。
     * @param user 用户。
     * @author David Dai
     * @since 2.0
     */
    public void removeRememberKey(User user) {
        rememberKeyDao.deleteKeyByUser(user);
    }
    
    /**
     * 更新秘钥的最近使用时间。
     * @param rememberKey 自动登录秘钥。
     * @author David Dai
     * @since 2.0
     */
    public void updateRememberKey(RememberKey rememberKey) {
        rememberKeyDao.updateLastUseTime(rememberKey);
    }
    
    /**
     * 更新秘钥的客户信息。
     * @param rememberKey 自动登录秘钥。
     * @author Sdanly
     * @since 2.0
     */
    public void updateRememberKeyCustomer(RememberKey rememberKey) {
        rememberKeyDao.updateCustomer(rememberKey);
    }
    
    /**
     * 获取秘钥。
     * @param rememberKeyValue 秘钥值。
     * @return 秘钥。如果存在则返回秘钥，否则返回 null。
     * @author David Dai
     * @since 1.0
     */
    public RememberKey getRememberKey(String rememberKeyValue) {
        return rememberKeyDao.findKey(rememberKeyValue);
    }
    
    /**
     * 验证秘钥有效性。
     * @param rememberKey 秘钥。
     * @param validTime 有效时间（毫秒数）。
     * @return 验证结果。如果秘钥为空或已过期则返回false，否则返回true。
     * @author David Dai
     * @since 2.0
     */
    public Boolean isValidRememberKey(RememberKey rememberKey, long validTime) {
        if (rememberKey == null) {
            return false;
        }
        
        // 检查秘钥是否过期（当前时间 - 秘钥时间 <= 有效时间）
        Long nowTime = System.currentTimeMillis();
        Long keyTime = rememberKey.getLastUseTime().getTime();
        if(nowTime - keyTime <= validTime) {
            return true;
        }
        return false;
    }

}
