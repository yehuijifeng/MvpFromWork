package com.android.mvp.constances;

import android.os.Environment;

import java.util.Locale;

/**
 * Created by Luhao on 2016/6/22.
 * app 常量
 */
public class AppConstant {
    /**
     * 防止被实例化
     */
    private AppConstant() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * sd卡根路径
     */
    public static String getSDPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * app根目录
     */
    public final static String APP_PATH = getSDPath() + "/AndroidMvpFromwork/";

    /**
     * 日志标识
     */
    public static final String TAG = "mvp";

    /**
     * app图片缓存路径
     */
    public final static String CACHE_PATH = APP_PATH + "Cache/";

    /**
     * app图片缓存路径
     */
    public final static String CACHE_IMG_PATH = CACHE_PATH + "CacheImage/";

    /**
     * app图片保存路径
     */
    public final static String SAVE_IMG_PATH = APP_PATH + "SaveImage/";

    /**
     * app日志存放路径
     */
    public final static String LOG_PATH = CACHE_PATH + "Log/";

    /**
     * app配置文件存放路径
     */
    public final static String SETTINGS_PATH = APP_PATH + "Settings/";

    /**
     * app文件存放路径
     */
    public final static String FILES_PATH = APP_PATH + "Files/";

    /**
     * 键值对存储标识,整个app的唯一标识
     */
    public final static String APP_SHARE = "app_share_preferences";

    /**
     * 键值对存储是否是第一次进入app的标识
     */
    public static final String IS_ONE_START = "is_one_start_app";

    /**
     * app设置语言的标识
     */
    public final static String APP_LANGUAGE = "app_language";

    /**
     * 默认app语言
     */
    public final static String DEFAULT_LANGUAGE = Locale.CHINA.getLanguage();
}
