package com.dreawer.customer.form;

import com.dreawer.customer.web.form.GoodsInfoForm;

import java.util.List;

/**
 * <CODE>DiscountQueryForm</CODE>
 * 计算会员折扣表单
 * @author fenrir
 * @Date 18-7-27
 */
public class DiscountQueryForm {

    private List<GoodsInfoForm> goodsInfo;

    private String userId;


    public List<GoodsInfoForm> getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(List<GoodsInfoForm> goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
