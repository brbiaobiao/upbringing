package com.teaching.upbringing.modular.mine;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.outsourcing.library.utils.StatusBarUtil;
import com.teaching.upbringing.R;
import com.teaching.upbringing.entity.IdentityAuthStatusEntity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.presenter.AuthenticatePresenter;
import com.teaching.upbringing.presenter.StudentIdentityPresenter;

import butterknife.BindView;

public class AuthenticateActivity extends BaseMVPActivity<AuthenticateContract.Ipresenter> implements AuthenticateContract.IView {
    private IdentityAuthStatusEntity.StudentRepulseBean studentRepulse;
    private IdentityAuthStatusEntity.TeacherRepulseBean teacherRepulse;
    @BindView(R.id.tv_student_status)
    TextView mTvStudentStatus;
    @BindView(R.id.tv_teacher_status)
    TextView mTvTeacherStatus;

    @Override
    protected Integer getContentId() {
        return R.layout.activity_authenticate;
    }

    @Override
    protected AuthenticateContract.Ipresenter initPresenter() {
        return new AuthenticatePresenter(this);
    }


    @Override
    protected void init() {
        setTitleText("身份认证");
        StatusBarUtil.setStatusBarColor(this,R.color.white);
        getPresenter().getIdentityAuthStatus();
    }

    public static void goInto(Context context){
        Intent intent = new Intent(context, AuthenticateActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void setIdentityAuthStatus(IdentityAuthStatusEntity identityAuthStatusEntity) {
        studentRepulse = identityAuthStatusEntity.getStudentRepulse();
        teacherRepulse = identityAuthStatusEntity.getTeacherRepulse();
        if(studentRepulse.getReviewStatus()==1){

        }else if(studentRepulse.getReviewStatus()==2){

        }else  if(studentRepulse.getReviewStatus()==3){

        }else {

        }
    }
}
