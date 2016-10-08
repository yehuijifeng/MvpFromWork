package com.android.skin.manager;

import android.view.View;

import java.util.List;

/**
 * Created by Luhao on 2016/9/13.
 * 1，该类提供了一个activity所有view的资源替换方法
 */
public class SkinView {
    private View view;//需要改变皮肤的view
    private List<SkinAttr> attrs;//这个view下所有的资源实例

    //传入view和所有资源
    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.view = view;
        this.attrs = skinAttrs;
    }

    //执行换肤，将该view下的所有资源都进行换肤
    public void apply() {
        if (view == null) return;
        for (SkinAttr attr : attrs) {
            //skinattr中有针对单个资源的换肤
            attr.apply(view);
        }
    }
}
