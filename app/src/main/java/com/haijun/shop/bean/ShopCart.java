package com.haijun.shop.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by haijun on 2018/2/4.
 */

public class ShopCart extends BmobObject {
    private String userId;
    private String goodsId;

    public ShopCart() {
    }

    public ShopCart(String userId, String goodsId) {
        this.userId = userId;
        this.goodsId = goodsId;
    }

    public ShopCart(String tableName, String userId, String goodsId) {
        super(tableName);
        this.userId = userId;
        this.goodsId = goodsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "userId='" + userId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                '}';
    }
}
