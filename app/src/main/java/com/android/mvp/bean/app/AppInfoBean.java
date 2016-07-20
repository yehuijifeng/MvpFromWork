package com.android.mvp.bean.app;

import android.graphics.drawable.Drawable;

/**
 * Created by Luhao on 2016/6/22.
 * app的信息
 */
public class AppInfoBean {

    private String appName;//app名称
    private String packageName;//包名
    private String versionName;//版本名称
    private int versionCode;//版本号
    private Drawable icon;//app图标
    private String appSignature;//app签名
    private String[] appPremission;//app所有权限

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppSignature() {
        return appSignature;
    }

    public void setAppSignature(String appSignature) {
        this.appSignature = appSignature;
    }

    public String[] getAppPremission() {
        return appPremission;
    }

    public void setAppPremission(String[] appPremission) {
        this.appPremission = appPremission;
    }
}
