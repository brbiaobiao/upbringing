package com.teaching.upbringing.presenter;



import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.model.LoginModel;
import com.teaching.upbringing.modular.user.LoginContract;

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
        mMainModels.captchaLogin(captcha,phone)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<UserInfoEntity>() {
                    @Override
                    public void onNext(UserInfoEntity userInfoEntity) {
                        getView().login(userInfoEntity);
                    }
                });
    }


}