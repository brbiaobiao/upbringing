package com.teaching.upbringing.presenter;


import com.outsourcing.library.mvp.observer.NextObserver;
import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.model.LoginModel;
import com.teaching.upbringing.modular.user.LoginContract;
import com.teaching.upbringing.utils.PhoneUtil;
import com.teaching.upbringing.utils.ToastUtil;

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
        if(!PhoneUtil.checkPhone(phone)) {
            return;
        }
        getView().showProgress();
        mMainModels.loginCaptcha(phone)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity testEntity) {
                        getView().hideProgress();
                        ToastUtil.showShort("获取验证码成功");
                        getView().verification(testEntity);
                    }
                });
    }

    @Override
    public void login(String captcha, String phone) {
        if(!PhoneUtil.checkPhone(phone)) {
            return;
        }
        getView().showProgress();
        mMainModels.captchaLogin(captcha,phone)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<UserInfoEntity>() {
                    @Override
                    public void onNext(UserInfoEntity userInfoEntity) {
                        getView().hideProgress();
                        getView().login(userInfoEntity);
                    }
                });
    }
}
