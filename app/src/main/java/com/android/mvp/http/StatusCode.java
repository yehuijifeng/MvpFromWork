package com.android.mvp.http;

/**
 * Created by yehuijifeng
 * on 2015/9/9 11:56.
 * 网络请求状态
 */
public class StatusCode {
    /**
     * 状态码:请求成功
     */
    public static final int REQUEST_SUCCESS = 200;

    /**
     * 状态码：没有更多数据
     */
    public static final int NOT_MORE_DATA = 201;

    /**
     * 状态码：服务器繁忙
     */
    public static final int SERVER_BUSY = 300;
    /**
     * 状态码：签名错误
     */
    public static final int SIGNATURE_ERROR = 313;
    /**
     * 状态码：服务器错误
     */
    public static final int SERVER_ERROR = 500;
    /**
     * 状态码：服务器错误
     */
    public static final int NETWORK_ERROR = -1;

}
