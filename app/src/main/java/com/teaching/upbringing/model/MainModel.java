package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.MainApi;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.manager.ApiManager;

import io.reactivex.Observable;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public class MainModel extends ApiModel<MainApi> {

    public MainModel() {
        super(ApiManager.mainApi());
    }

    public Observable<TestEntity> getTestData(){
        return getApi().getTestData().compose(RxUtil.httpAsyn());
    }

}
