package com.teaching.upbringing.modular.community;

import com.teaching.upbringing.R;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;

/**
 * @author ChenHh
 * @time 2019/10/27 16:02
 * @des
 **/
public class CommunityFragment extends BaseMVPFragment<CommunityContract.Ipresenter> implements CommunityContract.IView {
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected Integer getContentId() {
        return R.layout.fragment_communuty;
    }

    @Override
    protected void init() {

    }
}
