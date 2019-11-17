package com.teaching.upbringing.presenter;


import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.model.ForgetModel;
import com.teaching.upbringing.modular.user.ForgetContract;
import com.teaching.upbringing.utils.PhoneUtil;
import com.teaching.upbringing.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class ForgetPresenter extends Presenter<ForgetContract.IView> implements ForgetContract.IPresenter {

    private ForgetModel forgetModel;

    public ForgetPresenter(ForgetContract.IView view) {
        super(view);
    }
    @Override
    protected void init() {
        forgetModel = new ForgetModel();
    }


    @Override
    public void forgetPwdCaptcha(String phone) {
        if(!PhoneUtil.checkPhone(phone)) {
            return;
        }
        getView().showProgress();
        forgetModel.forgetPwdCaptcha(phone)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity captchaEntity) {
                        getView().hideProgress();
                        getView().startTimecount();
                    }
                });
    }

    @Override
    public void forgetPwd(String captcha, String password, String phone) {
        if(!PhoneUtil.checkPhone(phone)) {
            return;
        }
        getView().showProgress();
        forgetModel.forgetPwd(captcha,password,phone)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity testEntity) {
                        getView().hideProgress();
                        getView().forgetPwd(testEntity);
                    }
                });
    }
}
