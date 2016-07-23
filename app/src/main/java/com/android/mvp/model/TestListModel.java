package com.android.mvp.model;

import android.content.Context;

import com.android.mvp.http.request.RequestAction;
import com.android.mvp.http.request.RetrofitManage;

import rx.Subscription;

/**
 * Created by Luhao on 2016/7/22.
 */
public class TestListModel extends BaseModel {

    public Subscription getGoodsList(Context context, int number) {
        RequestAction.GET_GOODS_LIST.params.getParams().put("shopInfo.typeId", 0);
        RequestAction.GET_GOODS_LIST.params.getParams().put("shopInfo.index", "pub");
        RequestAction.GET_GOODS_LIST.params.getParams().put("pageNum", number);
        //发送请求
        return RetrofitManage.getInstance().sendRequest(RequestAction.GET_GOODS_LIST);
    }
}
