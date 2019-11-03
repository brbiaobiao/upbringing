package com.teaching.upbringing.modular.setting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.outsourcing.library.utils.PreferenceManagers;
import com.teaching.upbringing.R;
import com.teaching.upbringing.modular.user.LoginActivity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ChenHh
 * @time 2019/10/25 10:47
 * @des ${TODO}
 **/
public class SettingActivity extends BaseMVPActivity<SettingContract.IPresenter> implements SettingContract.IView {

    @BindView(R.id.tv_chang_pwd)
    TextView mTvChangPwd;
    @BindView(R.id.tv_common_add)
    TextView mTvCommonAdd;
    @BindView(R.id.tv_class_area_setting)
    TextView mTvClassAreaSetting;
    @BindView(R.id.tv_class_push)
    TextView mTvClassPush;
    @BindView(R.id.switch_release)
    Switch mSwitchRelease;
    @BindView(R.id.tv_login_out)
    TextView mTvLoginOut;


    public static void goIntent(Context context){
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected Integer getContentId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        setTitleText("设置");
        mSwitchRelease.setChecked(false);
        mSwitchRelease.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b)
                ToastUtil.showShort("开");
            else
                ToastUtil.showShort("关");

        });
    }

    @Override
    protected SettingContract.IPresenter initPresenter() {
        return new SettingPresenter(this);
    }

    @OnClick({R.id.tv_chang_pwd, R.id.tv_common_add, R.id.tv_class_area_setting, R.id.tv_class_push, R.id.switch_release, R.id.tv_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_chang_pwd:
                break;
            case R.id.tv_common_add:
                break;
            case R.id.tv_class_area_setting:
                break;
            case R.id.tv_class_push:
                break;
            case R.id.tv_login_out:
                getPresenter().loginOut();
                loginOut();
                break;
        }
    }

    @Override
    public void loginOut() {
        hideProgress();
        PreferenceManagers.saveValue("tokenId", "");
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
