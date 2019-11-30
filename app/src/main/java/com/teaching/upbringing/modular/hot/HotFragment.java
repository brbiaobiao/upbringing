package com.teaching.upbringing.modular.hot;

import com.lefore.tutoring.R;
import com.teaching.upbringing.modular.home.HomeContract;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;

/**
 * @author ChenHh
 * @time 2019/10/27 16:02
 * @des
 **/
public class HotFragment extends BaseMVPFragment<HotContract.Ipresenter> implements HomeContract.IView {
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected Integer getContentId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void init() {

    }
}
