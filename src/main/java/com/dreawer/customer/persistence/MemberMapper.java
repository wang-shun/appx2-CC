package com.dreawer.customer.persistence;

import com.dreawer.customer.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MemberMapper {

    int insert(Member member);

    List<Member> findAllMember();

    Member findById(String id);

    int update(Member member);

    List<Member> findByStoreId(String storeId);

    List<Member> findByHierarchyId(Map<String, Object> map);

    int updateHierarchyByGrowthValue(Map<String, Object> map);

    List<Member> findByCondition(Map<String, Object> map);

    int findByConditionCount(Map<String, Object> map);

    List<Member> findByGrowthValue(Map<String, Object> map);
}
