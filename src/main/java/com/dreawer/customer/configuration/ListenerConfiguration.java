//package com.dreawer.customer.configuration;
//
//import com.aliyun.openservices.ons.api.MessageListener;
//import com.aliyun.openservices.ons.api.bean.ConsumerBean;
//import com.aliyun.openservices.ons.api.bean.Subscription;
//import com.dreawer.customer.listener.MemberMessageListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * <CODE>ListenerConfiguration</CODE>
// *
// * @author fenrir
// * @Date 18-5-9
// */
//
//@Configuration
//@ConfigurationProperties(prefix = "message")
//public class ListenerConfiguration {
//
//
//    private String ConsumerId;
//
//    private String AccessKey;
//
//    private String SecretKey;
//
//    private String ConsumeThreadNums;
//
//    private String topic;
//
//
//    @Autowired
//    private MemberMessageListener memberMessageListener;
//
//    @Bean(initMethod="start",
//            destroyMethod="shutdown")
//
//    public ConsumerBean getConsumerBean(){
//        Properties properties = new Properties();
//        properties.setProperty("ConsumerId",ConsumerId);
//        properties.setProperty("AccessKey",AccessKey);
//        properties.setProperty("SecretKey",SecretKey);
//        properties.setProperty("ConsumeThreadNums",ConsumeThreadNums);
//        ConsumerBean consumerBean = new ConsumerBean();
//        consumerBean.setProperties(properties);
//
//        Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();
//        Subscription subscription = new Subscription();
//        subscription.setTopic(topic);
//        subscription.setExpression("*");
//        subscriptionTable.put(subscription,memberMessageListener);
//        consumerBean.setSubscriptionTable(subscriptionTable);
//        return consumerBean;
//    }
//
//    public String getConsumerId() {
//        return ConsumerId;
//    }
//
//    public void setConsumerId(String consumerId) {
//        ConsumerId = consumerId;
//    }
//
//    public String getAccessKey() {
//        return AccessKey;
//    }
//
//    public void setAccessKey(String accessKey) {
//        AccessKey = accessKey;
//    }
//
//    public String getSecretKey() {
//        return SecretKey;
//    }
//
//    public void setSecretKey(String secretKey) {
//        SecretKey = secretKey;
//    }
//
//    public String getConsumeThreadNums() {
//        return ConsumeThreadNums;
//    }
//
//    public void setConsumeThreadNums(String consumeThreadNums) {
//        ConsumeThreadNums = consumeThreadNums;
//    }
//
//    public MemberMessageListener getMemberMessageListener() {
//        return memberMessageListener;
//    }
//
//    public void setMemberMessageListener(MemberMessageListener memberMessageListener) {
//        this.memberMessageListener = memberMessageListener;
//    }
//
//    public String getTopic() {
//        return topic;
//    }
//
//    public void setTopic(String topic) {
//        this.topic = topic;
//    }
//}
