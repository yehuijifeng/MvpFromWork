package com.android.skin.manager;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Luhao on 2016/9/13.
 * 3,资源类型枚举
 */
public enum SkinAttrType {

    BACKGROUD("background")//背景，将给传入的view设定新的背景
            {
                @Override
                public void apply(View view, String resName) {
                    //背景可能是图片也可能只是颜色
                    Drawable drawable = getResourceManager().getDrawableByName(resName);
                    if (drawable == null) return;
                    view.setBackgroundDrawable(drawable);
                    //LogUtils.i("背景：" + resName + "view的id：" + view.getId());
                }
            },
    TEXT_COLOR("textColor")//字体颜色，将给传入的view设定新的字体颜色
            {
                @Override
                public void apply(View view, String resName) {
                    if (view instanceof TextView) {
                        ColorStateList colorlist = getResourceManager().getColorStateList(resName);
                        if (colorlist == null) return;
                        ((TextView) view).setTextColor(colorlist);
                        //LogUtils.i("字体颜色：" + resName + "view的id：" + view.getId());
                    }
                }
            },
    SRC("src")//src图片，将给传入的view指定新的图片
            {
                @Override
                public void apply(View view, String resName) {
                    if (view instanceof ImageView) {
                        Drawable drawable = getResourceManager().getDrawableByName(resName);
                        if (drawable == null) return;
                        ((ImageView) view).setImageDrawable(drawable);
                        //LogUtils.i("src图片：" + resName + "view的id：" + view.getId());
                    }
                }
            };

    private String attrType;//资源类型

    //提供外部调用方法，获得当前资源的类型
    public String getAttrType() {
        return attrType;
    }

    //构造方法
    SkinAttrType(String attrType) {
        this.attrType = attrType;
    }

    //抽象方法传入需要改变的view和资源名
    protected abstract void apply(View view, String resName);

    //获得资源管理器
    public ResourceManager getResourceManager() {
        ResourceManager resourceManager = SkinManager.getInstance().getResourceManager();
        return resourceManager;
    }
}
