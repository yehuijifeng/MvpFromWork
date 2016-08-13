package com.android.mvp.utils;

import android.content.Context;
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
    private SharedPreferencesUtils sharedPreferences;

    static {
        localeMap.put(Locale.CHINA.getLanguage(), Locale.CHINA);
        localeMap.put(Locale.ENGLISH.getLanguage(), Locale.ENGLISH);
    }

    public LanguageUtils(Context context) {
        sharedPreferences = new SharedPreferencesUtils(context);
    }

    /**
     * 适配本机语言
     */
    public void adapterLanguage() {
        String language = sharedPreferences.getString(AppConstant.APP_LANGUAGE);
        if (TextUtils.isEmpty(language))
            setUserLanguage(getUserLanguage());
        else {
            setUserLanguage(localeMap.get(language));
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
        if (locale == null)
            locale = getUserLanguage();
        //选择语言
        Configuration config = MvpAppliaction.getInstance().getResources().getConfiguration();
        DisplayMetrics dm = MvpAppliaction.getInstance().getResources().getDisplayMetrics();
        config.locale = locale;
        MvpAppliaction.getInstance().getResources().updateConfiguration(config, dm);
        sharedPreferences.saveString(AppConstant.DEFAULT_LANGUAGE, locale.getLanguage());
        return MvpAppliaction.getInstance().getResources().getConfiguration().locale.getCountry();
    }
}
