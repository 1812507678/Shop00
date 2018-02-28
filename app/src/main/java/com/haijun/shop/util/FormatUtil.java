package com.haijun.shop.util;

/**
 * @anthor haijun
 * @project name: Shop
 * @class name：com.haijun.shop.util
 * @time 2018-02-28 2:01 PM
 * @describe
 */
public class FormatUtil {

    //判断字符串是否为数字的方法:
    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
