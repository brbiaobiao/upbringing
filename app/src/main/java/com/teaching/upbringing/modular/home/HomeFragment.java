package com.teaching.upbringing.modular.home;

import com.lefore.tutoring.R;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;

/**
 * @author ChenHh
 * @time 2019/10/27 16:00
 * @des
 **/
public class HomeFragment extends BaseMVPFragment<HomeContract.Ipresenter> implements HomeContract.IView {
    @Override
    protected Integer getContentId() {
        return R.layout.fragment_home;
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
