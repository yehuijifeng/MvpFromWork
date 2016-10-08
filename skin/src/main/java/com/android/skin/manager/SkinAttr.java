package com.android.skin.manager;

import android.view.View;

/**
 * Created by Luhao on 2016/9/13.
 * 2，该类保存了一个view下的资源名和资源类型，对应关系
 */
public class SkinAttr {
    private String resName;//资源名
    private SkinAttrType attrType;//资源类型

    //构造方法中传入资源类型实例和资源名
    public SkinAttr(SkinAttrType attrType, String resName) {
        this.resName = resName;
        this.attrType = attrType;
    }

    //执行换肤，对于传进来的view换肤成相对应传进来的资源名
    protected void apply(View view) {
        //枚举中有具体的换肤操作
        attrType.apply(view, resName);
    }
}
