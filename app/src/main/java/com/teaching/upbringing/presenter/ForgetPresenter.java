package com.teaching.upbringing.presenter;



import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.model.ForgetModel;
import com.teaching.upbringing.modular.user.ForgetContract;

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
        getView().showProgress();
        forgetModel.forgetPwd(captcha,password,phone)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity testEntity) {
                        getView().hideProgress();
                        getView().forgetPwd(testEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        super.onError(e);
                    }
                });
    }
}
