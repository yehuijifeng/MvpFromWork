package com.android.skin.interfaces;

/**
 * Created by Luhao on 2016/9/13.
 * 通知皮肤设置的接口
 * 执行换肤的时候其实就是调用的ISkinListener接口而已。所有继承它的view都能得到换肤的通知。
 */
public interface ISkinListener {
    //这个接口用于通知应该改变皮肤资源了
    void onSkinChanged();
}
