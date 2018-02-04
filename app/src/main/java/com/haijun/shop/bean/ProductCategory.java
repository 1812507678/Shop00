package com.haijun.shop.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by haijun on 2018/2/4.
 */

public class ProductCategory extends BmobObject {
    private String name;
    private ProductCategoryType productCategoryType;
    private List<Goods> goodsList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public ProductCategory(String name, List<Goods> goodsList) {
        this.name = name;
        this.goodsList = goodsList;
    }

    public ProductCategoryType getProductCategoryType() {
        return productCategoryType;
    }

    public void setProductCategoryType(ProductCategoryType productCategoryType) {
        this.productCategoryType = productCategoryType;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "name='" + name + '\'' +
                ", productCategoryType=" + productCategoryType +
                ", goodsList=" + goodsList +
                '}';
    }

    public ProductCategory(String name, ProductCategoryType productCategoryType, List<Goods> goodsList) {
        this.name = name;
        this.productCategoryType = productCategoryType;
        this.goodsList = goodsList;
    }

    public enum ProductCategoryType implements Parcelable{
        ProductCategory_Daily,ProductCategory_Phone,ProductCategory_Computer,ProductCategory_Camera,ProductCategory_Earphone;

        @Override
        public int describeContents() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ordinal());
        }

        public static final Creator<ProductCategoryType> CREATOR = new Creator<ProductCategoryType>() {
            @Override
            public ProductCategoryType createFromParcel(Parcel in) {
                return  ProductCategoryType.values()[in.readInt()];
            }

            @Override
            public ProductCategoryType[] newArray(int size) {
                return new ProductCategoryType[size];
            }
        };
    }
}
