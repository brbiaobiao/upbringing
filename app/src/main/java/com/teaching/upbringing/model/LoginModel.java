package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.LoginApi;
import com.teaching.upbringing.api.MainApi;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.manager.ApiManager;

import io.reactivex.Observable;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public class LoginModel extends ApiModel<LoginApi> {

    public LoginModel() {
        super(ApiManager.loginApi());
    }

    public Observable<TestEntity> loginCaptcha(){
        return getApi().loginCaptcha().compose(RxUtil.httpAsyn());
    }

    public Observable<TestEntity> captchaLogin(){
        return getApi().captchaLogin().compose(RxUtil.httpAsyn());
    }

}
