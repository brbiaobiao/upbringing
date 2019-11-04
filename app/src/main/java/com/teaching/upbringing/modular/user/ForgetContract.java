package com.teaching.upbringing.modular.user;



import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.CaptchaEntity;


public interface ForgetContract {

     interface IView extends IContextView, IProgressAble {

         void startTimecount();
         void forgetPwd(CaptchaEntity entity);
    }

    interface IPresenter extends IBasePresenter<ForgetContract.IView> {
         void forgetPwdCaptcha(String phone);

         void forgetPwd(String captcha, String password,String phone);
    }
}
