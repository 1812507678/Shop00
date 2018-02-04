package com.haijun.shop.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haijun.shop.bean.Goods;
import com.haijun.shop.bean.ShopCart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haijun on 2018/2/5.
 */

public class ShopCartUtil {
    private List<ShopCart> goodsShopCartList = new ArrayList<>();
    private static ShopCartUtil shopCartUtil;

    public static ShopCartUtil getInstance(){
        if (shopCartUtil==null){
            shopCartUtil = new ShopCartUtil();
        }
        return shopCartUtil;
    }

    public List<ShopCart> getGoodsShopCartList() {
        return goodsShopCartList;
    }

    public void setGoodsShopCartList(List<ShopCart> goodsShopCartList) {
        this.goodsShopCartList = goodsShopCartList;
    }

    public void addGoodsShopCartList(ShopCart shopCart){
        if (goodsShopCartList!=null){
            goodsShopCartList.add(shopCart);
        }
    }

    public static void addGoodsShopCartListToSP(ShopCart shopCart){
        List<ShopCart> goodsShopCartListFromSP = getGoodsShopCartListFromSP();
        if (goodsShopCartListFromSP!=null){
            goodsShopCartListFromSP.add(shopCart);
            putGoodsShopCartListToSP(goodsShopCartListFromSP);
        }
    }

    public static List<ShopCart> getGoodsShopCartListFromSP(){
        Gson gson = new Gson();
        String shopCartString = SPUtil.getStringValueFromSP("shopCart");
        if (!TextUtils.isEmpty(shopCartString)){
            return gson.fromJson(shopCartString, new TypeToken<List<Goods>>() {
            }.getType());
        }
        return null;
    }

    public static void putGoodsShopCartListToSP(List<ShopCart> goodsList){
        Gson gson = new Gson();
        String shopCartString = gson.toJson(goodsList);
        SPUtil.putStringValueToSP("shopCart",shopCartString);
    }
}
