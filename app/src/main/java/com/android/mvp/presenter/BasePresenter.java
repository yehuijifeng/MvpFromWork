package com.android.mvp.presenter;

import com.android.mvp.function.RxBus;
import com.android.mvp.http.request.RequestAction;
import com.android.mvp.http.request.RetrofitManage;
import com.android.mvp.http.response.ResponseAction;
import com.android.mvp.view.interfaces.base.IBaseView;

import rx.Observable;
import rx.Subscription;

/**
 * Created by 浩 on 2016/7/20.
 * presenter层的基类
 */
public abstract class BasePresenter<T extends IBaseView> {
    protected T mView;//view 层实例
    public Subscription subscription;//rx的观察者
    public Observable observable;//RxBus 实例

    /**
     * 每个继承基类的presenter都要去实现构造方法，并传入view层
     *
     * @param mView
     */
    public BasePresenter(T mView) {
        this.mView = mView;
    }

    /**
     * 每个页面都会使用的网络请求事件
     *
     * @param requesteAction
     */
    public void sendRequest(RequestAction requesteAction) {
        subscription = RetrofitManage.getInstance().sendRequest(requesteAction);
    }

    public void onResume() {
        //注册用户信息事件
        observable = RxBus.getDefault().register(ResponseAction.class);
    }

    public void onStop() {
        unsubscride();
    }

    public void unsubscride() {
        if (subscription != null && !subscription.isUnsubscribed())
            //如果订阅取消订阅
            subscription.unsubscribe();
    }

    /**
     * 因为业务层持有view层的对象，所以需要在activity销毁的时候释放对象
     */
    public void onDestory() {
        mView = null;
    }
}
