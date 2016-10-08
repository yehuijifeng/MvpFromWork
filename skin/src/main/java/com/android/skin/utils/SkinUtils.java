package com.android.skin.utils;

import android.text.TextUtils;

import com.android.skin.interfaces.ISkinCallback;
import com.android.skin.manager.SkinManager;

/**
 * Created by Luhao on 2016/9/13.
 * <p>
 * 更换皮肤使用方法：
 * 在调用该工具类中的方法前应该做的事情，在xml布局文件中给需要换肤的控件设置tag
 * <p>
 * 设置tag的规则如下：
 * "skin："开头
 * "资源名称:"
 * "资源属性"
 * <p>
 * 例：
 * <LinearLayout
 * android:id="@+id/toolbar_ly"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:background="@color/title_color"
 * android:tag="skin:title_color:background">
 * <p>
 * linearlayout中要换肤的资源名称是title_color
 * linearlayout中要换肤的资源属性是background
 * 那么，tag的写法就是：skin:title_color:backgroud
 * <p>
 * 如果换肤标签是：red
 * 那么需要在app内部资源中就要有相对应的名称：title_color_red
 * 以"_"+"标签名"
 */
public class SkinUtils {

    /**
     * 更换内部资源，根据后缀名
     *
     * @param skinType 后缀名
     */
    public void changeInternalSkinByPostfix(String skinType) {
        if (TextUtils.isEmpty(skinType)) return;
        SkinManager.getInstance().changeSkin(skinType);
    }

    /**
     * 更换外部资源，定位绝对路径，同app内部原生资源名相同
     *
     * @param skinPath      资源包绝对路径
     * @param skinBackage   资源包包名
     * @param iSkinCallback 回调接口
     */
    public void changeExternalSkin(String skinPath, String skinBackage, final ISkinCallback iSkinCallback) {
        SkinManager.getInstance().changeSkin(skinPath, skinBackage, iSkinCallback);
    }

    /**
     * 更换外部资源，定位绝对路径，同app内部原生资源名的拓展名相同
     *
     * @param skinPath      资源包绝对路径
     * @param skinBackage   资源包包名
     * @param Postfix       资源拓展名
     * @param iSkinCallback 回调接口
     */
    public void changeExternalSkinByPostfix(String skinPath, String skinBackage, String Postfix, final ISkinCallback iSkinCallback) {
        SkinManager.getInstance().changeSkin(skinPath, skinBackage, Postfix, iSkinCallback);
    }
}
