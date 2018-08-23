package com.dreawer.customer.service;

import com.dreawer.customer.domain.Member;
import com.dreawer.customer.persistence.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <CODE>MemberService</CODE>
 * 会员Service
 *
 * @author fenrir
 * @Date 18-3-20
 */
@Service
@Transactional
public class MemberService extends BaseService {

    @Autowired
    MemberMapper memberMapper;


    public Member findById(String id)  {
        Member member = memberMapper.findById(id);
        return member;
    }

    /**
     * 注册会员
     *
     * @return
     */
    //@Transactional
    public Member addMember(Member member)  {
        memberMapper.insert(member);
        return member;
    }


    /**
     * 编辑客户信息
     *
     * @param member 会员信息
     * @return 返回结果
     */
    @Transactional
    public Member editMember(Member member)  {
        memberMapper.update(member);
        return member;
    }

    /**
     * 通过店铺ID查询所有会员信息
     * @param storeId 店铺ID
     * @return
     */
    public List<Member> findByStoreId(String storeId) {
        return memberMapper.findByStoreId(storeId);

    }


    public List<Member> findByHierarchyId(String storeId, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("id", id);
        return memberMapper.findByHierarchyId(map);
    }

    @Transactional
    public int updateHierarchyByGrowthValue(String storeId, String id, Integer growthValue) {
        Map<String, Object> map = new HashMap<>();
        map.put("growthValue", growthValue);
        map.put("id", id);
        map.put("storeId", storeId);
        return memberMapper.updateHierarchyByGrowthValue(map);
    }

    public List<Member> findByCondition(String storeId, String phoneNumber,
                                        String username, String hierarchyId,
                                        String nickName, String appType,
                                        int pageNo, int pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("storeId",storeId);
        map.put("phoneNumber",phoneNumber);
        map.put("userName",username);
        map.put("hierarchyId",hierarchyId);
        map.put("nickName",nickName);
        map.put("appType",appType);
        int start = (pageNo > 0 ? pageNo - 1 : 0) * pageSize;
        map.put("start",start);
        map.put("pageSize",pageSize);
        return memberMapper.findByCondition(map);
    }

    public int findByConditionCount(String storeId, String phoneNumber, String username, String hierarchyId, String nickName, String appType) {

        Map<String,Object> map = new HashMap<>();
        map.put("storeId",storeId);
        map.put("phoneNumber",phoneNumber);
        map.put("userName",username);
        map.put("hierarchyId",hierarchyId);
        map.put("nickName",nickName);
        map.put("appType",appType);
        return memberMapper.findByConditionCount(map);
    }

    /**
     * 查询成长值大于等于指定区间的会员信息
     * @param storeId
     * @param growthValue
     * @return
     */
    public List<Member> findByGrowthValue(String storeId, Integer growthValue) {
        Map<String,Object> map = new HashMap<>();
        map.put("storeId",storeId);
        map.put("growthValue",growthValue);
        return memberMapper.findByGrowthValue(map);
    }
}
