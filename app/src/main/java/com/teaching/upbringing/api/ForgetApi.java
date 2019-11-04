package com.teaching.upbringing.api;


import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.CaptchaEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public interface ForgetApi {



    @POST("login/forgetPwd")
    Observable<RxHttpResponse<CaptchaEntity>> forgetPwd(@Body Map<String, String> param);

    @POST("login/forgetPwdCaptcha")
    Observable<RxHttpResponse<CaptchaEntity>> forgetPwdCaptcha(@Body Map<String, String> param);
}
