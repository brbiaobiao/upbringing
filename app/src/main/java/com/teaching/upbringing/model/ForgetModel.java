package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.ForgetApi;
import com.teaching.upbringing.api.LoginApi;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.manager.ApiManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public class ForgetModel extends ApiModel<ForgetApi> {

    public ForgetModel() {
        super(ApiManager.forgetApi());
    }


    public Observable<CaptchaEntity> forgetPwd(String captcha, String password,String phone){
        Map<String,String> map = new HashMap<>();
        map.put("captcha",captcha);
        map.put("password",password);
        map.put("phone",phone);
        return getApi().forgetPwd(map).compose(RxUtil.httpAsyn());
    }

}
