package com.android.mvp.function;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Luhao on 2016/6/23.
 */
public class RxBus {

    public static RxBus getDefault() {
        return RxBusInstance.rxBus;
    }

    //内部类
    private static class RxBusInstance {
        private static final RxBus rxBus = new RxBus();
    }

    // 主题，Subject是非线程安全的
    public final Subject bus;

    /**
     * 单例
     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     * 序列化主题
     * 将 Subject转换为一个 SerializedSubject ，类中把线程非安全的PublishSubject包装成线程安全的Subject
     */
    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 注册
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public Observable register(Class eventType) {
        return bus.ofType(eventType);
    }

    /**
     * 发送
     * 提供了一个新的事件用于发送,这时候的Subject是一个观察者
     * ofType将返回一个特定的class类型
     */
    public void post(final Object obj) {
        bus.onNext(obj);
    }

}
