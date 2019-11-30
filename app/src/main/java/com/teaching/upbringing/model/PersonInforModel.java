package com.teaching.upbringing.model;

import com.outsourcing.library.utils.RxUtil;
import com.teaching.upbringing.api.PersonInforApi;
import com.teaching.upbringing.entity.OssEntity;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.manager.ApiManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author bb
 * @time 2019/11/1 16:40
 * @des ${TODO}
 **/
public class PersonInforModel extends ApiModel<PersonInforApi> {
    public PersonInforModel() {
        super(ApiManager.personInforApi());
    }

    public Observable<PersonInforEntity> getUserInfo() {
        return getApi().getUserInfo().compose(RxUtil.httpAsyn());
    }

    public Observable<UserInfoEntity> setNickName(String nickname) {
        Map<String,String> map = new HashMap<>();
        map.put("nickname",nickname);
        return getApi().setNickName(map).compose(RxUtil.httpAsyn());
    }

    public Observable<UserInfoEntity> setIntroduce(String introduce) {
        Map<String,String> map = new HashMap<>();
        map.put("introduce",introduce);
        return getApi().setIntroduce(map).compose(RxUtil.httpAsyn());
    }

    public Observable<UserInfoEntity> setTitle(String title) {
        Map<String,String> map = new HashMap<>();
        map.put("title",title);
        return getApi().setTitle(map).compose(RxUtil.httpAsyn());
    }

    public Observable<UserInfoEntity> setBrightSpot(String brightSpot) {
        Map<String,String> map = new HashMap<>();
        map.put("brightSpot",brightSpot);
        return getApi().setBrightSpot(map).compose(RxUtil.httpAsyn());
    }

    public Observable<UserInfoEntity> setSex(int sex) {
        Map<String,String> map = new HashMap<>();
        map.put("sex",sex+"");
        return getApi().setSex(map).compose(RxUtil.httpAsyn());
    }

    public Observable<OssEntity> setOss(int code) {
        Map<String,String> map = new HashMap<>();
        map.put("code",code+"");
        return getApi().setOss(map).compose(RxUtil.httpAsyn());
    }

    public Observable<UserInfoEntity> setImgUrl(String headImgUrl) {
        Map<String,String> map = new HashMap<>();
        map.put("headImgUrl",headImgUrl+"");
        return getApi().setImgUrl(map).compose(RxUtil.httpAsyn());
    }

    public Observable<UserInfoEntity> setAttendClassArea(Map<String,Object> map){
        return getApi().setAttendClassArea(map).compose(RxUtil.httpAsyn());
    }

    public Observable<UserInfoEntity> applyStudent(String name,int sex) {
        Map<String,String> map = new HashMap<>();
        map.put("name",name+"");
        map.put("sex",sex+"");
        return getApi().applyStudent(map).compose(RxUtil.httpAsyn());
    }

    public Observable<UserInfoEntity> gitIdentityAuth(int id) {
        Map<String,String> map = new HashMap<>();
        map.put("id",id+"");
        return getApi().gitIdentityAuth(map).compose(RxUtil.httpAsyn());
    }

}
