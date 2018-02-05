package com.haijun.shop.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.haijun.shop.application.MyAppliaction;

/**
 * @anthor haijun
 * @project name: Healthy
 * @class name：com.amsu.bleinteractionlibrary.utils
 * @time 12/4/2017 3:50 PM
 * @describe
 */

public class SPUtil {

    public static final String spName = "spinfo";
    //创建一个写入器
    private static SharedPreferences mPreferences;

    public static void putIntValueToSP(String key,int value){
        if (mPreferences==null){
            mPreferences = MyAppliaction.getContext().getSharedPreferences(spName,Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putInt(key,value).apply();
    }

    public static int getIntValueFromSP(String key){
        if (mPreferences==null){
            mPreferences = MyAppliaction.getContext().getSharedPreferences(spName,Context.MODE_PRIVATE);
        }
        return mPreferences.getInt(key,-1);
    }

    public static void putStringValueToSP(String key,String value){
        if (mPreferences==null){
            mPreferences = MyAppliaction.getContext().getSharedPreferences(spName,Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putString(key,value).apply();
    }

    public static String getStringValueFromSP(String key){
        if (mPreferences==null){
            mPreferences = MyAppliaction.getContext().getSharedPreferences(spName,Context.MODE_PRIVATE);
        }
        return mPreferences.getString(key,"");
    }

    public static void putBooleanValueToSP(String key,boolean value){
        if (mPreferences==null){
            mPreferences = MyAppliaction.getContext().getSharedPreferences(spName,Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putBoolean(key,value).apply();
    }

    public static boolean getBooleanValueFromSP(String key){
        if (mPreferences==null){
            mPreferences = MyAppliaction.getContext().getSharedPreferences(spName,Context.MODE_PRIVATE);
        }
        return mPreferences.getBoolean(key,false);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     *
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {
        SharedPreferences.Editor editor = mPreferences.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        }
        else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        }
        else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        }
        else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        }
        else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        }
        else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    //清楚SP里的所有数据
    public static void clearAllSPData(){
        mPreferences.edit().clear().apply();
    }

}