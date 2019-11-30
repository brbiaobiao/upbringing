package com.teaching.upbringing.modular.user;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lefore.tutoring.R;
import com.outsourcing.library.utils.KeyboardUtils;
import com.outsourcing.library.utils.PreferenceManagers;
import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.manager.UserInfo;
import com.teaching.upbringing.modular.ShalpActivity;
import com.teaching.upbringing.modular.main.MainActivity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.presenter.LoginPresenter;
import com.teaching.upbringing.utils.StringUtils;
import com.teaching.upbringing.utils.TimeCountUtil;
import com.teaching.upbringing.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMVPActivity<LoginContract.IPresenter> implements LoginContract.IView {

    private static final int REQUEST_CODE_CHOOSE = 1;
    @BindView(R.id.tv_login)
    TextView mTvLogin;//登录
    @BindView(R.id.et_phone)
    EditText mEtPhone;//手机号码
    @BindView(R.id.et_login_code)
    EditText mEtLoginCode;//验证码
    @BindView(R.id.tv_verification_code)
    TextView mTvCode;//获取验证码
    private TimeCountUtil mTimeCountUtil;
    private boolean firstOpenApp;


    public static final void goInto(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static Intent getCallInto(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }


    @Override
    protected LoginContract.IPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @OnClick({R.id.tv_verification_code, R.id.tv_login, R.id.tv_register, R.id.tv_password_login,R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                getPresenter().login(mEtLoginCode.getText().toString().trim() + "", mEtPhone.getText().toString().trim() + "");
                break;
            case R.id.tv_verification_code:
                getPresenter().verification(mEtPhone.getText().toString());
                break;
            case R.id.tv_register:
                RegisterActivity.goInto(this);
                break;
            case R.id.tv_password_login:
                Intent intents = new Intent(this, PasswordLoginActivity.class);
                startActivity(intents);
                break;
            case R.id.iv_close:
                onBackPressed();
//                finish();
                break;
        }
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        StatusBarUtil.setStatusBarColor(this,R.color.white);
        mFlTitleBarContent.setVisibility(View.GONE);
        mTimeCountUtil = new TimeCountUtil(this, 60000, 1000, mTvCode);
        mEtLoginCode.addTextChangedListener(new MyTextWatcher(mEtLoginCode));
        mEtPhone.addTextChangedListener(new MyTextWatcher(mEtPhone));

        firstOpenApp = PreferenceManagers.getBoolean(ShalpActivity.ISFIRSTOPEN,false);
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
                    && mEtLoginCode.getText().toString().trim().length() >= 4) {
                mTvLogin.setEnabled(true);
            } else {
                mTvLogin.setEnabled(false);
            }
        }
    }


    @Override
    public void verification(CaptchaEntity entity) {
        mTimeCountUtil.start();
    }

    @Override
    public void login(UserInfoEntity entity) {
        ToastUtil.showShort("登录成功");
        KeyboardUtils.hideSoftInput(this);
        mEtPhone.setText("");
        mEtLoginCode.setText("");
        PreferenceManagers.saveValue(UserInfo.USERID, String.valueOf(entity.getUserId()));
        UserInfo.notifyUserInfoChange();
        if(firstOpenApp) {
            MainActivity.goInto(this);
        }else {
            setResult(RESULT_OK);
        }
        finish();
    }

    @Override
    public void isFirstOpenApp(boolean flag) {
        firstOpenApp = flag;
    }
}
