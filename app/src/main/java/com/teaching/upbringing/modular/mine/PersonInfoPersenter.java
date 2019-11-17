package com.teaching.upbringing.modular.mine;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.model.PersonInforModel;
import com.teaching.upbringing.presenter.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author bb
 * @time 2019/11/1 16:24
 * @des ${TODO}
 **/
public class PersonInfoPersenter extends Presenter<PersonInforContract.IView> implements PersonInforContract.Ipresenter{

    private PersonInforModel personInforModel;

    public  PersonInfoPersenter(PersonInforContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        personInforModel = new PersonInforModel();
    }


    @Override
    public void initData(boolean needLoading) {
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
                        getView().setInit(personInforEntity);
                    }
                });
    }
}
