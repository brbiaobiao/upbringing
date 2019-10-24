package com.teaching.upbringing.api;


import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.TestEntity;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public interface MainApi {

    @POST("Task.queryPlatFormUsedCar")
    Observable<RxHttpResponse<TestEntity>> getTestData();
}
