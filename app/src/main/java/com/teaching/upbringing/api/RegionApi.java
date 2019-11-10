package com.teaching.upbringing.api;

import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.ListAllRegionByNameEntity;
import com.teaching.upbringing.entity.ListAllRegionEntity;
import com.teaching.upbringing.entity.RegionByCityNameEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author ChenHh
 * @time 2019/11/9 23:43
 * @des
 **/
public interface RegionApi {

    @GET("region/listAllRegion")
    Observable<RxHttpResponse<ListAllRegionEntity>> listAllRegion();

    @GET("region/listAllRegionByNameLike")
    Observable<RxHttpResponse<List<ListAllRegionByNameEntity>>> listAllRegionByNameLike(@Query("name") String string);


    @GET("region/listAllRegionOrderByName")
    Observable<RxHttpResponse<List<RegionByCityNameEntity>>> listAllRegionOrderByName();

    @GET("region/listAreaRegionByCityName")
    Observable<RxHttpResponse<List<ListAllRegionByNameEntity>>> listAreaRegionByCityName(@Query("cityName") String cityName);
}
