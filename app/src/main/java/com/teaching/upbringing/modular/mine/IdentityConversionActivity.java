package com.teaching.upbringing.modular.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.outsourcing.library.utils.StatusBarUtil;
import com.outsourcing.library.widget.dialog.ActionSheetDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.teaching.upbringing.R;
import com.teaching.upbringing.manager.UserInfo;
import com.teaching.upbringing.modular.setting.AboutUsActivity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.ToastUtil;
import com.teaching.upbringing.widget.dialog.ConfigurableDialog;


import butterknife.BindView;
import butterknife.OnClick;

public class IdentityConversionActivity extends BaseMVPActivity {
    @BindView(R.id.rl_teacher)
    RelativeLayout mRlTeacher;
    @BindView(R.id.iv_teacher)
    ImageView mIvTeacher;
    @BindView(R.id.rl_student)
    RelativeLayout mRlStudent;
    @BindView(R.id.iv_student)
    ImageView mIvStudent;

    @Override
    protected Integer getContentId() {
        return R.layout.activity_identity_conversion;
    }


    public static void goInto(Context context){
        Intent intent = new Intent(context, IdentityConversionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        setTitleText("身份切换界面");
        StatusBarUtil.setStatusBarColor(this,R.color.white);
    }

    @OnClick({R.id.rl_teacher,R.id.rl_student})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_teacher:
                mIvTeacher.setVisibility(View.VISIBLE);
                mIvStudent.setVisibility(View.GONE);
                break;
            case R.id.rl_student:
                mIvTeacher.setVisibility(View.GONE);
                mIvStudent.setVisibility(View.VISIBLE);
                break;
        }
    }


    //选择职业
    private void showProfessionDialog() {
        final String[] items = {"在校学生", "教师","其他"};
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this, items, null);
        actionSheetDialog.isTitleShow(false).show();
        actionSheetDialog.setOnOpenItemClickL((parent, view, position, id) -> {
            actionSheetDialog.dismiss();
            ToastUtil.showShort(items[position].toString());
        });
    }


    //选择教育程度
    private void showMajorDialog() {
        final String[] items = {"本科", "硕士","博士"};
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this, items, null);
        actionSheetDialog.isTitleShow(false).show();
        actionSheetDialog.setOnOpenItemClickL((parent, view, position, id) -> {
            actionSheetDialog.dismiss();
        });
    }

}
