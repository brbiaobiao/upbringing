package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.RegionApi;
import com.teaching.upbringing.entity.ListAllRegionByNameEntity;
import com.teaching.upbringing.entity.ListAllRegionEntity;
import com.teaching.upbringing.entity.ListSubRegionEntity;
import com.teaching.upbringing.entity.RegionByCityNameEntity;
import com.teaching.upbringing.manager.ApiManager;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author bb
 * @time 2019/11/7 11:40
 * @des ${地址相关数据model}
 **/
public class RegionModel extends ApiModel<RegionApi> {
    public RegionModel() {
        super(ApiManager.regionApi());
    }

    public Observable<List<ListAllRegionEntity>> listAllRegion(){
        return getApi().listAllRegion().compose(RxUtil.httpAsyn());
    }

    public Observable<List<ListSubRegionEntity>> listSubRegion(String parentId){
        return getApi().listSubRegion(parentId).compose(RxUtil.httpAsyn());
    }


    /**
     * 模糊搜索市区列表
     * @param key
     * @return
     */
    public Observable<List<ListAllRegionByNameEntity>> listAllRegionByNameLike(String key){
//        Map<String,String> map = new HashMap<>();
//        map.put("string",key);
        return getApi().listAllRegionByNameLike(key).compose(RxUtil.httpAsyn());
    }

    /**
     * 获取所有城市列表（名称首字母排序）
     * @return
     */
    public Observable<List<RegionByCityNameEntity>> listAllRegionOrderByName(){
        return getApi().listAllRegionOrderByName().compose(RxUtil.httpAsyn());
    }

    /**
     * 通过adCode获取区县列表
     * @param cityName 城市名称
     * @return
     */
    public Observable<List<ListAllRegionByNameEntity>> listAreaRegionByCityName(String cityName){
        return getApi().listAreaRegionByCityName(cityName).compose(RxUtil.httpAsyn());
    }
}
