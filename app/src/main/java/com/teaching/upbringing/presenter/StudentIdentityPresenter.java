package com.teaching.upbringing.presenter;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.model.PersonInforModel;
import com.teaching.upbringing.modular.mine.StudentIdentityContract;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class StudentIdentityPresenter  extends Presenter<StudentIdentityContract.IView>
        implements StudentIdentityContract.Ipresenter {

    private PersonInforModel personInforModel;

    public StudentIdentityPresenter(StudentIdentityContract.IView view) {
        super(view);
    }
    @Override
    public void setApplyStudent(String name, int sex) {
        getView().showProgress();
        personInforModel.applyStudent(name,sex)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<UserInfoEntity>() {
                    @Override
                    public void onNext(UserInfoEntity userInfoEntity) {
                        getView().hideProgress();
                        getView().getApplyStudent(userInfoEntity);
                    }
                });
    }

    @Override
    public void gitIdentityAuth(int id) {

    }


    @Override
    protected void init() {
        personInforModel = new PersonInforModel();
    }
}
