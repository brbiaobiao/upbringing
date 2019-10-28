package com.teaching.upbringing.modular.mine;

import com.teaching.upbringing.R;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;

/**
 * @author ChenHh
 * @time 2019/10/28 0:17
 * @des
 **/
public class MyOrderFragment extends BaseMVPFragment<MyOrderContract.Ipresenter> implements MyOrderContract.IView {

    @Override
    protected Integer getContentId() {
        return R.layout.fragment_myorder;
    }

    @Override
    protected void init() {
    }
}
