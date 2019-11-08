package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.AddressApi;
import com.teaching.upbringing.entity.CommonAddEntity;
import com.teaching.upbringing.manager.ApiManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author bb
 * @time 2019/11/7 11:40
 * @des ${地址相关数据model}
 **/
public class AddressModel extends ApiModel<AddressApi> {
    public AddressModel() {
        super(ApiManager.addressApi());
    }

    public Observable<List<CommonAddEntity>> getAddressList(){
        return getApi().getAddressList().compose(RxUtil.httpAsyn());
    }

    public Observable<List<CommonAddEntity>> deleteAddress(String id){
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        return getApi().deleteAddress(map).compose(RxUtil.httpAsyn());
    }

    public Observable<CommonAddEntity> addAddress(Map<String,Object> map){
        return getApi().addAddress(map).compose(RxUtil.httpAsyn());
    }
}
