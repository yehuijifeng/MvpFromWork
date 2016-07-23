package com.android.mvp.bean.test;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.mvp.http.request.RequestUrls;

import java.util.List;

/**
 * Created by Luhao on 2016/6/24.
 */
public class GoodsListBean implements Parcelable {


    /**
     * shopZhPj : 0.0
     * shopSpecList : [{"specId":47,"shopId":128,"specName":"红烧牛肉面*1桶","specPrice":4,"specNum":4118},{"specId":48,"shopId":128,"specName":"红烧牛肉面*12桶","specPrice":48,"specNum":2161}]
     * shopId : 128
     * shopName : 康师傅 红烧牛肉面（桶）111g*12桶
     * shopIcon : upload/img/shopInfo/thum/1458703171199224140.png
     * shopImg : upload/img/noImg.png
     * isGuige : 0
     * shopStock : 222
     * showPrice : 4.0
     * shopPjNum : 0
     */
    private double shopZhPj;
    private int shopId;
    private String shopName;
    private String shopIcon;
    private String shopImg;
    private int isGuige;
    private int shopStock;
    private double showPrice;
    private int shopPjNum;
    private List<ShopSpecListBean> shopSpecList;

    public GoodsListBean() {
    }

    protected GoodsListBean(Parcel in) {
        shopZhPj = in.readDouble();
        shopId = in.readInt();
        shopName = in.readString();
        shopIcon = in.readString();
        shopImg = in.readString();
        isGuige = in.readInt();
        shopStock = in.readInt();
        showPrice = in.readDouble();
        shopPjNum = in.readInt();
        shopSpecList = in.createTypedArrayList(ShopSpecListBean.CREATOR);
    }

    public static final Creator<GoodsListBean> CREATOR = new Creator<GoodsListBean>() {
        @Override
        public GoodsListBean createFromParcel(Parcel in) {
            return new GoodsListBean(in);
        }

        @Override
        public GoodsListBean[] newArray(int size) {
            return new GoodsListBean[size];
        }
    };

    public double getShopZhPj() {
        return shopZhPj;
    }

    public void setShopZhPj(double shopZhPj) {
        this.shopZhPj = shopZhPj;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopIcon() {
        return RequestUrls.IP+shopIcon;
    }

    public void setShopIcon(String shopIcon) {
        this.shopIcon = shopIcon;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public int getIsGuige() {
        return isGuige;
    }

    public void setIsGuige(int isGuige) {
        this.isGuige = isGuige;
    }

    public int getShopStock() {
        return shopStock;
    }

    public void setShopStock(int shopStock) {
        this.shopStock = shopStock;
    }

    public double getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(double showPrice) {
        this.showPrice = showPrice;
    }

    public int getShopPjNum() {
        return shopPjNum;
    }

    public void setShopPjNum(int shopPjNum) {
        this.shopPjNum = shopPjNum;
    }

    public List<ShopSpecListBean> getShopSpecList() {
        return shopSpecList;
    }

    public void setShopSpecList(List<ShopSpecListBean> shopSpecList) {
        this.shopSpecList = shopSpecList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(shopZhPj);
        dest.writeInt(shopId);
        dest.writeString(shopName);
        dest.writeString(shopIcon);
        dest.writeString(shopImg);
        dest.writeInt(isGuige);
        dest.writeInt(shopStock);
        dest.writeDouble(showPrice);
        dest.writeInt(shopPjNum);
        dest.writeTypedList(shopSpecList);
    }
}
