package com.dreawer.customer.schedule;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * <CODE>QuarzConfiguraction</CODE>
 * 定时器配置类
 * @author fenrir
 * @Date 18-4-23
 */
@Configuration
public class QuartzConfiguration {

    // 配置定时任务1
    @Bean(name = "MemberExpireJobDetail")
    public MethodInvokingJobDetailFactoryBean MemberExpireJobDetail(Task task) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(task);
        // 需要执行的方法
        jobDetail.setTargetMethod("memberExpireHandler");
        return jobDetail;
    }


    // 配置触发器1
    @Bean(name = "MemberExpireTrigger")
    public CronTriggerFactoryBean MemberExpireTrigger(JobDetail firstJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(firstJobDetail);
        // 设置任务启动延迟
        trigger.setStartDelay(0);
        trigger.setCronExpression("0 0 5 * * ?");
        trigger.setName("MemberExpireTrigger");
        return trigger;
    }


    // 配置Scheduler
    @Bean(name = "scheduler")
    //需要多个任务,就在传入参数加入不同的Trigger
    public SchedulerFactoryBean schedulerFactory(Trigger MemberExpireTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        //bean.setTriggers(firstTrigger,secondTrigger); 注册多个
        bean.setTriggers(MemberExpireTrigger);
        return bean;
    }

      /*// 配置定时任务2
    @Bean(name = "secondJobDetail")
    public MethodInvokingJobDetailFactoryBean secondJobDetail(SecondJob secondJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(secondJob);
        // 需要执行的方法
        jobDetail.setTargetMethod("task");
        return jobDetail;
    }*/

    /*// 配置触发器2
    @Bean(name = "secondTrigger")
    public CronTriggerFactoryBean secondTrigger(JobDetail secondJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(secondJobDetail);
        // cron表达式
        trigger.setCronExpression("0 30 20 * * ?");
        return trigger;
    }
*/
}

