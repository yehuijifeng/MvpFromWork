package com.android.mvp.model;

import com.android.mvp.http.request.RequestAction;
import com.android.mvp.http.request.RetrofitManage;
import com.android.mvp.model.base.BaseModel;

import java.util.Map;

import rx.Subscription;

/**
 * Created by Luhao on 2016/8/23.
 */
public class TestGridModel extends BaseModel {

    public Subscription getGoodsList(int number) {
        Map<String, Object> params = RequestAction.GET_GOODS_LIST.params.getParams();
        params.put("shopInfo.typeId", 0);
        params.put("shopInfo.index", "pub");
        params.put("pageNum", number);
        //发送请求
        return RetrofitManage.getInstance().sendRequest(RequestAction.GET_GOODS_LIST);
    }
}
