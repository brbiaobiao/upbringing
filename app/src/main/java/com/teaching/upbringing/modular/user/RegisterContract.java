package com.teaching.upbringing.modular.user;

import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.modular.webview.WebViewContract;

public interface RegisterContract {

    interface IView extends IContextView{
        void signInCaptcha(CaptchaEntity entity);
        void signIn(TestEntity entity);
    }

    interface IPresenter extends IBasePresenter<RegisterContract.IView> {
        abstract void signInCaptcha(String phone);
        abstract void signIn(String captcha,String phone);
    }

}
