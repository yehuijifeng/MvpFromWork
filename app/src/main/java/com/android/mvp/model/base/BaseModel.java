package com.android.mvp.model.base;

import android.content.Context;

/**
 * Created by Luhao on 2016/6/23.
 * model层的基类
 */
public abstract class BaseModel {

    private Context context;

    protected BaseModel(Context context) {
        this.context = context;
    }

    protected BaseModel() {
    }

    public Context getContext() {
        return context;
    }

    public void closeModel() {
        context = null;
    }
}
