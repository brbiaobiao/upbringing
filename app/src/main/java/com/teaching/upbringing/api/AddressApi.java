package com.teaching.upbringing.api;

import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.CommonAddEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author bb
 * @time 2019/11/7 11:28
 * @des ${地址相关api}
 **/
public interface AddressApi {

    @GET("address/list")
    Observable<RxHttpResponse<List<CommonAddEntity>>> getAddressList();

    @POST("address/delete")
    Observable<RxHttpResponse<List<CommonAddEntity>>> deleteAddress(@Body Map<String,String> param);

    @POST("address/add")
    Observable<RxHttpResponse<CommonAddEntity>> addAddress(@Body Map<String,Object> param);


}
