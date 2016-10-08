package com.android.skin.contacts;

/**
 * Created by Luhao on 2016/9/13.
 * 皮肤常量
 */
public class SkinContact {
    public static final String PREF_NAME = "skin_plugin_name";//插件工厂名
    public static final String KEY_PLUGIN_PATH = "key_plugin_path";//插件路径
    public static final String KEY_PLUGIN_PACKAGE = "key_plugin_package";//插件包名
    public static final String KEY_PLUGIN_SUFFIX = "key_plugin_suffix";//插件后缀
    public static final String ATTR_PREFIX = "skin:";//资源验证的前缀，只有带有skin的前缀才是要被修改的前缀
    public static final int SKIN_TAG = 0x5201314;//表单验证，获得当前view的所有名字
}
