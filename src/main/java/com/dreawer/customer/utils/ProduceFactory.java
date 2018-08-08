package com.dreawer.customer.utils;

import org.springframework.stereotype.Component;

@Component
public class ProduceFactory {

//
//    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器
//
//	public ProduceResult send(String json) {
//       /*ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-producer.xml");
//        Producer producer = (Producer) context.getBean("producer");*/
//		if(StringUtils.isBlank(json)){
//            return new ProduceResult(false, "参数不能为空");
//		}
//
//        //发送消息
//        Message msg = new Message( //
//                // Message 所属的 Topic
//                "dreawer_devel_topic",
//                // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
//                "tag_test",
//                // Message Body 可以是任何二进制形式的数据， MQ 不做任何干预
//                // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
//                json.getBytes());
//        // 设置代表消息的业务关键属性，请尽可能全局唯一
//        // 以方便您在无法正常收到消息情况下，可通过 MQ 控制台查询消息并补发
//        // 注意：不设置也不会影响消息正常收发
//        // String token = UUID.randomUUID().toString().replace("-", "");
//        // msg.setKey("messagetest1");
//        // 发送消息，只要不抛异常就是成功
//        try {
//            SendResult sendResult = producer.send(msg);
//            if(sendResult != null){
//                return new ProduceResult(true, sendResult.getMessageId());
//            }else{
//                return new ProduceResult(false, "发送失败");
//            }
//        }catch (ONSClientException e) {
//            // 消息发送失败，可重新发送这条消息或持久化这条数据进行补偿处理
//            logger.error(e);
//            return new ProduceResult(false, "发送失败");
//        }
//
//    }

}
