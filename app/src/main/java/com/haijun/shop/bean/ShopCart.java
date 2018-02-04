package com.haijun.shop.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by haijun on 2018/2/4.
 */

public class ShopCart extends BmobObject {
    private String userId;
    private String name;
    private String logoUrl;
    private String specification;
    private float price;

    public ShopCart(String userId, String name, String logoUrl, String specification, float price) {
        this.userId = userId;
        this.name = name;
        this.logoUrl = logoUrl;
        this.specification = specification;
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", specification='" + specification + '\'' +
                ", price=" + price +
                '}';
    }
}
