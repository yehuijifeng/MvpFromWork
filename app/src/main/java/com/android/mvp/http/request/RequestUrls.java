package com.android.mvp.http.request;

/**
 * Created by Luhao on 2016/6/23.
 * 网络请求的url，统一管理
 */
public class RequestUrls {

    public static final String IP = "http://115.29.224.175:9999/";
    public static final String ROOT_URL = IP + "";

    /**
     * 商城首页
     */
    public static final String GET_INDEX_URL = "alsfoxShop/site/index/selectIndexContent.action";

    /**
     * 商品列表
     * params.put("shopInfo.typeId", 0);
     * params.put("shopInfo.index", "pub");
     * params.put("pageNum", 1);
     */
    public static final String GET_GOODS_LIST_URL = "alsfoxShop/site/shop/shopinfo/selectShopInfoList.action";

    //4.6MB
    public static final String GET_DOWN_FILE = "upload/down/shop.apk";

    /**
     * 单文件上传
     * userInfo.usderId
     */
    public static final String GET_UPLOAD_FILE = "alsfoxShop/site/user/updateUserAvatar.action";

}