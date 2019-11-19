package com.teaching.upbringing.modular.user;


import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;


public interface UpdatePwdContract {

    interface IView extends IContextView, IProgressAble {
        void updatePwd(UserInfoEntity userInfoEntity);
        void verification(CaptchaEntity captchaEntity);
    }

    interface IPresenter extends IBasePresenter<UpdatePwdContract.IView> {
        abstract void verification(String phone);

        abstract void updatePwd(String captcha, String newPassword,String phone);
    }
}
