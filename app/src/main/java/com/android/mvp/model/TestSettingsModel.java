package com.android.mvp.model;

import android.content.Context;
import android.os.Handler;

import com.android.mvp.appliaction.MvpAppliaction;
import com.android.mvp.model.base.BaseModel;
import com.android.mvp.utils.AppUtils;

import java.util.Locale;

/**
 * Created by Luhao on 2016/9/12.
 */
public class TestSettingsModel extends BaseModel {

    /**
     * 设置本机语言
     *
     * @param context 上下文
     * @param locale  修改的语言
     * @param cla     重启app后跳转的页面
     */
    public void settingLanguage(Context context, Locale locale, Class cla) {
        if (MvpAppliaction.getInstance().languageUtils == null)
            return;
        else
            MvpAppliaction.getInstance().languageUtils.setUserLanguage(locale);
        reStartApp(context, cla);
    }

    /**
     * 重启app
     *
     * @param context
     * @param cla
     */
    public void reStartApp(final Context context, final Class cla) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppUtils.reStartApp(context, cla);
            }
        }, 3000);

    }
}
