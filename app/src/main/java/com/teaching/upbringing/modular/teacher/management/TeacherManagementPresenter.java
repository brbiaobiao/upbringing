package com.teaching.upbringing.modular.teacher.management;

import com.teaching.upbringing.presenter.Presenter;

/**
 * @author ChenHh
 * @time 2019/12/8 1:39
 * @des
 **/
public class TeacherManagementPresenter extends Presenter<TeacherManagementContract.IView>
        implements TeacherManagementContract.Ipresenter {
    public TeacherManagementPresenter(TeacherManagementContract.IView view) {
        super(view);
    }

    @Override
    protected void init() {

    }
}
