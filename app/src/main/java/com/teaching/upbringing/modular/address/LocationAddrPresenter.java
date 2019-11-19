package com.teaching.upbringing.modular.address;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.AllCityEntity;
import com.teaching.upbringing.entity.CityLevelEntity;
import com.teaching.upbringing.entity.ListAllRegionByNameEntity;
import com.teaching.upbringing.entity.ListAllRegionEntity;
import com.teaching.upbringing.entity.RegionByCityNameEntity;
import com.teaching.upbringing.model.RegionModel;
import com.teaching.upbringing.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author ChenHh
 * @time 2019/11/9 23:50
 * @des
 **/
public class LocationAddrPresenter extends Presenter<LocationAddrContract.IView> implements LocationAddrContract.IPresenter {

    private RegionModel regionModel;
    private List<AllCityEntity> allCityEntities;
    private List<CityLevelEntity> cityLevelEntityList = new ArrayList<>();
    private List<CityLevelEntity.CityBean> cityBeanList = new ArrayList<>();
    private List<CityLevelEntity.CityBean.AreaBean> areaBeanList = new ArrayList<>();

    public LocationAddrPresenter(LocationAddrContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        regionModel = new RegionModel();
    }

    /**
     * 获取所有城市列表
     */
    @Override
    public void getListAllRegion() {
        getView().showProgress();
        regionModel.listAllRegionOrderByName()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<List<RegionByCityNameEntity>>() {
                    @Override
                    public void onNext(List<RegionByCityNameEntity> regionByCityNameEntities) {
                        getView().hideProgress();
                        allCityEntities = new ArrayList<>();
                        for(int i = 0; i < regionByCityNameEntities.size(); i++) {
                            RegionByCityNameEntity regionByCityNameEntity = regionByCityNameEntities.get(i);
                            String letter = regionByCityNameEntity.getLetter();
                            List<RegionByCityNameEntity.RegionRspsBean> regionRsps = regionByCityNameEntity.getRegionRsps();
                            for(int j = 0; j < regionRsps.size(); j++) {
                                AllCityEntity allCityEntity = new AllCityEntity();
                                allCityEntity.setAdCode(regionRsps.get(j).getAdCode());
                                allCityEntity.setDistrictLevel(regionRsps.get(j).getDistrictLevel());
                                allCityEntity.setId(regionRsps.get(j).getId());
                                allCityEntity.setName(regionRsps.get(j).getName());
                                allCityEntity.setChar_new(letter);
                                allCityEntities.add(allCityEntity);
                            }
                        }
                        getView().setAllCityAdapter(allCityEntities);
                    }
                });
                getcityTravel1();
    }

    /**
     * 检索key对应城市
     *
     * @param key
     */
    @Override
    public void listAllRegionByNameLike(String key) {
        regionModel.listAllRegionByNameLike(key)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<List<ListAllRegionByNameEntity>>() {
                    @Override
                    public void onNext(List<ListAllRegionByNameEntity> listAllRegionByNameEntities) {
                        getView().setCityAdapter(listAllRegionByNameEntities);
                    }
                });
    }

    @Override
    public void onLetterUpdate(String letter) {
        if (allCityEntities == null)
            return;
        for (int i = 0; i < allCityEntities.size(); i++) {
            if (allCityEntities.get(i).getChar_new().equalsIgnoreCase(letter)) {
                getView().letterUpdate(i - 1);
                getView().setLetterText(letter);
            }
        }
    }

    @Override
    public void listAreaRegionByCityCityName(String cityName) {
        regionModel.listAreaRegionByCityName(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<List<ListAllRegionByNameEntity>>() {
                    @Override
                    public void onNext(List<ListAllRegionByNameEntity> listAllRegionByNameEntities) {
                        getView().setReCityAdapter(listAllRegionByNameEntities);
                    }
                });
    }

    public void getcityTravel1() {
        regionModel.listAllRegion()
                .compose(bindLife())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NextObserver<List<ListAllRegionEntity>>() {
                    @Override
                    public void onNext(List<ListAllRegionEntity> listAllRegionEntityList) {
                        for (int i = 0; i < listAllRegionEntityList.size(); i++) {
                            CityLevelEntity cityLevelEntity = new CityLevelEntity();
                            List<ListAllRegionEntity.RegionRspsBean> regionRsps = listAllRegionEntityList.get(i).getRegionRsps();
                            for(int i1 = 0; i1 < regionRsps.size(); i1++) {
                                String id = regionRsps.get(i1).getId();
                                String name = regionRsps.get(i1).getName();
                                cityLevelEntity.setProvince_id(id);
                                cityLevelEntity.setProvince_name(name);
                                /*//请求第二级
                                regionModel.listSubRegion(id)
                                        .compose(bindLife())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new NextObserver<List<ListSubRegionEntity>>() {
                                            @Override
                                            public void onNext(List<ListSubRegionEntity> listSubRegionEntities) {
                                                for(int i = 0; i < listSubRegionEntities.size(); i++) {
                                                    String id = listSubRegionEntities.get(i).getId();
                                                    String name = listSubRegionEntities.get(i).getName();
                                                    CityLevelEntity.CityBean cityBean = new CityLevelEntity.CityBean();
                                                    cityBean.setCity_id(id);
                                                    cityBean.setCity_name(name);
                                                    //请求第三级
                                                    regionModel.listSubRegion(id)
                                                            .compose(bindLife())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(new NextObserver<List<ListSubRegionEntity>>() {
                                                                @Override
                                                                public void onNext(List<ListSubRegionEntity> listSubRegionEntities) {
                                                                    for(int i = 0; i < listSubRegionEntities.size(); i++) {
                                                                        String id = listSubRegionEntities.get(i).getId();
                                                                        String name1 = listSubRegionEntities.get(i).getName();
                                                                        CityLevelEntity.CityBean.AreaBean areaBean = new CityLevelEntity.CityBean.AreaBean();
                                                                        areaBean.setArea_id(id);
                                                                        areaBean.setArea_name(name1);
                                                                        areaBeanList.add(areaBean);
                                                                    }
                                                                    cityBean.setArea(areaBeanList);
                                                                }
                                                            });
                                                    cityBeanList.add(cityBean);
                                                }
                                                cityLevelEntity.setCity(cityBeanList);
                                            }
                                        });
                                cityLevelEntityList.add(cityLevelEntity);*/
                            }

                        }
                    }

                        /*String cityLevelEntityListString = GsonUtil.getIntance().toJson(cityLevelEntityList);
                        Log.d("biao_city",cityLevelEntityListString);*/
                });
    }
}
