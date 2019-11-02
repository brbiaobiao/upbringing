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
    Observable<RxHttpResponse<Boolean>> setNickName(@Field("nickname") String nickname);

    @FormUrlEncoded
    @POST("user/setIntroduce")
    Observable<RxHttpResponse<Boolean>> setIntroduce(@Field("introduce") String introduce);

    @FormUrlEncoded
    @POST("user/setTitle")
    Observable<RxHttpResponse<Boolean>> setTitle(@Field("title") String title);

    @FormUrlEncoded
    @POST("user/setBrightSpot")
    Observable<RxHttpResponse<Boolean>> setBrightSpot(@Field("brightSpot") String brightSpot);

    @FormUrlEncoded
    @POST("user/setSex")
    Observable<RxHttpResponse<Boolean>> setSex(@Field("sex") int sex);
}
