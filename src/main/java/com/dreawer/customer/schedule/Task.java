package com.dreawer.customer.schedule;

import com.dreawer.customer.domain.Hierarchy;
import com.dreawer.customer.domain.Member;
import com.dreawer.customer.domain.PointRecord;
import com.dreawer.customer.exception.ResponseCodeException;
import com.dreawer.customer.lang.member.Expiration;
import com.dreawer.customer.manager.MemberManager;
import com.dreawer.customer.service.HierarchyService;
import com.dreawer.customer.service.MemberService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <CODE>Task</CODE>
 * 定时任务类
 * @author fenrir
 * @Date 18-4-23
 */
@Component
@EnableScheduling
public class Task {
    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器



    @Autowired
    private MemberService memberService;

    @Autowired
    private HierarchyService hierarchyService;

    @Autowired
    private MemberManager memberManager;

    /**
     * 会员到期扣减成长值方法
     */
    public void memberExpireHandler()  {
      try {

          //获取所有开启会员的店铺列表
          List<String> stores = hierarchyService.findAllStores();
          if (stores==null){
              return;
          }
          for (String store : stores) {
              //获取店铺下所有会员
              List<Member> members = memberService.findByStoreId(store);
              if (members==null){
                  continue;
              }
              for (Member member : members) {
                  //禁用店铺唯一会员等级时会同步删除所有会员的等级,如果该用户没有等级信息则跳过计算到期扣减
                  Hierarchy hierarchy = member.getHierarchy();
                  if (member.getHierarchyId()!=null&&
                          hierarchy !=null&&
                          member.getDueDate()!=null&&
                          hierarchy.getExpiration().equals(Expiration.LIMITED.toString())){
                      Timestamp dueDate = member.getDueDate();
                      Timestamp current = new Timestamp(System.currentTimeMillis());
                      //如果到期日期小于等于当前时刻则扣减成长值
                      if (dueDate.compareTo(current)!=1){
                          //扣减值
                          Integer expireDeduction = member.getHierarchy().getExpireDeduction();
                          PointRecord pointRecord = new PointRecord();
                          pointRecord.setType("EXPIRE");
                          pointRecord.setValue(expireDeduction.toString());
                          pointRecord.setSource("SYSTEM");
                          pointRecord.setStoreId(store);
                          pointRecord.setCustomerId(member.getId());
                          memberManager.updateRecord(pointRecord,member.getStoreId());

                      }
                  }
              }
          }
      }catch (ResponseCodeException e){
          logger.error(e.getResponseCode().display()+e.getResponseCode().getCheckPoint());
      }catch (Exception e){
          logger.error(e);
      }
    }


}