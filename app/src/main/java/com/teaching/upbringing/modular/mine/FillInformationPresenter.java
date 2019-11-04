package com.teaching.upbringing.modular.mine;

import android.content.Intent;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.model.PersonInforModel;
import com.teaching.upbringing.presenter.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class FillInformationPresenter extends Presenter<FillInformationContract.IView>
        implements FillInformationContract.Ipresenter {

    private PersonInforModel personInforModel;
    private int type;

    public FillInformationPresenter(FillInformationContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        personInforModel = new PersonInforModel();
    }

    @Override
    public void getIntent(Intent intent) {
        type = intent.getIntExtra(FillInformationActivity.UPDATATYPE, -1);
    }

    @Override
    public void upDataInfor(String info) {
        if (type == -1) return;
        getView().showProgress();
        switch (type) {
            case 1:
                personInforModel.setNickName(info)
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(bindLife())
                        .doOnError(throwable -> getView().hideProgress())
                        .subscribe(new NextObserver<String>() {
                            @Override
                            public void onNext(String string) {
                                getView().hideProgress();
                                getView().upDataCallBack();
                            }
                        });
                break;
            case 2:
                personInforModel.setIntroduce(info)
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(bindLife())
                        .doOnError(throwable -> getView().hideProgress())
                        .subscribe(new NextObserver<String>() {
                            @Override
                            public void onNext(String aBoolean) {
                                getView().hideProgress();
                                getView().upDataCallBack();
                            }
                        });
                break;
            case 3:
                personInforModel.setTitle(info)
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(bindLife())
                        .doOnError(throwable -> getView().hideProgress())
                        .subscribe(new NextObserver<String>() {
                            @Override
                            public void onNext(String aBoolean) {
                                getView().hideProgress();
                                getView().upDataCallBack();
                            }
                        });
                break;
            case 4:
                personInforModel.setBrightSpot(info)
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(bindLife())
                        .doOnError(throwable -> getView().hideProgress())
                        .subscribe(new NextObserver<String>() {
                            @Override
                            public void onNext(String aBoolean) {
                                getView().hideProgress();
                                getView().upDataCallBack();
                            }
                        });
                break;

        }
    }
}
