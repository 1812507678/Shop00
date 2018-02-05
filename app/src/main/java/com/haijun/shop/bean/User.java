package com.haijun.shop.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/7/15.
 */
public class User extends BmobObject {
    private String nickname;
    private String password;
    private String phone;
    private BmobFile icon;
    private String iconUrl;

    public User() {
    }

    public User(String nickname, String password, String phone, BmobFile icon, String iconUrl) {
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.icon = icon;
        this.iconUrl = iconUrl;
    }

    public User(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", icon=" + icon +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

}
