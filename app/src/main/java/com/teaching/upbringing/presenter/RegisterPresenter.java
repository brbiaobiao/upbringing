package com.teaching.upbringing.presenter;



import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.model.LoginModel;
import com.teaching.upbringing.model.RegisterModel;
import com.teaching.upbringing.modular.user.LoginContract;
import com.teaching.upbringing.modular.user.LoginContract.IPresenter;
import com.teaching.upbringing.modular.user.RegisterContract;
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
        getView().showProgress();
        mMainModels.signInCaptcha(phone)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity testEntity) {
                        getView().hideProgress();
                         getView().signInCaptcha(testEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        super.onError(e);
                    }
                });
    }

    @Override
    public void signIn(String captcha,String invitation, String phone) {
        getView().showProgress();
        mMainModels.signIn(captcha,invitation,phone)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity testEntity) {
                        getView().hideProgress();
                        getView().signIn(testEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        super.onError(e);
                    }
                });
    }


}
