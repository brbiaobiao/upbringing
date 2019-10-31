package com.teaching.upbringing.modular.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.ShapeUtils;
import com.outsourcing.library.widget.dialog.ActionSheetDialog;
import com.teaching.upbringing.R;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author bb
 * @time 2019/10/30 16:22
 * @des ${个人信息页面}
 **/
public class PersonlInforActivity extends BaseMVPActivity<PersonlInforContract.Ipresenter> implements PersonlContract.IView {


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
    private String sex;

    public static void goIntent(Context context) {
        Intent intent = new Intent(context, PersonlInforActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_personl_infor;
    }

    @Override
    protected void init() {
        setTitleText("个人资料");
        isShowTitleRightText(true);
        setTitleRightText("编辑").setTitleRightTextColor(AppUtils.getColor(R.color.white))
                .setTitleRightTextClick(v -> {
                    //iOS风格dialog示例
                    showSelectDialog();
                });
        TextView titleRightText = getTitleRightText();
        GradientDrawable shape = ShapeUtils.createShape(-1, 26, -1, null, "#FD8440");
        titleRightText.setBackground(shape);
    }

    private void showSelectDialog() {
        final String[] items = {"男", "女"};
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this, items, null);
        actionSheetDialog.isTitleShow(false).show();
        actionSheetDialog.setOnOpenItemClickL((parent, view, position, id) -> {
            actionSheetDialog.dismiss();
            if (position == 0) {//男
                sex = "男";
            } else {
                sex = "女";
            }
            mTvSex.setText(sex);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_nickname, R.id.ll_sex, R.id.ll_account, R.id.ll_regist_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_nickname:
                break;
            case R.id.ll_sex:
                showSelectDialog();
                break;
            case R.id.ll_account:
                break;
            case R.id.ll_regist_time:
                break;
        }
    }
}
