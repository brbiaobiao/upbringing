package com.teaching.upbringing.presenter;


import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.model.RegisterModel;
import com.teaching.upbringing.modular.user.RegisterContract;
import com.teaching.upbringing.utils.PhoneUtil;
import com.teaching.upbringing.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class RegisterPresenter extends Presenter<RegisterContract.IView> implements RegisterContract.IPresenter {

    private RegisterModel mMainModels;

    public RegisterPresenter(RegisterContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        mMainModels = new RegisterModel();
    }

    @Override
    public void signInCaptcha(String phone) {
        if (!PhoneUtil.checkPhone(phone)) {
            return;
        }
        getView().showProgress();
        mMainModels.signInCaptcha(phone)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity testEntity) {
                        getView().hideProgress();
                        ToastUtil.showShort("注册成功");
                        getView().signInCaptcha(testEntity);
                    }
                });
    }

    @Override
    public void signIn(String captcha, String invitation, String phone) {
        if (!PhoneUtil.checkPhone(phone)) {
            return;
        }
        getView().showProgress();
        mMainModels.signIn(captcha, invitation, phone)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity testEntity) {
                        getView().hideProgress();
                        getView().signIn(testEntity);
                    }
                });
    }

}
