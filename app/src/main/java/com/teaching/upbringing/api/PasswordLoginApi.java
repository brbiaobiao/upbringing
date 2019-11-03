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
public interface PasswordLoginApi {



    @POST("login/pwdLogin")
    Observable<RxHttpResponse<UserInfoEntity>> passwordLogin(@Body Map<String, String> param);
}
