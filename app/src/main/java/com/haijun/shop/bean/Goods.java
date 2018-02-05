package com.haijun.shop.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by haijun on 2018/2/2.
 */

public class Goods extends BmobObject implements Parcelable{
    private String name;
    private String logoUrl;
    private String specification;
    private float curPrice;
    private float oldPrice;
    private List<String> photoImageUrlList;
    private List<String> intrImageUrlList;
    private ProductCategory.ProductCategoryType categoryType;

    public Goods(String name, String logoUrl, String specification, float curPrice, float oldPrice, List<String> photoImageUrlList, List<String> introduceImageUrlList) {
        this.name = name;
        this.logoUrl = logoUrl;
        this.specification = specification;
        this.curPrice = curPrice;
        this.oldPrice = oldPrice;
        this.photoImageUrlList = photoImageUrlList;
        this.intrImageUrlList = introduceImageUrlList;
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

    public float getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(float curPrice) {
        this.curPrice = curPrice;
    }

    public float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", specification='" + specification + '\'' +
                ", curPrice=" + curPrice +
                ", oldPrice=" + oldPrice +
                ", photoImageUrlList=" + photoImageUrlList +
                ", intrImageUrlList=" + intrImageUrlList +
                ", categoryType=" + categoryType +
                '}';
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }


    public List<String> getPhotoImageUrlList() {
        return photoImageUrlList;
    }

    public void setPhotoImageUrlList(List<String> photoImageUrlList) {
        this.photoImageUrlList = photoImageUrlList;
    }

    public List<String> getIntrImageUrlList() {
        return intrImageUrlList;
    }

    public void setIntrImageUrlList(List<String> intrImageUrlList) {
        this.intrImageUrlList = intrImageUrlList;
    }

    public ProductCategory.ProductCategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(ProductCategory.ProductCategoryType categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*private String name;
    private String logoUrl;
    private String specification;
    private float curPrice;
    private float oldPrice;
    private List<String> photoImageUrlList;
    private List<String> intrImageUrlList;
    private ProductCategory.ProductCategoryType categoryType;*/
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(logoUrl);
        parcel.writeString(specification);
        parcel.writeFloat(curPrice);
        parcel.writeFloat(oldPrice);
        parcel.writeList(photoImageUrlList);
        parcel.writeList(intrImageUrlList);
        parcel.writeInt(categoryType.ordinal());
        parcel.writeString(getObjectId());
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel source) {
            return new Goods(source);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public Goods(Parcel source){
        this.name = source.readString();
        this.logoUrl = source.readString();
        this.specification = source.readString();
        this.curPrice = source.readFloat();
        this.oldPrice = source.readFloat();

        this.photoImageUrlList = new ArrayList<>();
        source.readList(this.photoImageUrlList,null);

        this.intrImageUrlList = new ArrayList<>();
        source.readList(this.intrImageUrlList,null);

        this.categoryType = ProductCategory.ProductCategoryType.values()[source.readInt()];

        this.setObjectId(source.readString());
    }
}
