package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.RegisterApi;
import com.teaching.upbringing.api.SettingApi;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.manager.ApiManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public class SettingModel extends ApiModel<SettingApi> {

    public SettingModel() {
        super(ApiManager.settingApi());
    }



    public Observable<CaptchaEntity> loginOut(){
        return getApi().loginOut().compose(RxUtil.httpAsyn());
    }

}
