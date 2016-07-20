package com.android.mvp.utils;

import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.android.mvp.appliaction.MvpAppliaction;
import com.android.mvp.constances.AppConstant;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by yehuijifeng
 * on 2016/1/22.
 * 适配语言
 */
public class LanguageUtils {

    //保存app可适配的语言
    public static Map<String, Locale> localeMap = new HashMap<>();

    static {
        localeMap.put(Locale.CHINA.getLanguage(), Locale.CHINA);
        localeMap.put(Locale.ENGLISH.getLanguage(), Locale.ENGLISH);
    }

    /**
     * 适配本机语言
     */
    public void adapterLanguage() {
        SharedPreferencesUtils sharedPreferences = new SharedPreferencesUtils(MvpAppliaction.getInstance());
        if (TextUtils.isEmpty(sharedPreferences.getString(AppConstant.APP_LANGUAGE)))
            setUserLanguage(getUserLanguage());
        else {
            setUserLanguage(localeMap.get(sharedPreferences.getString(AppConstant.APP_LANGUAGE, AppConstant.DEFAULT_LANGUAGE)));
        }
    }


    /**
     * 获取手机设置的语言
     */
    public Locale getUserLanguage() {
        return MvpAppliaction.getInstance().getResources().getConfiguration().locale;
    }

    /**
     * 修改当前语言
     */
    public String setUserLanguage(Locale locale) {
        //选择语言
        Configuration config = MvpAppliaction.getInstance().getResources().getConfiguration();
        DisplayMetrics dm = MvpAppliaction.getInstance().getResources().getDisplayMetrics();
        config.locale = locale;
        MvpAppliaction.getInstance().getResources().updateConfiguration(config, dm);
        return MvpAppliaction.getInstance().getResources().getConfiguration().locale.getCountry();
    }
}
