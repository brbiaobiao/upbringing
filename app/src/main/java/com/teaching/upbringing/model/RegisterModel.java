package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;

import com.teaching.upbringing.api.RegisterApi;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.manager.ApiManager;
import com.teaching.upbringing.utils.RequestBodyParam;

import java.util.HashMap;
import java.util.Map;

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

    public Observable<CaptchaEntity> signInCaptcha(String phone){
        return getApi().signInCaptcha(phone).compose(RxUtil.httpAsyn());
    }

    public Observable<CaptchaEntity> signIn(String captcha,String invitation, String phone){
      //  RequestBodyParam param=new RequestBodyParam();
        Map<String,String> map = new HashMap<>();
        map.put("captcha",captcha);
        map.put("invitation",invitation);
        map.put("phone",phone);

//        param.addParam("captcha",captcha);
//        param.addParam("invitation",invitation);
//        param.addParam("phone",phone);
        return getApi().signIn(map).compose(RxUtil.httpAsyn());
    }

}
