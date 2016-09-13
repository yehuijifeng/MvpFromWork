package com.android.mvp.model;

import android.os.Handler;

import com.android.mvp.model.base.BaseModel;
import com.android.mvp.utils.AppUtils;
import com.android.mvp.utils.LanguageUtils;
import com.android.skin.interfaces.ISkinCallback;
import com.android.skin.utils.SkinUtils;

import java.util.Locale;

/**
 * Created by Luhao on 2016/9/12.
 */
public class TestSettingsModel extends BaseModel {

    /**
     * 设置本机语言
     *
     * @param locale 修改的语言
     * @param cla    重启app后跳转的页面
     */
    public void settingLanguage(Locale locale, Class cla) {
        new LanguageUtils().setUserLanguage(locale);
        reStartApp(cla);//重启
    }

    /**
     * 重启app
     *
     * @param cla
     */
    private void reStartApp(final Class cla) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppUtils.reStartApp(cla);
            }
        }, 1000);
    }

    /**
     * 更换内部资源，根据后缀名
     *
     * @param skinType 后缀名
     */
    public void changeInternalSkinByPostfix(String skinType) {
        SkinUtils skinUtils = new SkinUtils();
        skinUtils.changeInternalSkinByPostfix(skinType);
    }

    /**
     * 更换外部资源，定位绝对路径，同app内部原生资源名相同
     *
     * @param skinPath      资源包绝对路径
     * @param skinBackage   资源包包名
     * @param iSkinCallback 回调接口
     */
    public void changeExternalSkin(String skinPath, String skinBackage, ISkinCallback iSkinCallback) {
        SkinUtils skinUtils = new SkinUtils();
        skinUtils.changeExternalSkin(skinPath, skinBackage, iSkinCallback);
    }

    /**
     * 更换外部资源，定位绝对路径，同app内部原生资源名的拓展名相同
     *
     * @param skinPath      资源包绝对路径
     * @param skinBackage   资源包包名
     * @param Postfix       资源拓展名
     * @param iSkinCallback 回调接口
     */
    public void changeExternalSkinByPostfix(String skinPath, String skinBackage, String Postfix, ISkinCallback iSkinCallback) {
        SkinUtils skinUtils = new SkinUtils();
        skinUtils.changeExternalSkinByPostfix(skinPath, skinBackage, Postfix, iSkinCallback);
    }
}
