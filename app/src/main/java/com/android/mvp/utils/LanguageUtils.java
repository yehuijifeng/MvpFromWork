package com.android.mvp.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.DisplayMetrics;

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
    //获取本地保存的语言设置
    private SharedPreferencesUtils sharedPreferences;
    //上下文
    private Context context;

    static {
        localeMap.put(Locale.CHINA.getLanguage(), Locale.CHINA);
        localeMap.put(Locale.ENGLISH.getLanguage(), Locale.ENGLISH);
    }

    public LanguageUtils(Context context) {
        this.context = context;
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
        if (context == null) return null;
        return context.getResources().getConfiguration().locale;
    }

    /**
     * 修改当前语言
     */
    public void setUserLanguage(Locale locale) {
        if (locale == null)
            locale = getUserLanguage();
        if (context == null) return;
        //选择语言
        Configuration config = context.getResources().getConfiguration();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        config.locale = locale;
        context.getResources().updateConfiguration(config, dm);
        sharedPreferences.saveString(AppConstant.DEFAULT_LANGUAGE, locale.getLanguage());
        String country = context.getResources().getConfiguration().locale.getCountry();//国家
    }
}
