package com.android.mvp.adapter.base;

import android.view.View;

/**
 * Created by Luhao on 2016/6/28.
 * 当listview或者gridview需要重用的时候就要实现该方法
 * 该方法的作用在于listview或者gridview出发ui重用的时候会调用该方法
 */
public abstract class BaseViewHolder {

    //传入的是每一个item的父view
    public BaseViewHolder(View itemView) {
        initItemView(itemView);
    }
    //在该方法中实现所有父view的子view
    public abstract void initItemView(View itemView);
}
