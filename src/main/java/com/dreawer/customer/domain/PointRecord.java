package com.dreawer.customer.domain;


import com.dreawer.customer.lang.record.Source;
import com.dreawer.customer.lang.record.Type;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <CODE>PointRecord</CODE>
 * 会员成长值记录
 *
 * @author fenrir
 * @Date 18-3-26
 */
public class PointRecord implements Serializable {

    private static final long serialVersionUID = 1475885168692186914L;

    private String id; //主键

    private Type type; //成长值增减类型

    private String value; //成长值变更值

    private Source source; //来源

    private String customerId = null; //用户Id

    private String storeId = null; //店铺ID

    private Timestamp createTime = null; // 创建时间

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = Type.get(type);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSource() {
        return source.toString();
    }

    public void setSource(String source) {
        this.source = Source.get(source);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PointRecord{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", value='" + value + '\'' +
                ", source=" + source +
                ", customerId='" + customerId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
