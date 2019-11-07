package com.teaching.upbringing.modular.address;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CommonAddEntity;
import com.teaching.upbringing.model.AddressModel;
import com.teaching.upbringing.presenter.Presenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author bb
 * @time 2019/11/7 11:19
 * @des ${TODO}
 **/
public class CommonAddPresenter extends Presenter<CommonAddContract.IView> implements CommonAddContract.IPresenter {

    private AddressModel addressModel;
    private List<CommonAddEntity> addEntities;

    public CommonAddPresenter(CommonAddContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        addressModel = new AddressModel();
    }

    @Override
    public void getAddressList() {
        addressModel.getAddressList()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<List<CommonAddEntity>>() {
                    @Override
                    public void onNext(List<CommonAddEntity> commonAddEntities) {
                        addEntities = commonAddEntities;
                        commonAddEntities.add(new CommonAddEntity("就在海的那边山的那边啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦", true,"广州第一人民医院"));
                        commonAddEntities.add(new CommonAddEntity("就是那个公园对对对对对对对对对对对对对都丢地对对对", false,"沙美公园"));
                        commonAddEntities.add(new CommonAddEntity("hhhahha", false,"hhhahhaha"));
                        getView().setAdapter(commonAddEntities);
                        /*if(commonAddEntities.size() == 0) {
                            getView().setEmptyView(true);
                        }else {
                            getView().setEmptyView(false);
                        }*/
                        getView().refreshFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        getView().refreshFinish();
                    }
                });
    }

    @Override
    public void deleteAddress(int position) {
        getView().showProgress();
        addressModel.deleteAddress(addEntities.get(position).getId()+"")
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<List<CommonAddEntity>>() {
                    @Override
                    public void onNext(List<CommonAddEntity> commonAddEntities) {
                        getView().removeData(position);
                    }
                });
    }
}
