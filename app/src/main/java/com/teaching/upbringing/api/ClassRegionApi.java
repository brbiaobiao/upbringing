package com.teaching.upbringing.api;

import com.outsourcing.library.net.RxHttpResponse;
import com.teaching.upbringing.entity.ClassAreaEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author bb
 * @time 2019/11/29 18:05
 * @des ${TODO}
 **/
public interface ClassRegionApi {

    /**
     * 获取上课区域列表
     * @return
     */
    @GET("classRegion/list")
    Observable<RxHttpResponse<List<ClassAreaEntity>>> getClassList();

    /**
     * 新增上课区域
     * @param param
     * @return
     */
    @POST("classRegion/create")
    Observable<RxHttpResponse<ClassAreaEntity>> createClassArea(@Body Map<String, Object> param);

    /**
     * 删除上课区域
     * @param param
     * @return
     */
    @POST("classRegion/delete")
    Observable<RxHttpResponse<ClassAreaEntity>> deleteClassArea(@Body Map<String, String> param);
}
