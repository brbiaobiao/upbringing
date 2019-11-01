package com.teaching.upbringing.modular.mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.outsourcing.library.utils.OnResultUtil;
import com.outsourcing.library.widget.dialog.ActionSheetDialog;
import com.teaching.upbringing.R;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author bb
 * @time 2019/10/30 16:22
 * @des ${个人信息页面}
 **/
public class EditPersonInfoActivity extends BaseMVPActivity<EditPersonlInfoContract.Ipresenter> implements EditPersonlInfoContract.IView {


    @BindView(R.id.iv_head_pic)
    ImageView mIvHeadPic;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.ll_nickname)
    LinearLayout mLlNickname;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.ll_account)
    LinearLayout mLlAccount;
    @BindView(R.id.tv_regist_time)
    TextView mTvRegistTime;
    @BindView(R.id.ll_regist_time)
    LinearLayout mLlRegistTime;
    @BindView(R.id.line_regist_time)
    View mLineRegistTime;
    @BindView(R.id.iv_teacher_id)
    ImageView mIvTeacherId;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_bright_point)
    TextView mTvBrightPoint;
    @BindView(R.id.ll_bright_point)
    LinearLayout mLlBrightPoint;

    private OnResultUtil onResultUtil;

    public static void goIntent(Context context) {
        Intent intent = new Intent(context, EditPersonInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_edit_personl_infor;
    }

    @Override
    protected void init() {
        setTitleText("编辑资料");
        mLlRegistTime.setVisibility(View.GONE);
        mLineRegistTime.setVisibility(View.GONE);
    }

    private void showSelectDialog() {
        final String[] items = {"男", "女"};
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this, items, null);
        actionSheetDialog.isTitleShow(false).show();
        actionSheetDialog.setOnOpenItemClickL((parent, view, position, id) -> {
            actionSheetDialog.dismiss();
            mTvSex.setText(items[position]);
        });
    }

    @OnClick({R.id.ll_nickname, R.id.ll_sex, R.id.ll_account, R.id.ll_regist_time,
            R.id.ll_title,R.id.ll_bright_point})
    public void onViewClicked(View view) {
        if(onResultUtil == null) {
            onResultUtil = new OnResultUtil(this);
        }
        switch (view.getId()) {
            case R.id.ll_nickname:
                onResultUtil.call(FillInformationActivity.getCallIntent(this,"昵称","请输入昵称"))
                        .filter(info ->OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> {
                            String reback_text = activityResultInfo.getData().getStringExtra(FillInformationActivity.REBACKTEXT);
                            mTvNickname.setText(reback_text);
                        });
                break;
            case R.id.ll_sex:
                showSelectDialog();
                break;
            case R.id.ll_account:
                onResultUtil.call(FillInformationActivity.getCallIntent(this,"简介","简单介绍下自己吧！"))
                        .filter(info ->OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> {
                            String reback_text = activityResultInfo.getData().getStringExtra(FillInformationActivity.REBACKTEXT);
                            mTvAccount.setText(reback_text);
                        });
                break;
            case R.id.ll_title:
                onResultUtil.call(FillInformationActivity.getCallIntent(this,"头衔","请输入您的头衔"))
                        .filter(info ->OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> {
                            String reback_text = activityResultInfo.getData().getStringExtra(FillInformationActivity.REBACKTEXT);
                            mTvTitle.setText(reback_text);
                        });
                break;
            case R.id.ll_bright_point:
                onResultUtil.call(FillInformationActivity.getCallIntent(this,"亮点","请填写您的亮点，让同学们跟喜欢您"))
                        .filter(info ->OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> {
                            String reback_text = activityResultInfo.getData().getStringExtra(FillInformationActivity.REBACKTEXT);
                            mTvBrightPoint.setText(reback_text);
                        });
                break;
        }
    }
}
