package com.teaching.upbringing.utils;

import rx.Subscriber;

/**
 * Created by 01 on 2017/3/21.
 */

public abstract class SimpleSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }
}
