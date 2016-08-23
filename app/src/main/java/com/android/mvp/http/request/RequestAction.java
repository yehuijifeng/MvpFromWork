package com.android.mvp.http.request;


import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;

/**
 * Created by Luhao on 2016/6/23.
 * 网络请求的主要枚举
 */
public enum RequestAction {

    GET_GOODS_LIST(new RequestParams()) {
        @Override
        public void getRequest() {
            observable = RetrofitManage.getInstance().getService().getGoodsList(params.getParams());
        }
    },
    GET_GOODS_GRID(new RequestParams()) {
        @Override
        public void getRequest() {
            observable = RetrofitManage.getInstance().getService().getGoodsList(params.getParams());
        }
    },
    GET_DOWN_FILE(new RequestParams()) {
        @Override
        public void getRequest() {
            call = RetrofitManage.getInstance().getService().getDownFile();
        }
    },
    GET_UPLOAD_FILE(new RequestParams()) {
        @Override
        public void getRequest() {
            //上传文件的提交在retrofitmanager中完成
        }
    },

    ;
    public Call<ResponseBody> call;//下载文件专用，okhttp类型的回调
    public Observable observable;//普通网络请求使用，网络请求的操作实例
    public RequestParams params;//请求参数实例

    RequestAction(RequestParams paramss) {
        params = paramss;
    }

    //发送请求，生成预请求对象
    public abstract void getRequest();
}
