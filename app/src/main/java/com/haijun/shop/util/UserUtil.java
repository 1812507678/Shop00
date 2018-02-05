package com.haijun.shop.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.haijun.shop.bean.User;

/**
 * @anthor haijun
 * @project name: Shop
 * @class name：com.haijun.shop.util
 * @time 2018-02-02 11:29 AM
 * @describe
 */
public class UserUtil {

    public static User user;
    public static User getUserInfo() {
        if (user==null){
            user = UserUtil.getUserFromSP();
        }
        return user;
    }

    public static User getUserFromSP(){
        Gson gson = new Gson();
        String userString = SPUtil.getStringValueFromSP("user");
        if (!TextUtils.isEmpty(userString)){
            return gson.fromJson(userString,User.class);
        }
        return null;
    }

    public static void putUserToSP(User user){
        Gson gson = new Gson();
        String userString = gson.toJson(user);
        SPUtil.putStringValueToSP("user",userString);
    }
}
