package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.PersonInforApi;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.manager.ApiManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author bb
 * @time 2019/11/1 16:40
 * @des ${TODO}
 **/
public class PersonInforModel extends ApiModel<PersonInforApi> {
    public PersonInforModel() {
        super(ApiManager.personInforApi());
    }

    public Observable<PersonInforEntity> getUserInfo() {
        return getApi().getUserInfo().compose(RxUtil.httpAsyn());
    }

    public Observable<String> setNickName(String nickname) {
        Map<String,String> map = new HashMap<>();
        map.put("nickname",nickname);

        return getApi().setNickName(map).compose(RxUtil.httpAsyn());
    }

    public Observable<Boolean> setIntroduce(String introduce) {
        Map<String,String> map = new HashMap<>();
        map.put("introduce",introduce);
        return getApi().setIntroduce(map).compose(RxUtil.httpAsyn());
    }

    public Observable<String> setTitle(String title) {
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        return getApi().setTitle(map).compose(RxUtil.httpAsyn());
    }

    public Observable<String> setBrightSpot(String brightSpot) {
        Map<String,String> map = new HashMap<>();
        map.put("brightSpot",brightSpot);
        return getApi().setBrightSpot(map).compose(RxUtil.httpAsyn());
    }

    public Observable<String> setSex(int sex) {
        Map<String,String> map = new HashMap<>();
        map.put("sex",sex+"");
        return getApi().setSex(map).compose(RxUtil.httpAsyn());
    }
}
