package com.teaching.upbringing.presenter;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.IdentityAuthStatusEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.model.PersonInforModel;
import com.teaching.upbringing.modular.mine.AuthenticateContract;
import com.teaching.upbringing.modular.mine.StudentIdentityContract;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class AuthenticatePresenter extends Presenter<AuthenticateContract.IView>
        implements AuthenticateContract.Ipresenter {

    private PersonInforModel personInforModel;

    public AuthenticatePresenter(AuthenticateContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        personInforModel = new PersonInforModel();
    }

    @Override
    public void getIdentityAuthStatus() {
        getView().showProgress();
        personInforModel.getIdentityAuthStatus()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<IdentityAuthStatusEntity>() {
                    @Override
                    public void onNext(IdentityAuthStatusEntity identityAuthStatusEntity) {
                        getView().hideProgress();
                        getView().setIdentityAuthStatus(identityAuthStatusEntity);
                    }
                });
    }
}
