package com.dreawer.customer.manager;


import com.dreawer.customer.domain.Hierarchy;
import com.dreawer.customer.domain.Member;
import com.dreawer.customer.exception.ResponseCodeException;
import com.dreawer.customer.lang.member.Expiration;
import com.dreawer.customer.lang.member.Status;
import com.dreawer.customer.service.HierarchyService;
import com.dreawer.customer.service.MemberService;
import com.dreawer.customer.utils.BeanUtils;
import com.dreawer.customer.utils.RedisUtil;
import com.dreawer.responsecode.rcdt.EntryError;
import com.dreawer.responsecode.rcdt.PermissionsError;
import com.dreawer.responsecode.rcdt.RuleError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <CODE>HierarchyManager</CODE>
 * 获取监听器消息后处理业务逻辑
 * @author fenrir
 */
@Component
public class HierarchyManager extends BaseManager{



    private final
    HierarchyService service;

    private final
    MemberService memberService;

    private final
    RedisUtil redisUtil;

    @Autowired
    public HierarchyManager(HierarchyService service, MemberService memberService, RedisUtil redisUtil) {
        this.service = service;
        this.memberService = memberService;
        this.redisUtil = redisUtil;
    }

//    /**
//     * 修改会员等级状态为处理中
//     * @param message 请求消息
//     * @return 应答消息
//     */
//    public ResponseMessage suspend(PublicRequestMessage message,
//                                   String storeId,
//                                   Map<String,Object> entity) throws ResponseCodeException, IllegalAccessException {
//
//        String data = entity.get("data").toString();
//        Hierarchy jsonObject = new Gson().fromJson(data, Hierarchy.class);
//        String id = jsonObject.getId();
//        Hierarchy hierarchy = service.findById(id);
//        //如果查询不到会员信息
//        if (hierarchy==null){
//            throw new ResponseCodeException(RuleError.NON_EXISTENT("未查询到会员信息"));
//        }
//
//        //更新缓存中单条等级记录
//        hierarchy.setStatus(Status.SUSPEND.toString());
//        hierarchy.setUpdateTime(getNow());
//        service.update(hierarchy);
//        //更新缓存中等级列表记录
//        service.findByStoreId(storeId);
//        return new ResponseMessage(new RequestMessage(message),Success.SUCCESS);
//    }

    /**
     * 添加会员等级
     */
    public Hierarchy addHierarchy(Map<String,Object> data,String storeId) throws Exception {


           Hierarchy hierarchy = BeanUtils.mapToObject(data, Hierarchy.class);
            List<Hierarchy> list = service.findByStoreId(storeId);
            if (list==null){
                list = new ArrayList<>();
            }
           int size = list.size();

            if (size!=0){
                //判断新添加的会员等级所需成长值是否大于上一级
                Hierarchy before = list.get(size - 1);
                if (before.getGrowthValue()>=hierarchy.getGrowthValue()){
                    throw new ResponseCodeException(EntryError.COMMON("成长值需大于上一级成长值"));
                }
                //判断会员名称是否重复
                for (Hierarchy node : list) {
                    if (node.getName().trim().equals(hierarchy.getName().trim())){
                        throw new ResponseCodeException(EntryError.DUPLICATE("名称不能重复"));
                    }
                }
            }

        //为新增的会员等级添加排序信息
        if (size>=7){
            throw new ResponseCodeException(PermissionsError.FUNCTION_NO_ALLOW("已达到最大会员等级"));
           }else {
               hierarchy.setSequence(size+1);
           }

           hierarchy.setCreateTime(getNow());
           //缓存单条
           service.addHierarchy(hierarchy);
            //缓存等级列表
           list= service.findByStoreId(storeId);
           //更新达到最新等级成长值的会员的等级
           updateMemberHierarchy(hierarchy,list);
           return hierarchy;

    }


    /**
     * 更新会员等级启用和禁用状态
     */
    public void updateStatus(Map<String, Object> entity,String storeId) throws IllegalAccessException, ResponseCodeException {

        String id = (String) entity.get("id");
        String status = (String) entity.get("status");
        if (!(status.equals(Status.DISABLE.toString()) ||
                status.equals(Status.ENABLE.toString()) ||
                status.equals(Status.SUSPEND.toString()))) {
            //返回错误
            throw new ResponseCodeException(EntryError.COMMON("状态输入有误"));
        }

        Hierarchy hierarchy = service.findById(id);
        //查询用户等级列表
        List<Hierarchy> hierarchies = service.findByStoreId(storeId);
        if (hierarchies == null || hierarchies.size() == 0) {
            //返回错误
            throw new ResponseCodeException(RuleError.NON_EXISTENT("未查询到会员等级列表"));
        }
        //更改会员等级状态
        //更新缓存中单条等级记录
        hierarchy.setStatus(status);
        hierarchy.setUpdateTime(getNow());
        service.update(hierarchy);
        //更新缓存中等级列表记录
        hierarchies = service.findByStoreId(storeId);
        //更改该店铺下会员的等级
        updateMemberHierarchy(hierarchy, hierarchies);
    }


    /**
     * 修改会员等级信息后同步更新所有会员的会员等级
     * @param hierarchy 会员等级
     * @param hierarchies 店铺会员等级列表
     */
    private void updateMemberHierarchy(Hierarchy hierarchy, List<Hierarchy> hierarchies) throws IllegalAccessException {
        String storeId = hierarchy.getStoreId();
        Integer growthValue = hierarchy.getGrowthValue();
        String status = hierarchy.getStatus();
        String id = hierarchy.getId();

        //如果开启会员等级,则将积分大于等于这一级的用户关联其ID
        if (status.equals(Status.ENABLE.toString())) {
            //更新数据库
            memberService.updateHierarchyByGrowthValue(storeId, id, growthValue);
            //更新redis
            List<Member> list =  memberService.findByGrowthValue(storeId,growthValue);
            if (list!=null){
                for (Member member : list) {
                    member.setHierarchy(hierarchy);
                    member.setHierarchyId(hierarchy.getId());
                    setDueDate(hierarchy, member);
                    memberService.editMember(member);
                    redisUtil.put("member_"+member.getId()+"_"+member.getStoreId(),member);
                }
            }
        } else if (status.equals(Status.DISABLE.toString())) {
            //关闭会员等级则将其会员降级
            if (hierarchies.size() == 1) {
                //如果关闭的是全店唯一的等级则删除所有用户的等级
                memberService.updateHierarchyByGrowthValue(storeId, null, 0);
                List<Member> list =  memberService.findByStoreId(storeId);
                if (list!=null){
                    for (Member member : list) {
                        member.setHierarchy(null);
                        member.setHierarchyId(null);
                        memberService.editMember(member);
                        redisUtil.put("member_"+member.getId()+"_"+member.getStoreId(),member);
                    }
                }
            } else {
                for (int i = 0; i < hierarchies.size(); i++) {
                    Hierarchy node = hierarchies.get(i);
                    String previousHierarchyId = null;
                    if (node.getId().equals(id)) {
                        //如果禁用的是第一个等级则直接删除所有用户的等级
                        //反之则将所有用户的等级降级
                        if (i!=0){
                            previousHierarchyId = hierarchies.get(i - 1).getId();
                            node = hierarchies.get(i-1);
                        }else {
                            node = null;
                        }
                        memberService.updateHierarchyByGrowthValue(storeId, previousHierarchyId, growthValue);
                        List<Member> list =  memberService.findByGrowthValue(storeId,growthValue);
                        if (list!=null){
                            for (Member member : list) {
                                member.setHierarchy(node);
                                member.setHierarchyId(previousHierarchyId);
                                //如果会员有会员等级则判断到期时间
                                if (node!=null){
                                    setDueDate(node, member);
                                }
                                memberService.editMember(member);
                                redisUtil.put("member_"+member.getId()+"_"+member.getStoreId(),member);
                            }
                        }
                        break;
                    }

                }
            }
        }
    }



    /**
     * 更新会员等级
     */
    public Hierarchy update(Map<String,Object> entity,String storeId) throws Exception {

        Hierarchy hierarchy = BeanUtils.mapToObject(entity, Hierarchy.class);

        if (hierarchy.getExpiration().equals(Expiration.UNLIMITED.toString())){
            hierarchy.setExpireDeduction(0);
            hierarchy.setPeriod(0);
        }

        Hierarchy exist = service.findById(hierarchy.getId());
        //获取当前等级顺序
        hierarchy.setSequence(exist.getSequence());
        //判断该等级成长值是否不低于上一等级成长值
        List<Hierarchy> list = service.findByStoreId(storeId);
        if (list==null){
            list = new ArrayList<>();
        }

        //判断会员名称是否重复
        for (Hierarchy node : list) {
            if (node.getName().trim().equals(hierarchy.getName().trim())&&
                    !node.getId().equals(hierarchy.getId())){
                //名称重复时回滚启用状态
                rollBack(hierarchy.getId(),hierarchy.getStatus());
                throw new ResponseCodeException(EntryError.DUPLICATE("名称不能重复"));
            }
        }

        if (list.size()!=0){
            for(int i=0;i<list.size();i++){
                Hierarchy node = list.get(i);
                if (node.getId().equals(hierarchy.getId())){
                    //当更新的等级不是第一级时,判断该等级成长值是否低于上一等级成长值
                    if (i!=0){
                        //获取上一级
                        Hierarchy before = list.get(i - 1);
                        //如果上一级所需成长值大于等于该等级成长值
                        if (before.getGrowthValue()>=hierarchy.getGrowthValue()){
                            rollBack(hierarchy.getId(),hierarchy.getStatus());
                            throw new ResponseCodeException(RuleError.LESS_LEAST("该等级成长值应大于上一级成长值"));
                        }
                        //如果修改的不是最后一级
                        if (i<list.size()-1){
                            Hierarchy after = list.get(i+1);
                            //如果该等级成长值大于下一级成长值
                            if (after.getGrowthValue()<=hierarchy.getGrowthValue()){
                                rollBack(hierarchy.getId(),hierarchy.getStatus());
                                throw new ResponseCodeException(RuleError.LESS_LEAST("该等级成长值应小于下一级成长值"));
                            }
                        }
                    }
                }
            }
        }
        if (!hierarchy.getDiscount()){
            hierarchy.setDiscountAmount(null);
        }
        hierarchy.setUpdateTime(getNow());
        service.update(hierarchy);
        //更新缓存中等级列表记录
        list = service.findByStoreId(storeId);
        //更新会员详情中等级记录
        updateMemberHierarchy(hierarchy,list);

        return hierarchy;
    }

    /**
     * 判断店铺下是否有处理中的会员等级
     * @param storeId 店铺ID
     * @return Boolean
     */
    @SuppressWarnings("unused")
	private Boolean isSuspend(String storeId){
        List<Hierarchy> list = service.findByStoreId(storeId);
        if (list==null){
            return false;
        }
        for (Hierarchy hierarchy : list) {
            if (hierarchy.getStatus().equals(Status.SUSPEND.toString())){
                return true;
            }
        }
        return false;
    }

    /**
     * 当修改会员等级抛出异常时,将处理中状态回滚为修改前状态
     * @param id 等级ID
     * @param status 启动状态
     */
    private void rollBack(String id,String status) throws IllegalAccessException {
        Hierarchy hierarchy = service.findById(id);
        hierarchy.setStatus(status);
        hierarchy.setUpdateTime(getNow());
        service.update(hierarchy);
        service.findByStoreId(hierarchy.getStoreId());
    }
}
