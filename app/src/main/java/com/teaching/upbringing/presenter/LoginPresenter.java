package com.teaching.upbringing.presenter;



import android.util.Log;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.model.LoginModel;
import com.teaching.upbringing.model.MainModel;
import com.teaching.upbringing.modular.user.LoginContract;
import com.teaching.upbringing.modular.user.LoginContract.IPresenter;
import com.teaching.upbringing.modular.webview.WebViewContract;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class LoginPresenter extends Presenter<LoginContract.IView> implements LoginContract.IPresenter{

    private LoginModel mMainModels;

    public LoginPresenter(LoginContract.IView view) {
        super(view);
    }
    @Override
    protected void init() {
        mMainModels = new LoginModel();
    }

    @Override
    public void verification(String phone) {
        mMainModels.loginCaptcha()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity testEntity) {
                        getView().verification(testEntity);
                    }
                });
    }

    @Override
    public void login(String captcha, String phone) {
        mMainModels.captchaLogin()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity testEntity) {
                        getView().login(testEntity);
                    }
                });
    }


}
