package com.teaching.upbringing.modular.address;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CommonAddEntity;
import com.teaching.upbringing.model.AddressModel;
import com.teaching.upbringing.presenter.Presenter;
import com.teaching.upbringing.utils.StringUtils;
import com.teaching.upbringing.utils.ToastUtil;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author bb
 * @time 2019/11/7 16:40
 * @des ${TODO}
 **/
public class AddAddressPresenter extends Presenter<AddAddressContract.IView> implements AddAddressContract.IPresenter {

    private AddressModel addressModel;

    public AddAddressPresenter(AddAddressContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        addressModel = new AddressModel();
    }

    @Override
    public void addOrUpdataAddress(boolean isNew,Map<String, Object> map) {
        if(StringUtils.isEmpty(getView().getLoaction())) {
            ToastUtil.showFault("请选择地址");
            return;
        }
        if(StringUtils.isEmpty(getView().getHouseName())) {
            ToastUtil.showFault("请填写门牌号");
            return;
        }
        getView().showProgress();
        if(isNew) {
            addressModel.addAddress(map)
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(bindLife())
                    .doOnError(throwable -> getView().hideProgress())
                    .subscribe(new NextObserver<CommonAddEntity>() {
                        @Override
                        public void onNext(CommonAddEntity commonAddEntity) {
                            getView().hideProgress();
                            getView().afterAdd();
                        }
                    });
        }else {
            addressModel.updateAddress(map)
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(bindLife())
                    .doOnError(throwable -> getView().hideProgress())
                    .subscribe(new NextObserver<CommonAddEntity>() {
                        @Override
                        public void onNext(CommonAddEntity commonAddEntity) {
                            getView().hideProgress();
                            getView().afterAdd();
                        }
                    });
        }

    }
}
