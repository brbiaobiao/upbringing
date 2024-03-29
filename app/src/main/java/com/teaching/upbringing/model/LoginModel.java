package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
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
public class LoginModel extends ApiModel<LoginApi> {

    public LoginModel() {
        super(ApiManager.loginApi());
    }

    public Observable<CaptchaEntity> loginCaptcha(String phone){
//        Map<String,String> map = new HashMap<>();
//        map.put("phone",phone);
        return getApi().loginCaptcha(phone).compose(RxUtil.httpAsyn());
    }

    public Observable<UserInfoEntity> captchaLogin(String captcha, String phone){
        Map<String,String> map = new HashMap<>();
        map.put("captcha",captcha);
        map.put("phone",phone);
        return getApi().captchaLogin(map).compose(RxUtil.httpAsyn());
    }

    /**
     * 修改密码
     * @param
     * @param newPassword
     * @return
     */
    public Observable<UserInfoEntity> setPwd(String captcha, String newPassword,String phone){
        Map<String,String> map = new HashMap<>();
        map.put("captcha",captcha);
        map.put("newPassword",newPassword);
        map.put("phone",phone);
        return getApi().setPwd(map).compose(RxUtil.httpAsyn());
    }

    public Observable<CaptchaEntity> updatePwdCaptcha(String phone) {
//        Map<String,String> map = new HashMap<>();
//        map.put("phone",phone);
        return getApi().updatePwdCaptcha(phone).compose(RxUtil.httpAsyn());
    }
}
