package com.teaching.upbringing.modular.setting;

import com.teaching.upbringing.presenter.Presenter;

/**
 * @author bb
 * @time 2019/10/29 10:35
 * @des ${TODO}
 **/
public class SettingPresenter extends Presenter<SettingContract.IView> implements SettingContract.IPresenter {

    public SettingPresenter(SettingContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {

    }

    @Override
    public void loginOut() {

    }
}
