package com.teaching.upbringing.presenter;



import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.model.LoginModel;
import com.teaching.upbringing.model.RegisterModel;
import com.teaching.upbringing.modular.user.LoginContract;
import com.teaching.upbringing.modular.user.LoginContract.IPresenter;
import com.teaching.upbringing.modular.user.RegisterContract;

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
        mMainModels.signInCaptcha()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity testEntity) {
                        getView().signInCaptcha(testEntity);
                    }
                });
    }

    @Override
    public void signIn(String captcha, String phone) {
        mMainModels.signIn()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<TestEntity>() {
                    @Override
                    public void onNext(TestEntity testEntity) {
                        getView().signIn(testEntity);
                    }
                });
    }


}
