package com.outsourcing.library.mvp.observer;

import com.outsourcing.library.net.ExceptionHandler;
import com.outsourcing.library.utils.LogUtils;

import io.reactivex.Observer;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public abstract class BasicObserver<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        LogUtils.d("BasicObserver","失败回调");
        ExceptionHandler.handle(e).show();
    }
}
