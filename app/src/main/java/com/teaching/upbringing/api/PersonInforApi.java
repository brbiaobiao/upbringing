package com.teaching.upbringing.api;

import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.PersonInforEntity;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author bb
 * @time 2019/11/1 16:39
 * @des ${TODO}
 **/
public interface PersonInforApi {

    @FormUrlEncoded
    @POST("user/getUserInfo")
    Observable<RxHttpResponse<PersonInforEntity>> getUserInfo();
}
