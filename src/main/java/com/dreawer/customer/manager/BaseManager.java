package com.dreawer.customer.manager;

import com.dreawer.customer.domain.Hierarchy;
import com.dreawer.customer.domain.Member;
import com.dreawer.customer.lang.member.Expiration;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * <CODE>BaseManager</CODE>
 * @author fenrir
 */
public class BaseManager {


    protected static Date getNow(){
        return new Date(System.currentTimeMillis());
    }

    protected Member setDueDate(Hierarchy node, Member member) {
        if (node.getExpiration().equals(Expiration.LIMITED)){
            //重新计算到期时间
            Integer period = node.getPeriod();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,period);
            //设置会员有效期
            member.setDueDate(new Timestamp(calendar.getTimeInMillis()));
        }
        return member;
    }



}
