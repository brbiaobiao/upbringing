package com.teaching.upbringing.modular.user;



import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;

import com.teaching.upbringing.entity.TestEntity;


public interface LoginContract {

     interface IView extends IContextView{
        void verification(TestEntity entity);
         void login(TestEntity entity);
    }

    interface IPresenter extends IBasePresenter<LoginContract.IView> {
        abstract void verification(String phone);
        abstract void login(String captcha,String phone);
    }


}
