package com.teaching.upbringing.modular.setting;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.model.PersonInforModel;
import com.teaching.upbringing.presenter.Presenter;
import com.teaching.upbringing.utils.ToastUtil;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author ChenHh
 * @time 2019/11/26 0:23
 * @des
 **/
public class ClassAreaPresenter extends Presenter<ClassAreaContract.IView> implements ClassAreaContract.IPresenter {

    private PersonInforModel personInforModel;
    public ClassAreaPresenter(ClassAreaContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        personInforModel = new PersonInforModel();
    }

    @Override
    public void setAttendClassArea(Map<String, Object> map) {
        getView().showProgress();
        personInforModel.setAttendClassArea(map)
                .compose(bindLife())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<UserInfoEntity>() {
                    @Override
                    public void onNext(UserInfoEntity userInfoEntity) {
                        getView().hideProgress();
                        getView().addSuccess();
                        ToastUtil.showSucceed("修改成功");
                    }
                });
    }

    @Override
    public void removeClassArea(int position) {
        getView().removeAreaItem(position);
    }
}
