package com.dreawer.customer.web.form;

import java.math.BigDecimal;

/**
 * <CODE>GoodsInfoForm</CODE>
 *
 * @author fenrir
 * @Date 18-7-27
 */
public class GoodsInfoForm {

    private String spuId = null; // 商品ID

    private String skuId = null; // 店铺ID

    private BigDecimal price = null; // 售价

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
