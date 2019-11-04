package com.teaching.upbringing.modular.user;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.teaching.upbringing.R;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.presenter.ForgetPresenter;
import com.teaching.upbringing.utils.StringUtils;
import com.teaching.upbringing.utils.TimeCountUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetActivity extends BaseMVPActivity<ForgetContract.IPresenter> implements ForgetContract.IView {

    @BindView(R.id.tv_register)
    TextView mTvRegister;//注册
    @BindView(R.id.et_phone)
    EditText mEtPhone;//手机号码
    @BindView(R.id.et_login_code)
    EditText mEtLoginCode;//验证码
    @BindView(R.id.tv_verification_code)
    TextView mTvCode;//获取验证码
    private TimeCountUtil mTimeCountUtil;
    @BindView(R.id.et_password_one)
    EditText mEtPwdOne;//邀请码
    @BindView(R.id.et_password_two)
    EditText mEtPwdTwo;//邀请码

    public static void goInto(Context context){
        Intent intent = new Intent(context, ForgetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activiy_forget;
    }

    @Override
    protected ForgetContract.IPresenter initPresenter() {
        return new ForgetPresenter(this);
    }

    @Override
    protected void init() {
        setTitleText("忘记密码");
        mTimeCountUtil = new TimeCountUtil(this, 60000, 1000, mTvCode);
        mEtLoginCode.addTextChangedListener(new MyTextWatcher(mEtLoginCode));
        mEtPhone.addTextChangedListener(new MyTextWatcher(mEtPhone));
        mEtPwdOne.addTextChangedListener(new MyTextWatcher(mEtPwdOne));
        mEtPwdTwo.addTextChangedListener(new MyTextWatcher(mEtPwdTwo));
    }

    @Override
    public void startTimecount() {
        mTimeCountUtil.start();
    }

    @Override
    public void forgetPwd(CaptchaEntity entity) {
        LoginActivity.goInto(this);
        finish();
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
            if (!StringUtils.isEmpty(mEtPhone.getText()) && mEtPhone.getText().toString().trim().length() == 11
                    && mEtLoginCode.getText().toString().trim().length() >= 4
                    && mEtPwdOne.getText().toString().trim().equals(mEtPwdTwo.getText().toString().trim())
                    && mEtPwdOne.getText().toString().length() >= 6) {
                mTvRegister.setEnabled(true);
            } else {
                mTvRegister.setEnabled(false);
            }
        }
    }


    @OnClick({R.id.tv_verification_code, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                getPresenter().forgetPwd(mEtLoginCode.getText().toString().trim() + "", mEtPwdOne.getText().toString().trim() + "", mEtPhone.getText().toString().trim() + "");
                break;
            case R.id.tv_verification_code:
                getPresenter().forgetPwdCaptcha(mEtPhone.getText().toString().trim() + "");
                break;
        }
    }


}
