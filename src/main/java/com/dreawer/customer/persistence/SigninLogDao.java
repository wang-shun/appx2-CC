package com.dreawer.customer.persistence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dreawer.customer.domain.SignInLog;
import com.dreawer.customer.domain.User;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;

@Repository
public class SigninLogDao extends MyBatisBaseDao<SignInLog> {
    
    /**
     * 保存登录日志。
     * @param log 用户登录成功日志。
     * @author David Dai
     * @since 1.0
     */
    public int save(SignInLog log) {
        return insert("save", log);
    }
    
    /**
     * 通过用户ID查询，查询登录IP和最后一次时间。
     * @param userId 用户ID号。
     * @return 用户登录日志。
     * @author jinzhenzuo 创建于：2016年7月7日 下午6:25:26
     * @since 1.0
     */
    public SignInLog getSignInLogByUserId(String userId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", userId);
        return selectOne("selectlog", map);
    }
    
    /**
     * 查询最后一次登录时间。
     * @param userId 用户ID号。
     * @return 用户登录日志。
     */
    public SignInLog selectLastSignInTime(String id){
        return selectOne("selectLastSignInTime", id);
    }

    /**
     * 查询登录次数
     * @param user 用户。
     * @return 登录次数。
     */
	public int findSigninCount(User user) {
		return count("findSigninCount", user);
	}
}
