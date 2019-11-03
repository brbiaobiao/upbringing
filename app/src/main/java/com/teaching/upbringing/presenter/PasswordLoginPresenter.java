package com.teaching.upbringing.presenter;



import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.model.LoginModel;
import com.teaching.upbringing.model.PasswordLoginModel;
import com.teaching.upbringing.modular.user.LoginContract;
import com.teaching.upbringing.modular.user.PasswordLoginContract;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class PasswordLoginPresenter extends Presenter<PasswordLoginContract.IView> implements PasswordLoginContract.IPresenter{

    private PasswordLoginModel mMainModels;

    public PasswordLoginPresenter(PasswordLoginContract.IView view) {
        super(view);
    }
    @Override
    protected void init() {
        mMainModels = new PasswordLoginModel();
    }



    @Override
    public void passwordLogin(String phone, String pwd) {
        getView().showProgress();
        mMainModels.passwordLogin(phone,pwd)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<UserInfoEntity>() {
                    @Override
                    public void onNext(UserInfoEntity userInfoEntity) {
                        getView().hideProgress();
                        getView().passwordLogin(userInfoEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        super.onError(e);
                    }
                });
    }
}
