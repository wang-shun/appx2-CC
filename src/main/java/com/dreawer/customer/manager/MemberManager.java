package com.dreawer.customer.manager;

import com.dreawer.customer.domain.Hierarchy;
import com.dreawer.customer.domain.Member;
import com.dreawer.customer.domain.PointRecord;
import com.dreawer.customer.exception.ResponseCodeException;
import com.dreawer.customer.lang.member.Status;
import com.dreawer.customer.lang.record.Source;
import com.dreawer.customer.lang.record.Type;
import com.dreawer.customer.service.HierarchyService;
import com.dreawer.customer.service.MemberService;
import com.dreawer.customer.service.PointRecordService;
import com.dreawer.responsecode.rcdt.BusinessError;
import com.dreawer.responsecode.rcdt.PermissionsError;
import com.dreawer.responsecode.rcdt.RuleError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.dreawer.customer.DomainConstants.SOURCE;
import static com.dreawer.customer.DomainConstants.TYPE;

/**
 * <CODE>MemberManager</CODE>
 * 会员管理类
 * @author fenrir
 */

@Component
public class MemberManager extends BaseManager {

    private final MemberService memberService;

    private final HierarchyService hierarchyService;

    private final PointRecordService pointRecordService;



    /**
     * 尽量避免使用直接在属性上注入
     */
    @Autowired
    public MemberManager(MemberService memberService, HierarchyService hierarchyService, PointRecordService pointRecordService) {
        this.memberService = memberService;
        this.hierarchyService = hierarchyService;
        this.pointRecordService = pointRecordService;
    }


    /**
     * 分页查询会员信息
     * @return 分页信息
     */
    public Map<String,Object> memberQuery(Map<String,Object> map){
            String storeId = null;
            if ( map.get("storeId")!=null){
                storeId = (String) map.get("storeId");
            }
            String phoneNumber = null;
            if (map.get("phoneNumber")!=null){
                phoneNumber = (String) map.get("phoneNumber");
            }
            String username = null;
            if (map.get("userName")!=null){
                username = (String) map.get("userName");
            }

            String hierarchyId = null;
            if (map.get("rankId")!=null){
                hierarchyId = (String) map.get("rankId");
            }

            String nickName = null;
            if (map.get("nickName")!=null){
                nickName = (String) map.get("nickName");
            }
            String appType = null;
            if (map.get("appType")!=null){
                appType = (String) map.get("appType");
            }
            Integer pageNo = 0;
            if (map.get("pageNo")!=null){
                pageNo = Integer.parseInt(map.get("pageNo").toString());
            }
            Integer pageSize = 5;
            if (map.get("pageSize")!=null){
                pageSize = Integer.parseInt(map.get("pageSize").toString());
            }
            int totalSize = memberService.findByConditionCount(storeId, phoneNumber, username, hierarchyId, nickName, appType);
            int totalPage = totalSize%pageSize==0?totalSize/pageSize:(totalSize/pageSize+1);
            List<Member> members = memberService.findByCondition(storeId, phoneNumber, username, hierarchyId, nickName, appType, pageNo, pageSize);

            Map<String,Object> params = new HashMap<>();
            params.put("totalSize",totalSize);
            params.put("totalPage",totalPage);
            params.put("result",members);
            return params;

    }

    /**
     * 添加会员
     */
    @Transactional
    public void addMember(Map<String,Object> map,String storeId) throws Exception {

        List<Hierarchy> list = hierarchyService.findByStoreId(storeId);
        //如果店铺所有会员等级都被禁用则报错
        if (list==null||list.size()==0){
            throw new ResponseCodeException(BusinessError.STATUS("未查找到会员等级列表"));
        }
        if (list.get(0).getStatus().equals(Status.DISABLE.toString())){
            throw new ResponseCodeException(BusinessError.STATUS("该店铺下所有等级都已禁用"));
        }
            Member member = new Member();
            if(map.get("sex")!=null){
                member.setSex(Integer.parseInt((map.get("sex").toString())));
            }
            if (map.get("phoneNumber")!=null){
                member.setPhoneNumber(map.get("phoneNumber").toString());
            }
            if (map.get("userName")!=null){
                member.setUserName(map.get("userName").toString());
            }
            if (map.get("id")!=null){
                member.setId(map.get("id").toString());
            }
            if (map.get("birthday")!=null){
                member.setBirthday(Long.valueOf(map.get("birthday").toString()));
            }
            if (map.get("mugshot")!=null){
                member.setMugshot(map.get("mugshot").toString());
            }
            if (map.get("nickName")!=null){
                member.setNickName(map.get("nickName").toString());
            }
            member.setStoreId(storeId);
            if (list.size()==0){
                throw new ResponseCodeException(RuleError.NON_EXISTENT("该店铺下未查询到会员等级信息"));
            }
            Hierarchy hierarchy = list.get(0);
            member = setDueDate(hierarchy,member);

            member.setCreateTime(getNow());
            member.setHierarchy(hierarchy);
            member.setHierarchyId(hierarchy.getId());
            member.setGrowthValue(0);
            memberService.addMember(member);

    }

    /**
     * 更新会员信息
     */
    public void update(Map<String,Object> map,String storeId) throws Exception {


        Member member = new Member();
        if(map.get("sex")!=null){
            member.setSex(Integer.parseInt((map.get("sex").toString())));
        }
        if (map.get("phoneNumber")!=null){
            member.setPhoneNumber(map.get("phoneNumber").toString());
        }
        if (map.get("userName")!=null){
            member.setUserName(map.get("userName").toString());
        }
        if (map.get("id")!=null){
            member.setId(map.get("id").toString());
        }
        if (map.get("birthday")!=null){
            member.setBirthday(Long.valueOf(map.get("birthday").toString()));
        }
        member.setStoreId(storeId);


            Member before = memberService.findById(member.getId());
            if (before==null){
                throw new ResponseCodeException(RuleError.NON_EXISTENT("未查询到用户信息"));
            }
            before.setSex(member.getSex());
            before.setPhoneNumber(member.getPhoneNumber());
            before.setUserName(member.getUserName());
            before.setBirthday(member.getBirthday());
            String hierarchyId = before.getHierarchyId();
            Hierarchy hierarchy = hierarchyService.findById(hierarchyId);
            if (hierarchy==null){
                throw new ResponseCodeException(RuleError.NON_EXISTENT("未查询到等级信息"));
            }
            before.setHierarchy(hierarchy);
            //缓存单条数据
            member.setUpdateTime(getNow());
            memberService.editMember(before);
    }

    /**
     * 更新用户成长值
     */
    public void updateRecord(PointRecord pointRecord,String storeId) throws Exception {

        List<Hierarchy> hierarchies = hierarchyService.findByStoreId(storeId);
        if (hierarchies==null){
            throw new ResponseCodeException(RuleError.NON_EXISTENT("未查询到店铺信息"));
        }
        if (hierarchies.get(0).getStatus().equals(Status.DISABLE)){
                throw new ResponseCodeException(PermissionsError.FUNCTION_NO_ALLOW("店铺无已启用的会员等级"));
            }
            //保留整数
            pointRecord.setValue(String.valueOf(new BigDecimal(pointRecord.getValue()).intValue()));
            pointRecord.setId(UUID.randomUUID().toString().replace("-",""));
            pointRecord.setStoreId(storeId);
            String customerId = pointRecord.getCustomerId();
            String value = pointRecord.getValue();

            Member member = memberService.findById(customerId);
            if (member==null){
               throw new ResponseCodeException(RuleError.NON_EXISTENT("未查询到会员信息"));
            }
            pointRecord.setCreateTime(new Timestamp(System.currentTimeMillis()));
            pointRecordService.addPointRecord(pointRecord);
            Integer growthValue = member.getGrowthValue();

            //增减用户成长值
            if (pointRecord.getType().equals(Type.ADD)){
                //增加成长值
                BigDecimal result = new BigDecimal(growthValue).add(new BigDecimal(value)).setScale(0,BigDecimal.ROUND_DOWN);
                //判断用户成长值归属到哪一等级
                for (Hierarchy hierarchy:hierarchies) {
                    member =setMemberRank(member, result, hierarchy);
                }
            }else if (pointRecord.getType().equals(Type.REDUCE)){
                BigDecimal result = new BigDecimal(growthValue).subtract(new BigDecimal(value)).setScale(0,BigDecimal.ROUND_DOWN);
                //如果相减结果小于0
                if (result.compareTo(new BigDecimal(0))==-1){
                    result=new BigDecimal(0);
                }
                //判断用户成长值归属到哪一等级
                for (Hierarchy hierarchy:hierarchies) {
                    member = setMemberRank(member, result, hierarchy);
                }
            }
            //更新缓存中会员对象
            member.setUpdateTime(getNow());
            memberService.editMember(member);

    }

    private Member setMemberRank(Member member, BigDecimal result, Hierarchy hierarchy) {
        if (hierarchy.getStatus().equals(Status.ENABLE)){
            Integer next = hierarchy.getGrowthValue();
            if (result.intValue()>=next){
                member.setHierarchyId(hierarchy.getId());
                member.setHierarchy(hierarchy);
                member.setGrowthValue(result.intValue());
                member = setDueDate(hierarchy, member);

            }
        }
        return member;
    }


    /**
     * 查询会员成长值记录
     */
   public Map<String, Object> recordQuery(Map<String,Object> map,String storeId)throws Exception{

            String customerId = (String) map.get("userId");
            Integer pageNo = 0;
            if (map.get("pageNo")!=null){
                pageNo = Integer.parseInt(map.get("pageNo").toString());
            }
            Integer pageSize = 5;
            if (map.get("pageSize")!=null){
                pageSize = Integer.parseInt(map.get("pageSize").toString());
            }
       Source source= (Source) map.get(SOURCE);
       Type type = (Type) map.get(TYPE);
            List<PointRecord> pointRecords = pointRecordService.recordQuery(storeId,customerId,pageNo,pageSize,source,type);
            int totalSize = pointRecordService.recordQueryCount(storeId,customerId,source,type);
            int totalPage = totalSize%pageSize==0?totalSize/pageSize:(totalSize/pageSize+1);
            Map<String,Object> params = new HashMap<>();
            params.put("totalSize",totalSize);
            params.put("totalPage",totalPage);
            params.put("result",pointRecords);
            return params;
   }
}
