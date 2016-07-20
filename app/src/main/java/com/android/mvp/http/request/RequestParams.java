package com.android.mvp.http.request;

import java.util.HashMap;

/**
 * Created by Luhao on 2016/6/23.
 */
public class RequestParams {

    public RequestParams() {
        params = new HashMap<>();
    }

    private HashMap<String, Object> params;//请求参数

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

}
