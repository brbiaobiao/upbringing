package com.teaching.upbringing.modular.mine;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.lefore.tutoring.R;
import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.entity.IdentityAuthEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.presenter.StudentIdentityPresenter;
import com.teaching.upbringing.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class StudentIdentityActivity extends BaseMVPActivity<StudentIdentityContract.Ipresenter> implements StudentIdentityContract.IView {

    @BindView(R.id.ck_man)
    CheckBox mCkMan;
    @BindView(R.id.ck_women)
    CheckBox mCkWomen;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    private int sex=0;


    @Override
    protected StudentIdentityContract.Ipresenter initPresenter() {
        return new StudentIdentityPresenter(this);
    }


    @Override
    protected Integer getContentId() {
        return R.layout.activity_student_identity;
    }

    @Override
    protected void init() {
        setTitleText("学员认证");
        StatusBarUtil.setStatusBarColor(this,R.color.white);
        mCkMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sex=1;
                    mCkMan.setChecked(true);
                    mCkWomen.setChecked(false);
                }
            }
        });
        mCkWomen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sex=2;
                    mCkWomen.setChecked(true);
                    mCkMan.setChecked(false);
                }
            }
        });
    }

    @OnClick({R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if(mEtName.length()==0){
                    ToastUtil.showShort("请输入姓名");
                    return;
                }else if(sex==0){
                    ToastUtil.showShort("请选择性别");
                    return;
                }else{
                    getPresenter().setApplyStudent(mEtName.getText().toString().trim(),sex);
                }
                break;
        }
    }

    @Override
    public void getApplyStudent(UserInfoEntity userInfoEntity) {

    }

    @Override
    public void setIdentityAuth(IdentityAuthEntity identityAuthEntity) {

    }
}
