package com.teaching.upbringing.model;

import io.reactivex.Completable;

/**
 * @author: biao
 * @create: 2019/4/9
 * @Describe:
 */
public class ApiModel<Api> {

    private Api mApi;

    public Api getApi() {
        return mApi;
    }

    public ApiModel(Api api) {
        mApi = api;
    }


}
