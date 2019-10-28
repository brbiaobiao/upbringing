package com.teaching.upbringing.modular.study;

import com.teaching.upbringing.R;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;

/**
 * @author ChenHh
 * @time 2019/10/27 16:03
 * @des
 **/
public class StudyFragment extends BaseMVPFragment<StudyContract.IPresenter> implements StudyContract.IView{

    @Override
    protected Integer getContentId() {
        return R.layout.fragment_study;
    }

    @Override
    protected void init() {
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
