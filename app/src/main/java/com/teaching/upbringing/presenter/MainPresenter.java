package com.teaching.upbringing.presenter;


import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.contact.MainContact;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.model.MainModel;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public class MainPresenter extends Presenter<MainContact.IView> implements MainContact.IPersenter {

    private MainModel mMainModels;
    public MainPresenter(MainContact.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        mMainModels = new MainModel();

    }

    @Override
    public void getTestData() {
        mMainModels.getTestData()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .subscribe(new NextObserver<TestEntity>() {
                    @Override
                    public void onNext(TestEntity testEntity) {
                        getView().setData(testEntity);
                    }
                });
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void onViewInited() {

    }

    @Override
    public void onDestroyView() {

    }
}
