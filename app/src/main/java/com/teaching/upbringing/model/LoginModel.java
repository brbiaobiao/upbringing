package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.LoginApi;
import com.teaching.upbringing.api.MainApi;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.manager.ApiManager;

import java.util.HashMap;
import java.util.Map;

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

    public Observable<CaptchaEntity> loginCaptcha(){
        return getApi().loginCaptcha().compose(RxUtil.httpAsyn());
    }

    public Observable<CaptchaEntity> captchaLogin(String captcha,String phone){
        Map<String,String> map = new HashMap<>();
        map.put("captcha",captcha);
        map.put("phone",phone);
        return getApi().captchaLogin(map).compose(RxUtil.httpAsyn());
    }

}
