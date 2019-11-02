package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;

import com.teaching.upbringing.api.RegisterApi;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.manager.ApiManager;

import io.reactivex.Observable;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public class RegisterModel extends ApiModel<RegisterApi> {

    public RegisterModel() {
        super(ApiManager.registerApi());
    }

    public Observable<CaptchaEntity> signInCaptcha(){
        return getApi().signInCaptcha().compose(RxUtil.httpAsyn());
    }

    public Observable<TestEntity> signIn(){
        return getApi().signIn().compose(RxUtil.httpAsyn());
    }

}
