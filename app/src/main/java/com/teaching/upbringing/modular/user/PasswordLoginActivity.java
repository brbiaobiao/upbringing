package com.teaching.upbringing.modular.user;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.outsourcing.library.utils.KeyboardUtils;
import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.R;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.modular.main.MainActivity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.presenter.PasswordLoginPresenter;
import com.teaching.upbringing.utils.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class PasswordLoginActivity extends BaseMVPActivity<PasswordLoginContract.IPresenter> implements PasswordLoginContract.IView{

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.tv_login)
    TextView mTvLogin;

    @Override
    protected PasswordLoginContract.IPresenter initPresenter() {
        return new PasswordLoginPresenter(this);
    }

    @Override
    public void passwordLogin(UserInfoEntity entity) {
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

        }
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_password;
    }

    @Override
    protected void init() {
        setTitleText("密码登录");
        StatusBarUtil.setStatusBarColor(this, R.color.white);
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
