package com.teaching.upbringing.modular.address;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.ListAllRegionByNameEntity;
import com.teaching.upbringing.model.RegionModel;
import com.teaching.upbringing.presenter.Presenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author ChenHh
 * @time 2019/11/10 5:59
 * @des
 **/
public class ReLocationAddrPresenter extends Presenter<ReLocationAddrContract.IView> implements ReLocationAddrContract.IPresenter {
    private RegionModel regionModel;
    public ReLocationAddrPresenter(ReLocationAddrContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        regionModel = new RegionModel();
    }

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
    public void listAreaRegionByCityCityName(String cityName) {
        getView().showProgress();
        regionModel.listAreaRegionByCityName(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<List<ListAllRegionByNameEntity>>() {
                    @Override
                    public void onNext(List<ListAllRegionByNameEntity> listAllRegionByNameEntities) {
                        getView().hideProgress();
                        getView().setReCityAdapter(listAllRegionByNameEntities);
                    }
                });
    }
}
