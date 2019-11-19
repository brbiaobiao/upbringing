package com.teaching.upbringing.utils;

/**
 * Created by 01 on 2017/3/21.
 */

public final  class RxBus3 {

    /*// 主题
    private final Subject<Object, Object> mBus;
    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBus3() {
        mBus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus3 getDefault() {
        return RxBusHolder.sInstance;
    }

    private static class RxBusHolder {
        private static final RxBus3 sInstance = new RxBus3();
    }


    // 提供了一个新的事件
    public void post(Object o) {
        mBus.onNext(o);
    }


    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }*/
}
