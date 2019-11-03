package com.teaching.upbringing.modular.mine;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.model.PersonInforModel;
import com.teaching.upbringing.presenter.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class EditPersonInfoPresenter extends Presenter<EditPersonlInfoContract.IView>
        implements EditPersonlInfoContract.Ipresenter {

    private PersonInforModel personInforModel;

    public EditPersonInfoPresenter(EditPersonlInfoContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        personInforModel = new PersonInforModel();
    }

    @Override
    public void getInfo() {
        getView().showProgress();
        personInforModel.getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<PersonInforEntity>() {
                    @Override
                    public void onNext(PersonInforEntity personInforEntity) {
                        getView().hideProgress();
                        getView().setInfor(personInforEntity);
                    }
                });
    }

    @Override
    public void setSex(int sex) {
        getView().showProgress();
        personInforModel.setSex(sex)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<String>() {
                    @Override
                    public void onNext(String aBoolean) {
                        getView().hideProgress();
                        getInfo();
                    }
                });
    }
}
