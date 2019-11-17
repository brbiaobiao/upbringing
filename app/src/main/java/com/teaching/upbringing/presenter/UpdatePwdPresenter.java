package com.teaching.upbringing.presenter;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.model.LoginModel;
import com.teaching.upbringing.modular.user.UpdatePwdContract;
import com.teaching.upbringing.utils.PhoneUtil;
import com.teaching.upbringing.utils.ToastUtil;

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
        if(!PhoneUtil.checkPhone(phone)) {
            return;
        }
        getView().showProgress();
        loginModel.updatePwdCaptcha(phone)
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
    public void updatePwd(String captcha, String newPassword,String phone) {
        getView().showProgress();
        loginModel.setPwd(captcha, newPassword,phone)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<UserInfoEntity>() {
                    @Override
                    public void onNext(UserInfoEntity userInfoEntity) {
                        getView().hideProgress();
                        getView().updatePwd(userInfoEntity);
                    }
                });
    }

}
