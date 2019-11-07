package com.teaching.upbringing.modular.address;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CommonAddEntity;
import com.teaching.upbringing.model.AddressModel;
import com.teaching.upbringing.presenter.Presenter;

import java.util.List;
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
    public void addAddress(Map<String, Object> map) {
        addressModel.addAddress(map)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<List<CommonAddEntity>>() {
                    @Override
                    public void onNext(List<CommonAddEntity> commonAddEntities) {

                    }
                });
    }
}
