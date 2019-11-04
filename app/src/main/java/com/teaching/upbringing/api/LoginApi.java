package com.teaching.upbringing.api;


import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public interface LoginApi {

    @GET("login/loginCaptcha")
    Observable<RxHttpResponse<CaptchaEntity>> loginCaptcha();

    @POST("login/captchaLogin")
    Observable<RxHttpResponse<UserInfoEntity>> captchaLogin(@Body Map<String, String> param);

    /**
     * 修改密码
     * @param param
     * @return
     */
    @POST("login/setPwd")
    Observable<RxHttpResponse<UserInfoEntity>> setPwd(@Body Map<String, String> param);
}
