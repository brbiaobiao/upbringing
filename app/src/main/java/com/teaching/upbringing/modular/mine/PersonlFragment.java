package com.teaching.upbringing.modular.mine;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teaching.upbringing.R;
import com.teaching.upbringing.modular.setting.SettingActivity;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;
import com.teaching.upbringing.utils.FragmentHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ChenHh
 * @time 2019/10/27 16:03
 * @des
 **/
public class PersonlFragment extends BaseMVPFragment<PersonlContract.Ipresenter> implements PersonlContract.IView {

    @BindView(R.id.rl_head)
    RelativeLayout mRlHead;
    @BindView(R.id.iv_setting)
    ImageView mIvSetting;
    @BindView(R.id.iv_heat)
    ImageView mIvHeat;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_sign)
    TextView mTvSign;
    @BindView(R.id.iv_to_right)
    ImageView mIvToRight;
    @BindView(R.id.fl_inform)
    FrameLayout mFlInform;
    @BindView(R.id.fl_my_order)
    FrameLayout mFlMyOrder;
    @BindView(R.id.fl_used_service)
    FrameLayout mFlUsedService;
    @BindView(R.id.fl_flatform)
    FrameLayout mFlFlatform;

    @Override
    protected Integer getContentId() {
        return R.layout.fragment_personl;
    }

    @Override
    protected void init() {
        FragmentHelper.addFragment(getActivity(),R.id.fl_inform,new MyInfromationFragment(),null,
                FragmentHelper.NONE,FragmentHelper.NONE);
        FragmentHelper.addFragment(getActivity(),R.id.fl_my_order,new MyOrderFragment(),null,
                FragmentHelper.NONE,FragmentHelper.NONE);
        FragmentHelper.addFragment(getActivity(),R.id.fl_used_service,new UseredFragment(),null,
                FragmentHelper.NONE,FragmentHelper.NONE);
        FragmentHelper.addFragment(getActivity(),R.id.fl_flatform,new FlatformFragment(),null,
                FragmentHelper.NONE,FragmentHelper.NONE);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @OnClick({R.id.iv_setting, R.id.iv_heat, R.id.tv_nickname, R.id.tv_sign, R.id.iv_to_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
                SettingActivity.goIntent(getActivity());
                break;
            case R.id.iv_heat:
            case R.id.tv_nickname:
            case R.id.tv_sign:
            case R.id.iv_to_right:
                PersonlInforActivity.goIntent(getActivity());
                break;
        }
    }
}
