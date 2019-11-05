package com.teaching.upbringing.modular.user;


import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;


public interface LoginContract {

    interface IView extends IContextView, IProgressAble {
        void verification(CaptchaEntity entity);

        void login(UserInfoEntity entity);

        void isFirstOpenApp(boolean flag);
    }

    interface IPresenter extends IBasePresenter<LoginContract.IView> {
        abstract void verification(String phone);

        abstract void login(String captcha, String phone);
    }


}
