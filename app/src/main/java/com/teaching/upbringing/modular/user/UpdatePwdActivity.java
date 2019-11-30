package com.teaching.upbringing.modular.user;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lefore.tutoring.R;
import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.presenter.UpdatePwdPresenter;
import com.teaching.upbringing.utils.StringUtils;
import com.teaching.upbringing.utils.TimeCountUtil;
import com.teaching.upbringing.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author bb
 * @time 2019/11/4 14:34
 * @des ${修改密码}
 **/
public class UpdatePwdActivity extends BaseMVPActivity<UpdatePwdContract.IPresenter> implements UpdatePwdContract.IView {

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_login_code)
    EditText mEtLoginCode;
    @BindView(R.id.tv_verification_code)
    TextView mTvCode;
    @BindView(R.id.et_password_one)
    EditText mEtPasswordOne;
    @BindView(R.id.et_password_two)
    EditText mEtPasswordTwo;
    @BindView(R.id.ll_input)
    LinearLayout mLlInput;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    @BindView(R.id.iv_gone)
    ImageView mIvGone;
    private int show =0;

    private TimeCountUtil mTimeCountUtil;

    public static void goInto(Context context){
        Intent intent = new Intent(context, UpdatePwdActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_verification_code, R.id.tv_register,R.id.iv_gone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_verification_code:

                getPresenter().verification(mEtPhone.getText().toString().trim());
                break;
            case R.id.tv_register:
                getPresenter().updatePwd(mEtLoginCode.getText().toString().trim(),
                        mEtPasswordOne.getText().toString().trim(),
                        mEtPhone.getText().toString().trim());
                break;
            case R.id.iv_gone:
                if(show==0){
                    mIvGone.setImageResource(R.mipmap.icon_show);
                    mEtPasswordOne.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEtPasswordOne.setSelection(mEtPasswordOne.getText().length());
                    show=1;
                }else {
                    mIvGone.setImageResource(R.mipmap.icon_gone);
                    mEtPasswordOne.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEtPasswordOne.setSelection(mEtPasswordOne.getText().length());
                    show=0;
                }
                break;
        }
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
                    && mEtPasswordOne.getText().toString().trim().equals(mEtPasswordTwo.getText().toString().trim())
                    && mEtPasswordOne.getText().toString().length() >= 6) {
                mTvRegister.setEnabled(true);
            } else {
                mTvRegister.setEnabled(false);
            }
        }
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_updatepwd;
    }

    @Override
    protected UpdatePwdContract.IPresenter initPresenter() {
        return new UpdatePwdPresenter(this);
    }

    @Override
    protected void init() {
        setTitleText("修改登录密码");
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        mTimeCountUtil = new TimeCountUtil(this, 60000, 1000, mTvCode);
        mEtLoginCode.addTextChangedListener(new MyTextWatcher(mEtLoginCode));
        mEtPhone.addTextChangedListener(new MyTextWatcher(mEtPhone));
        mEtPasswordOne.addTextChangedListener(new MyTextWatcher(mEtPasswordOne));
        mEtPasswordTwo.addTextChangedListener(new MyTextWatcher(mEtPasswordTwo));
    }


    @Override
    public void updatePwd(UserInfoEntity userInfoEntity) {
        ToastUtil.showShort("修改成功");
        LoginActivity.goInto(this);
        finish();
    }

    @Override
    public void verification(CaptchaEntity captchaEntity) {
        mTimeCountUtil.start();
    }
}
