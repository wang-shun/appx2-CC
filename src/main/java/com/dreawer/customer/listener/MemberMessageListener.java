//package com.dreawer.customer.listener;
//
//import com.aliyun.openservices.ons.api.Action;
//import com.aliyun.openservices.ons.api.ConsumeContext;
//import com.aliyun.openservices.ons.api.Message;
//import com.aliyun.openservices.ons.api.MessageListener;
//import com.dreawer.cloudapp.logger.Log;
//import com.dreawer.cloudapp.message.PublicRequestMessage;
//import com.dreawer.cloudapp.message.RequestMessage;
//import com.dreawer.cloudapp.message.ResponseMessage;
//import com.dreawer.cloudapp.responsecode.Success;
//import com.dreawer.cloudapp.responsecode.exception.ResponseCodeException;
//import com.dreawer.customer.manager.HierarchyManager;
//import com.dreawer.customer.manager.MemberManager;
//import com.dreawer.member.domain.ThirdPartManager;
//import com.dreawer.customer.utils.GsonUtil;
//import com.dreawer.customer.utils.RedisUtil;
//import com.google.gson.reflect.TypeToken;
//import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * <CODE>HierarchyMessageListener</CODE>
// * 会员消息监听器
// * @author fenrir
// */
//@Component
//public class MemberMessageListener implements MessageListener {
//
//
//    private final HierarchyManager hierarchyManager;
//
//    private final MemberManager memberManager;
//
//    private final ThirdPartManager thirdPartManager;
//
//    private final RedisUtil redisUtil;
//
//    @Autowired
//    public MemberMessageListener(HierarchyManager hierarchyManager, MemberManager memberManager, ThirdPartManager thirdPartManager, RedisUtil redisUtil) {
//        this.hierarchyManager = hierarchyManager;
//        this.memberManager = memberManager;
//        this.thirdPartManager = thirdPartManager;
//        this.redisUtil = redisUtil;
//    }
//
//
//    @Override
//    public Action consume(Message message, ConsumeContext consumeContext) {
//        //do sth
//        RequestMessage requestMessage = null;
//        String taskId = null;
//        ResponseMessage responseMessage = null;
//        Map<String,Object> result = new HashMap<>();
//        try {
//            String response = new String(message.getBody(), "UTF-8");
//            /** 提取请求消息 **/
//            requestMessage = GsonUtil.fromJson(response, new TypeToken<RequestMessage>(){});
//            Log.request(requestMessage);
//            PublicRequestMessage publicMessage = requestMessage.getPublicMessage();
//            /** 服务码 用来区分不同服务的调用 **/
//            String serviceId = publicMessage.getServiceId();
//            /** 店铺ID **/
//            String storeId = publicMessage.getStoreId();
//            /** 任务码 任务执行结果的唯一ID **/
//            taskId = publicMessage.getTaskId();
//            /** 消息内容 **/
//            Map<String, Object> datas = requestMessage.getDatas();
//            switch (serviceId) {
//                case "mems-0001":
//                    //第三方用户登记
//                    result = thirdPartManager.addThirdPartUser(datas,storeId,taskId);
//                    break;
//                case "mems-0002":
//                    //修改昵称和头像
//                    thirdPartManager.editThirdUser(datas,storeId,taskId);
//                    break;
//                case "mems-0003":
//                    //会员注册
//                    memberManager.addMember(datas,storeId);
//                    break;
//                case "mems-0004":
//                    //添加收货地址
//                    break;
//                case "mems-0005":
//                    //添加商家
//                    break;
//                case "mems-0006":
//                    //修改商家名称、会员控制开关
//                    break;
//                case "mems-0007":
//                    //添加会员等级
//                    hierarchyManager.addHierarchy(requestMessage);
//                    break;
//                case "mems-0008":
//                    //启用、禁用会员等级
//                        responseMessage = hierarchyManager.suspend(publicMessage,storeId,datas);
//                        Log.response(responseMessage);
//                        redisUtil.put("taskId_"+taskId,responseMessage);
//                        hierarchyManager.updateStatus(requestMessage);
//                        break;
//                case "mems-0009":
//                    //修改会员等级
//                    responseMessage = hierarchyManager.suspend(publicMessage,storeId,datas);
//                    hierarchyManager.update(requestMessage);
//                    break;
//                case "mems-0010":
//                    //增加会员成长值
//                    memberManager.updateRecord(datas,storeId);
//                    break;
//                case "mems-0011":
//                    //客户、会员信息列表
//                    result = memberManager.memberQuery(datas);
//                    break;
//                case "mems-0012":
//                    //客户、会员信息详情
//                    break;
//                case "mems-0013":
//                    //客户收货地址信息列表
//                    break;
//                case "mems-0014":
//                    //客户切换常用收货地址
//                    break;
//                case "mems-0015":
//                    //修改会员信息
//                    memberManager.update(datas,storeId);
//                    break;
//                case "mems-0016":
//                    //查询会员成长值记录
//                    result = memberManager.recordQuery(datas,storeId);
//            }
//            /** 响应结果缓存 **/
//            if (responseMessage==null){
//                responseMessage = new ResponseMessage(new RequestMessage(publicMessage), Success.SUCCESS);
//            }
//            /** 将需要返回内容的响应体封装**/
//            if (result!=null){
//                responseMessage.setBody("data",result);
//            }
//            redisUtil.put("taskId_"+taskId,responseMessage);
//            //System.out.println(responseMessage);
//            Log.response(responseMessage);
//            Log.error("消息已提交");
//            return Action.CommitMessage;
//
//        }catch (ResponseCodeException e) {
//            /** 如果抛出该异常,则说明业务检查出错.将错误信息保存到redis **/
//            responseMessage = new ResponseMessage(requestMessage,e.getResponseCode());
//            redisUtil.put("taskId_"+taskId,responseMessage);
//            Log.response(responseMessage);
//            Log.error("消息已提交");
//            Log.error(e.getResponseCode().display()+e.getResponseCode().getCheckPoint());
//            return Action.CommitMessage;
//
//        } catch (Exception e) {
//            Log.error(e);
//            Log.error("消息已提交");
//            return Action.CommitMessage;
//        }
//    }
//}
