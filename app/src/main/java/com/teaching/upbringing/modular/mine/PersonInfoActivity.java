package com.teaching.upbringing.modular.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.PreferenceManagers;
import com.outsourcing.library.utils.ShapeUtils;
import com.teaching.upbringing.R;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.ToastUtil;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import butterknife.BindView;

/**
 * @author bb
 * @time 2019/10/30 16:22
 * @des ${个人信息页面}
 **/
public class PersonInfoActivity extends BaseMVPActivity<PersonInforContract.Ipresenter> implements PersonInforContract.IView {

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
    @BindView(R.id.gp_teacher_id)
    Group mGpTeacherId;

//    private OnResultUtil onResultUtil = new OnResultUtil(this);

    public static void goIntent(Context context) {
        Intent intent = new Intent(context, PersonInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected PersonInforContract.Ipresenter initPresenter() {
        return new PersonInfoPersenter(this);
    }

    @Override
    protected Integer getContentId() {
        return R.layout.activity_personl_infor;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void init() {
        setTitleText("个人资料");
        isShowTitleRightText(true);
        setTitleRightText("编辑").setTitleRightTextColor(AppUtils.getColor(R.color.white))
                .setTitleRightTextClick(v -> {
                    /*onResultUtil.call(EditPersonInfoActivity.goIntent(this))
                            .filter(info -> OnResultUtil.isOk(info))
                            .subscribe(activityResultInfo -> getPresenter().initData());*/

                    Intent intent = new Intent(this, EditPersonInfoActivity.class);
                    this.startActivityForResult(intent,001);
                });
        TextView titleRightText = getTitleRightText();
        GradientDrawable shape = ShapeUtils.createShape(-1, 26, -1, null, "#FD8440");
        titleRightText.setBackground(shape);

        getPresenter().initData();
    }

    @Override
    public void setInit(PersonInforEntity personInforEntity) {
        if(personInforEntity == null) return;

        mGpTeacherId.setVisibility(personInforEntity.isIfTeacher()?View.VISIBLE:View.GONE);

        //普通信息
        mTvNickname.setText(personInforEntity.getNickname());
        mTvSex.setText(personInforEntity.getSex() == 1?"男":"女");
        mTvAccount.setText(personInforEntity.getIntroduce());

        //教员信息
        mTvTitle.setText(personInforEntity.getTitle());
        mTvBrightPoint.setText(personInforEntity.getBrightSpot());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            getPresenter().initData();
        }
    }
}
