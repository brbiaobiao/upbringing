package com.teaching.upbringing.modular.user;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.outsourcing.library.utils.KeyboardUtils;
import com.outsourcing.library.utils.PreferenceManagers;
import com.teaching.upbringing.R;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.manager.UserInfo;
import com.teaching.upbringing.modular.main.MainActivity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.presenter.PasswordLoginPresenter;
import com.teaching.upbringing.utils.StringUtils;
import com.teaching.upbringing.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class PasswordLoginActivity extends BaseMVPActivity<PasswordLoginContract.IPresenter> implements PasswordLoginContract.IView{

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.iv_gone)
    ImageView mIvGone;
    private int show =0;

    @Override
    protected PasswordLoginContract.IPresenter initPresenter() {
        return new PasswordLoginPresenter(this);
    }

    @Override
    public void passwordLogin(UserInfoEntity entity) {
        ToastUtil.showShort("登录成功");
        PreferenceManagers.saveValue(UserInfo.USERID, String.valueOf(entity.getUserId()));
        UserInfo.notifyUserInfoChange();
        KeyboardUtils.hideSoftInput(this);
        MainActivity.goInto(this);
        finish();
    }


    @OnClick({R.id.tv_login,R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                getPresenter().passwordLogin(mEtPhone.getText().toString().trim()+"",mEtPassword.getText().toString().trim()+"");
                break;
            case R.id.tv_forget:
                ForgetActivity.goInto(this);
                break;
            case R.id.iv_gone:
                if(show==0){
                    mIvGone.setImageResource(R.mipmap.icon_show);
                    mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEtPassword.setSelection(mEtPassword.getText().length());
                    show=1;
                }else {
                    mIvGone.setImageResource(R.mipmap.icon_gone);
                    mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEtPassword.setSelection(mEtPassword.getText().length());
                    show=0;
                }
                break;
        }
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_password;
    }

    @Override
    protected void init() {
        setTitleText("密码登录");
        mEtPassword.addTextChangedListener(new MyTextWatcher(mEtPassword));
        mEtPhone.addTextChangedListener(new MyTextWatcher(mEtPhone));
    }


    private class MyTextWatcher implements TextWatcher {
        private View v;

        public MyTextWatcher(View v) {
            this.v = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!StringUtils.isEmpty(mEtPhone.getText()) && mEtPhone.getText().toString().trim().length()==11
                    && mEtPassword.getText().toString().trim().length() >= 4) {
                mTvLogin.setEnabled(true);
            } else {
                mTvLogin.setEnabled(false);
            }
        }
    }
}
