package com.android.skin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.skin.contacts.SkinContact;

/**
 * Created by Luhao on 2016/9/13.
 * 保存用户的皮肤喜好设置
 */
public class SkinSharedPreferencesUtils {

    private SharedPreferences sharedPreferences;//键值对实例

    public SkinSharedPreferencesUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(SkinContact.PREF_NAME, Context.MODE_PRIVATE);
    }

    //获得插件路径
    public String getPluginPath() {
        if (sharedPreferences == null) return "";
        return sharedPreferences.getString(SkinContact.KEY_PLUGIN_PATH, "");
    }

    //添加插件路径
    public void setPluginPath(String pluginPath) {
        if (sharedPreferences == null) return;
        sharedPreferences.edit().putString(SkinContact.KEY_PLUGIN_PATH, pluginPath).apply();
    }

    //获得资源后缀
    public String getAttrSuffix() {
        if (sharedPreferences == null) return "";
        return sharedPreferences.getString(SkinContact.KEY_PLUGIN_SUFFIX, "");
    }

    //添加资源后缀
    public void setAttrSuffix(String attrSuffix) {
        if (sharedPreferences == null) return;
        sharedPreferences.edit().putString(SkinContact.KEY_PLUGIN_SUFFIX, attrSuffix).apply();
    }

    //获得插件的包名
    public String getPluginPackage() {
        if (sharedPreferences == null) return "";
        return sharedPreferences.getString(SkinContact.KEY_PLUGIN_PACKAGE, "");
    }

    //添加插件的包名
    public void setPluginPackage(String pluginPackage) {
        if (sharedPreferences == null) return;
        sharedPreferences.edit().putString(SkinContact.KEY_PLUGIN_PACKAGE, pluginPackage).apply();

    }

    //清理当前的sharedPreferences
    public boolean clear() {
        if (sharedPreferences == null) return false;
        return sharedPreferences.edit().clear().commit();
    }
}
