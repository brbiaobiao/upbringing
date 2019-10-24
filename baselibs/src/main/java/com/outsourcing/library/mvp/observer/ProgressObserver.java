package com.outsourcing.library.mvp.observer;

import android.widget.Toast;

import com.outsourcing.library.application.BaseApplication;
import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.utils.NetWorkUtils;

import io.reactivex.disposables.Disposable;

/**
 * Created by LJJ on 2017/11/21.
 * use to:拥有加载框的订阅者
 */
public abstract class ProgressObserver<T> extends BasicObserver<T> {
    private IProgressAble mProgressAble;
    
    public ProgressObserver(IProgressAble progressAble) {
        this.mProgressAble = progressAble;
    }
    
    @Override
    public void onSubscribe(Disposable d) {
        if (!NetWorkUtils.isAvailable(BaseApplication.getIntansce())) {
            Toast.makeText(BaseApplication.getIntansce(), "当前网络不可用，请检查网络情况", Toast.LENGTH_SHORT).show();
            onComplete();
            return;
        }
        showLoadingProgress();
    }
    
    @Override
    public void onError(Throwable e) {
        super.onError(e);
        closeLoadingProgress();
    }
    
    @Override
    public void onComplete() {
        closeLoadingProgress();
    }
    
    protected void showLoadingProgress(){
        mProgressAble.showProgress();
    }
    
    protected void closeLoadingProgress(){
        mProgressAble.hideProgress();
    }
}
