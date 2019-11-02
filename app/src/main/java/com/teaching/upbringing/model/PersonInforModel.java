package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.PersonInforApi;
import com.teaching.upbringing.entity.PersonInforEntity;

import io.reactivex.Observable;

/**
 * @author bb
 * @time 2019/11/1 16:40
 * @des ${TODO}
 **/
public class PersonInforModel extends ApiModel<PersonInforApi> {
    public PersonInforModel() {
        super(null);
    }

    public Observable<PersonInforEntity> getUserInfo() {
        return getApi().getUserInfo().compose(RxUtil.httpAsyn());
    }

    public Observable<Boolean> setNickName(String nickname) {
        return getApi().setNickName(nickname).compose(RxUtil.httpAsyn());
    }

    public Observable<Boolean> setIntroduce(String introduce) {
        return getApi().setIntroduce(introduce).compose(RxUtil.httpAsyn());
    }

    public Observable<Boolean> setTitle(String title) {
        return getApi().setTitle(title).compose(RxUtil.httpAsyn());
    }

    public Observable<Boolean> setBrightSpot(String brightSpot) {
        return getApi().setBrightSpot(brightSpot).compose(RxUtil.httpAsyn());
    }

    public Observable<Boolean> setSex(int sex) {
        return getApi().setSex(sex).compose(RxUtil.httpAsyn());
    }
}
