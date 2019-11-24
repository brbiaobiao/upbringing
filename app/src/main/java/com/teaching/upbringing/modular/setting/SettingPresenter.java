package com.teaching.upbringing.modular.setting;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.model.PersonInforModel;
import com.teaching.upbringing.model.SettingModel;
import com.teaching.upbringing.presenter.Presenter;
import com.teaching.upbringing.utils.ToastUtil;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author bb
 * @time 2019/10/29 10:35
 * @des ${TODO}
 **/
public class SettingPresenter extends Presenter<SettingContract.IView> implements SettingContract.IPresenter {

    private SettingModel mMainModels;
    private PersonInforModel personInforModel;

    public SettingPresenter(SettingContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {
        mMainModels = new SettingModel();
        personInforModel = new PersonInforModel();
    }

    @Override
    public void loginOut() {
        getView().showProgress();
        mMainModels.loginOut()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindLife())
                .doOnError(throwable -> getView().hideProgress())
                .subscribe(new NextObserver<CaptchaEntity>() {
                    @Override
                    public void onNext(CaptchaEntity testEntity) {
                        getView().loginOut();
                    }
                });
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
                        ToastUtil.showSucceed("修改成功");
                    }
                });
    }
}
