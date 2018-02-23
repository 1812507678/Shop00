package com.haijun.shop.util;

import com.google.gson.reflect.TypeToken;
import com.haijun.shop.bean.Goods;

import java.util.List;

/**
 * @anthor haijun
 * @project name: Shop
 * @class name：com.haijun.shop.util
 * @time 2018-02-23 9:54 AM
 * @describe 缓存工具类
 */
public class CacheUtil{

    public static void putGoodsToCache(List<Goods> list){
        SPUtil.putListToSP(list,"goodsCache");
    }

    public static List<Goods> getGoodsFromCache(){
        return SPUtil.getListFromSP("goodsCache",new TypeToken<List<Goods>>() {}.getType());
    }




}
