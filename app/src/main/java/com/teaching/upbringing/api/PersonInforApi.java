package com.teaching.upbringing.api;

import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.IdentityAuthEntity;
import com.teaching.upbringing.entity.IdentityAuthStatusEntity;
import com.teaching.upbringing.entity.OssEntity;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.entity.UserInfoEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author bb
 * @time 2019/11/1 16:39
 * @des ${TODO}
 **/
public interface PersonInforApi {

    @GET("user/getUserInfo")
    Observable<RxHttpResponse<PersonInforEntity>> getUserInfo();

    @POST("user/setNickname")
    Observable<RxHttpResponse<UserInfoEntity>> setNickName(@Body Map<String, String> param);


    @POST("user/setIntroduce")
    Observable<RxHttpResponse<UserInfoEntity>> setIntroduce(@Body Map<String, String> param);


    @POST("user/setTitle")
    Observable<RxHttpResponse<UserInfoEntity>> setTitle(@Body Map<String, String> param);

    @POST("user/setBrightSpot")
    Observable<RxHttpResponse<UserInfoEntity>> setBrightSpot(@Body Map<String, String> param);


    @POST("user/setSex")
    Observable<RxHttpResponse<UserInfoEntity>> setSex(@Body Map<String, String> param);

    @POST("user/setHeadImgUrl")
    Observable<RxHttpResponse<UserInfoEntity>> setImgUrl(@Body Map<String, String> param);

    @GET("oss/getCredentials")
    Observable<RxHttpResponse<OssEntity>> setOss(@QueryMap Map<String, String> param);

    @POST("user/setAttendClassArea")
    Observable<RxHttpResponse<UserInfoEntity>> setAttendClassArea(@Body Map<String, Object> param);

    @POST("identityAuth/applyStudent")
    Observable<RxHttpResponse<UserInfoEntity>> applyStudent(@Body Map<String, String> param);

    @GET("identityAuth/gitIdentityAuth")
    Observable<RxHttpResponse<IdentityAuthEntity>> gitIdentityAuth(@QueryMap Map<String, String> param);

    @GET("identityAuth/getIdentityAuthStatus")
    Observable<RxHttpResponse<IdentityAuthStatusEntity>> getIdentityAuthStatus();


}
