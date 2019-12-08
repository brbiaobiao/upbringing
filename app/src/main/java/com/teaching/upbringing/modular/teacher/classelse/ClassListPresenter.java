package com.teaching.upbringing.modular.teacher.classelse;

import com.teaching.upbringing.presenter.Presenter;

/**
 * @author bb
 * @time 2019/11/4 11:59
 * @des ${TODO}
 **/
public class ClassListPresenter extends Presenter<ClassListContract.IView> implements ClassListContract.Ipresenter {

    public ClassListPresenter(ClassListContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {

    }
}
