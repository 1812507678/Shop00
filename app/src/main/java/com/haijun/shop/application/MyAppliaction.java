package com.haijun.shop.application;

import android.app.Application;
import android.content.Context;

import com.umeng.commonsdk.UMConfigure;

import org.xutils.x;

import cn.bmob.v3.Bmob;

/**
 * @anthor haijun
 * @project name: Shop
 * @class name：com.haijun.shop.application
 * @time 2018-01-31 6:18 PM
 * @describe
 */
public class MyAppliaction extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);  //xUtil初始化
        Bmob.initialize(this, "34394e9af01578393b2bbf063e9faa37");   //Bmob初始化
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);   //友盟初始化

        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }


}
