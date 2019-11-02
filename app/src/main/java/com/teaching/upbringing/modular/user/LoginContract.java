package com.teaching.upbringing.modular.user;



import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;


public interface LoginContract {

     interface IView extends IContextView{
        void verification(CaptchaEntity entity);
         void login(UserInfoEntity entity);
    }

    interface IPresenter extends IBasePresenter<LoginContract.IView> {
        abstract void verification(String phone);
        abstract void login(String captcha,String phone);
    }


}
