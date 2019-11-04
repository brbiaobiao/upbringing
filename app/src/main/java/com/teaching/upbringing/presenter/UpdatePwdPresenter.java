package com.teaching.upbringing.presenter;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.model.LoginModel;
import com.teaching.upbringing.modular.user.UpdatePwdContract;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author bb
 * @time 2019/11/4 15:08
 * @des ${TODO}
 **/
public class UpdatePwdPresenter extends Presenter<UpdatePwdContract.IView> implements UpdatePwdContract.IPresenter{

    private LoginModel loginModel;

    public UpdatePwdPresenter(UpdatePwdContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        loginModel = new LoginModel();
    }

    @Override
    public void verification(String phone) {

    }

    @Override
    public void updatePwd(String phone, String oldPassword, String newPassword) {
        getView().showProgress();
        loginModel.setPwd(oldPassword, newPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<UserInfoEntity>() {
                    @Override
                    public void onNext(UserInfoEntity userInfoEntity) {
                        getView().updatePwd();
                    }
                });
    }

}
