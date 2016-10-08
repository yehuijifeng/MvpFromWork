package com.android.skin.interfaces;

/**
 * Created by Luhao on 2016/9/13.
 * 设置皮肤状态回调
 */
public interface ISkinCallback {
    void onStart();//开始

    void onError(Exception e);//错误

    void onComplete();//完成
}
