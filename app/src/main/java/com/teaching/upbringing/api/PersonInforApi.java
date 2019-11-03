package com.teaching.upbringing.api;

import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.PersonInforEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author bb
 * @time 2019/11/1 16:39
 * @des ${TODO}
 **/
public interface PersonInforApi {

    @GET("user/getUserInfo")
    Observable<RxHttpResponse<PersonInforEntity>> getUserInfo();


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/setNickname")
    Observable<RxHttpResponse<String>> setNickName(@Body Map<String, String> param);


    @POST("user/setIntroduce")
    Observable<RxHttpResponse<String>> setIntroduce(@Body Map<String, String> param);


    @POST("user/setTitle")
    Observable<RxHttpResponse<String>> setTitle(@Body Map<String, String> param);

    @POST("user/setBrightSpot")
    Observable<RxHttpResponse<String>> setBrightSpot(@Body Map<String, String> param);


    @POST("user/setSex")
    Observable<RxHttpResponse<String>> setSex(@Body Map<String, String> param);
}
