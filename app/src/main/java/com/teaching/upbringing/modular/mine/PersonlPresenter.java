package com.teaching.upbringing.modular.mine;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.model.PersonInforModel;
import com.teaching.upbringing.presenter.Presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author bb
 * @time 2019/11/4 11:59
 * @des ${TODO}
 **/
public class PersonlPresenter extends Presenter<PersonlContract.IView> implements PersonlContract.Ipresenter {

    private PersonInforModel personInforModel;

    public PersonlPresenter(PersonlContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        personInforModel = new PersonInforModel();
    }

    @Override
    public void initData() {
        getView().showProgress();
        personInforModel.getUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<PersonInforEntity>() {
                    @Override
                    public void onNext(PersonInforEntity personInforEntity) {
                        getView().hideProgress();
                        getView().setInfo(personInforEntity);
                    }
                });
    }
}
