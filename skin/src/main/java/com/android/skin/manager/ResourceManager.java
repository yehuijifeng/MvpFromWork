package com.android.skin.manager;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * Created by Luhao on 2016/9/13.
 * 皮肤资源管理器
 * ResourceManager类用于保存当前app的资源对象或者从插件中获取的资源对象，
 * 用于得到资源对象中的资源。实际的换皮肤的资源和方法都由它管理。
 */
public class ResourceManager {

    private Resources mResources;//资源对象
    private String mPluginPackageName;//插件包名
    private String mSuffix;//皮肤区别的后缀名

    //默认图片和颜色类型
    private static final String DEFTYPE_DRAWABLE = "drawable";
    private static final String DEFTYPE_COLOR = "color";

    public ResourceManager(Resources mResources, String mPluginPackageName, String mSuffix) {
        this.mResources = mResources;
        this.mPluginPackageName = mPluginPackageName;
        this.mSuffix = TextUtils.isEmpty(mSuffix) ? "" : mSuffix;
    }

    //将资源名添加后缀，用于app内部的皮肤修改
    private String appendSuffix(String name) {
        //如果设置了皮肤的后缀名，则在资源名称的后面添加后缀名
        //例如：默认皮肤的资源名是：skin_index_drawable
        //如果添加了后缀名，则说明使用了另外一套皮肤：skin_index_drawable_red
        return TextUtils.isEmpty(mSuffix) ? name : name + "_" + mSuffix;
    }

    //传入资源名称，根据包名和资源后缀名来确定返回的资源
    public Drawable getDrawableByName(String name) {
        try {
            name = appendSuffix(name);
            //这段代码的意思相当于在指定的包名下找到指定的资源文件夹名字然后找到指定的资源名称。参数是相反的。
            //参数：1，资源名；2，资源类型；3，包名
            return mResources.getDrawable(mResources.getIdentifier(name, DEFTYPE_DRAWABLE, mPluginPackageName));
        } catch (Resources.NotFoundException e) {
            try {
                //如果在图片中没有找到资源就在颜色资源里找
                return mResources.getDrawable(mResources.getIdentifier(name, DEFTYPE_COLOR, mPluginPackageName));
            } catch (Resources.NotFoundException e2) {
                e.printStackTrace();
                return null;
            }
        }
    }

    //根据资源名获得颜色
    public int getColorByName(String name) {
        try {
            name = appendSuffix(name);
            return mResources.getColor(mResources.getIdentifier(name, DEFTYPE_COLOR, mPluginPackageName));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //根据资源名获得颜色集合
    public ColorStateList getColorStateList(String name) {
        try {
            name = appendSuffix(name);
            return mResources.getColorStateList(mResources.getIdentifier(name, DEFTYPE_COLOR, mPluginPackageName));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据资源名获得图片集合
    public ColorStateList getColorStateListtDrawable(String name) {
        try {
            name = appendSuffix(name);
            return mResources.getColorStateList(mResources.getIdentifier(name, DEFTYPE_DRAWABLE, mPluginPackageName));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
