package com.android.mvp.http.download;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Luhao on 2016/6/24.
 * 使用ResponseBody
 * 通过为OkhttpClient添加一个拦截器来使用我们自定义的ProgressResponseBody
 * 实现ProgressListener接口来获取下载进度
 */
public class ProgressHelper {
    private ProgressBean progressBean = new ProgressBean();
    private OnProgressListener mProgressListeners;

    public OkHttpClient.Builder addProgress(OkHttpClient.Builder client, OnProgressListener progressListeners) {
        if (client == null) {
            client = new OkHttpClient.Builder();
        }
        mProgressListeners = progressListeners;
        final OnProgressListener progressListener = new OnProgressListener() {
            //该方法在子线程中运行
            @Override
            public void onProgress(double progress, double total, long bytesRead) {
                progressBean.setProgress(progress);
                progressBean.setContentLength(total);
                progressBean.setBytesRead(bytesRead);
                Observable
                        .create(new Observable.OnSubscribe<ProgressBean>() {
                            @Override
                            public void call(Subscriber<? super ProgressBean> subscriber) {
                                    subscriber.onNext(progressBean);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())//切换到ui线程
                        .subscribe(new Action1<ProgressBean>() {
                            @Override
                            public void call(ProgressBean progressBean) {
                                mProgressListeners.onProgress(progressBean.getProgress(), progressBean.getContentLength(), progressBean.getBytesRead());
                            }
                        });
            }
        };
        //添加拦截器，自定义ResponseBody，添加下载进度
        client.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        });

        return client;
    }
}
