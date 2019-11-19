package com.teaching.upbringing.modular.mine;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.OssEntity;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.model.PersonInforModel;
import com.teaching.upbringing.presenter.Presenter;

import java.io.File;

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
    public void getInfo(boolean needLoading) {
        if(needLoading) {
            getView().showProgress();
        }
        personInforModel.getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
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
        personInforModel.setSex(sex)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<UserInfoEntity>() {
                    @Override
                    public void onNext(UserInfoEntity userInfoEntity) {
                        getInfo(false);
                    }
                });
    }

    @Override
    public void saveUserImg(File file) {

    }

    @Override
    public void setOss(int code) {
        personInforModel.setOss(code)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<OssEntity>() {
                    @Override
                    public void onNext(OssEntity ossEntity) {
                        getView().getOss(ossEntity);
                    }
                });
    }
}
