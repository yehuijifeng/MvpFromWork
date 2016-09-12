package com.android.mvp.model;

import android.os.Handler;

import com.android.mvp.model.base.BaseModel;
import com.android.mvp.utils.AppUtils;
import com.android.mvp.utils.LanguageUtils;

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
    public void reStartApp(final Class cla) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppUtils.reStartApp(cla);
            }
        }, 1000);

    }
}
