package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.ClassRegionApi;
import com.teaching.upbringing.entity.ClassAreaEntity;
import com.teaching.upbringing.manager.ApiManager;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author ChenHh
 * @time 2019/11/30 17:36
 * @des
 **/
public class ClassRegionModel extends ApiModel<ClassRegionApi> {
    public ClassRegionModel() {
        super(ApiManager.classRegionApi());
    }

    public Observable<List<ClassAreaEntity>> getClassList(){
        return getApi().getClassList().compose(RxUtil.httpAsyn());
    }

    public Observable<ClassAreaEntity> createClassArea(Map<String,Object> map){
        return getApi().createClassArea(map).compose(RxUtil.httpAsyn());
    }

    public Observable<ClassAreaEntity> deleteClassArea(Map<String,String> map){
        return getApi().deleteClassArea(map).compose(RxUtil.httpAsyn());
    }
}
