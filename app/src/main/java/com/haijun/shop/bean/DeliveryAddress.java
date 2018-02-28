package com.haijun.shop.bean;

import cn.bmob.v3.BmobObject;

/**
 * @anthor haijun
 * @project name: Shop
 * @class name：com.haijun.shop.bean
 * @time 2018-02-27 4:49 PM
 * @describe
 */
public class DeliveryAddress extends BmobObject {
    private String name;
    private String phone;
    private String address;
    private boolean isDefault;


    public DeliveryAddress(String name, String phone, String address, boolean isDefault) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.isDefault = isDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "DeliveryAddress{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
