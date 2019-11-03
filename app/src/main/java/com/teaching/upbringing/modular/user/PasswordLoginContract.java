package com.teaching.upbringing.modular.user;



import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;


public interface PasswordLoginContract {

     interface IView extends IContextView, IProgressAble {
         void passwordLogin(UserInfoEntity entity);
    }

    interface IPresenter extends IBasePresenter<PasswordLoginContract.IView> {
        abstract void passwordLogin(String phone, String pwd);
    }


}
