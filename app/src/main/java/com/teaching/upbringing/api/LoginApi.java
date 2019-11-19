package com.teaching.upbringing.api;


import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public interface LoginApi {

    @GET("login/loginCaptcha")
    Observable<RxHttpResponse<CaptchaEntity>> loginCaptcha(@Query("phone") String phone);

    @POST("login/captchaLogin")
    Observable<RxHttpResponse<UserInfoEntity>> captchaLogin(@Body Map<String, String> param);

    /**
     * 修改密码
     * @param param
     * @return
     */
    @POST("login/setPwd")
    Observable<RxHttpResponse<UserInfoEntity>> setPwd(@Body Map<String, String> param);

    @GET("login/updatePwdCaptcha")
    Observable<RxHttpResponse<CaptchaEntity>> updatePwdCaptcha(@Query("phone") String phone);
}
