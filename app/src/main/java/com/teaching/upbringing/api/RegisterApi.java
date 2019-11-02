package com.teaching.upbringing.api;


import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.utils.RequestBodyParam;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public interface RegisterApi {

    @GET("login/signInCaptcha")
    Observable<RxHttpResponse<CaptchaEntity>> signInCaptcha(@Field("phone") String phone);

    @POST("login/signIn")
    Observable<RxHttpResponse<CaptchaEntity>> signIn(@Body Map<String, String> param);
}
