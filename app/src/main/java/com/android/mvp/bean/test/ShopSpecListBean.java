package com.android.mvp.bean.test;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Luhao on 2016/6/24.
 */
public class ShopSpecListBean implements Parcelable {
    /**
     * specId : 47
     * shopId : 128
     * specName : 红烧牛肉面*1桶
     * specPrice : 4.0
     * specNum : 4118
     */

    private int specId;
    private int shopId;
    private String specName;
    private double specPrice;
    private int specNum;

    public ShopSpecListBean() {
    }

    protected ShopSpecListBean(Parcel in) {
        specId = in.readInt();
        shopId = in.readInt();
        specName = in.readString();
        specPrice = in.readDouble();
        specNum = in.readInt();
    }

    public static final Creator<ShopSpecListBean> CREATOR = new Creator<ShopSpecListBean>() {
        @Override
        public ShopSpecListBean createFromParcel(Parcel in) {
            return new ShopSpecListBean(in);
        }

        @Override
        public ShopSpecListBean[] newArray(int size) {
            return new ShopSpecListBean[size];
        }
    };

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public double getSpecPrice() {
        return specPrice;
    }

    public void setSpecPrice(double specPrice) {
        this.specPrice = specPrice;
    }

    public int getSpecNum() {
        return specNum;
    }

    public void setSpecNum(int specNum) {
        this.specNum = specNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(specId);
        dest.writeInt(shopId);
        dest.writeString(specName);
        dest.writeDouble(specPrice);
        dest.writeInt(specNum);
    }
}
