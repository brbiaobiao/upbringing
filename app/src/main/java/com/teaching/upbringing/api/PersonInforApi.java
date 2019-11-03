package com.teaching.upbringing.api;

import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.PersonInforEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author bb
 * @time 2019/11/1 16:39
 * @des ${TODO}
 **/
public interface PersonInforApi {

    @GET("user/getUserInfo")
    Observable<RxHttpResponse<PersonInforEntity>> getUserInfo();

    @FormUrlEncoded
    @POST("user/setNickname")
    Observable<RxHttpResponse<String>> setNickName(@Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("user/setIntroduce")
    Observable<RxHttpResponse<String>> setIntroduce(@Field("introduce") String introduce);

    @FormUrlEncoded
    @POST("user/setTitle")
    Observable<RxHttpResponse<String>> setTitle(@Field("title") String title);

    @FormUrlEncoded
    @POST("user/setBrightSpot")
    Observable<RxHttpResponse<String>> setBrightSpot(@Field("brightSpot") String brightSpot);

    @FormUrlEncoded
    @POST("user/setSex")
    Observable<RxHttpResponse<String>> setSex(@Field("sex") int sex);
}
