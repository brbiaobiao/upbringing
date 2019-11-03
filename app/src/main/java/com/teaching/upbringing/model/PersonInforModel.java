package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.PersonInforApi;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.manager.ApiManager;

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
        return getApi().setNickName(nickname).compose(RxUtil.httpAsyn());
    }

    public Observable<String> setIntroduce(String introduce) {
        return getApi().setIntroduce(introduce).compose(RxUtil.httpAsyn());
    }

    public Observable<String> setTitle(String title) {
        return getApi().setTitle(title).compose(RxUtil.httpAsyn());
    }

    public Observable<String> setBrightSpot(String brightSpot) {
        return getApi().setBrightSpot(brightSpot).compose(RxUtil.httpAsyn());
    }

    public Observable<String> setSex(int sex) {
        return getApi().setSex(sex).compose(RxUtil.httpAsyn());
    }
}
