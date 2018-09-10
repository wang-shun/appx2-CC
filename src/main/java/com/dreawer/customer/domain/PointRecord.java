package com.dreawer.customer.domain;


import com.dreawer.customer.lang.record.Source;
import com.dreawer.customer.lang.record.Type;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <CODE>PointRecord</CODE>
 * 会员成长值记录
 *
 * @author fenrir
 * @Date 18-3-26
 */
@Data
public class PointRecord implements Serializable {

    private static final long serialVersionUID = 1475885168692186914L;

    private String id; //主键

    private Type type; //成长值增减类型

    private String value; //成长值变更值

    private Source source; //来源

    private String customerId = null; //用户Id

    private String storeId = null; //店铺ID

    private Timestamp createTime = null; // 创建时间


}
