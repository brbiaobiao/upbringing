package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.LoginApi;
import com.teaching.upbringing.api.PasswordLoginApi;
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
public class PasswordLoginModel extends ApiModel<PasswordLoginApi> {

    public PasswordLoginModel() {
        super(ApiManager.passwordLoginApi());
    }


    public Observable<UserInfoEntity> passwordLogin(String phone, String pwd){
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("pwd",pwd);
        return getApi().passwordLogin(map).compose(RxUtil.httpAsyn());
    }

}
