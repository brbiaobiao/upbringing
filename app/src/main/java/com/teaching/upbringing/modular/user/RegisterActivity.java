package com.teaching.upbringing.modular.user;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.teaching.upbringing.R;
import com.teaching.upbringing.entity.CaptchaEntity;
import com.teaching.upbringing.entity.TestEntity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.TimeCountUtil;
import com.teaching.upbringing.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseMVPActivity<RegisterContract.IPresenter> implements RegisterContract.IView{

    @BindView(R.id.tv_register)
    TextView mTvRegister;//注册
    @BindView(R.id.et_phone)
    EditText mEtPhone;//手机号码
    @BindView(R.id.et_login_code)
    EditText mEtLoginCode;//验证码
    @BindView(R.id.tv_verification_code)
    TextView mTvCode;//获取验证码
    private TimeCountUtil mTimeCountUtil;


    @Override
    protected Integer getContentId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
         mTimeCountUtil = new TimeCountUtil(this, 60000, 1000, mTvCode);
    }

    @OnClick({R.id.tv_verification_code,R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                getPresenter().signIn(mEtLoginCode.getText().toString(),mEtPhone.getText().toString());
                break;
            case R.id.tv_verification_code:
                if(mEtPhone.length()==0){
                    ToastUtil.showShort("请输入手机号码");
                } else if(mEtPhone.length()!=11){
                    ToastUtil.showShort("请输入正确的手机号码");
                }else {
                    getPresenter().signInCaptcha(mEtPhone.getText().toString());
                }
                break;
        }
    }


    @Override
    public void signInCaptcha(CaptchaEntity entity) {
        mTimeCountUtil.start();
        ToastUtil.showShort("获取验证码成功");
    }

    @Override
    public void signIn(TestEntity entity) {

    }
}
