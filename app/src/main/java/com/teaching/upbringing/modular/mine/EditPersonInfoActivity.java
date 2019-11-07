package com.teaching.upbringing.modular.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.outsourcing.library.mvp.observer.NextObserver;
import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.DateUtils;
import com.outsourcing.library.utils.NotificationUtils;
import com.outsourcing.library.utils.OnResultUtil;
import com.outsourcing.library.widget.dialog.ActionSheetDialog;
import com.outsourcing.library.widget.dialog.listener.OnOpenItemClickL;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.teaching.upbringing.R;
import com.teaching.upbringing.application.AppFileManager;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.manager.UserInfo;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.CameraUtils;
import com.teaching.upbringing.utils.StringUtils;
import com.teaching.upbringing.widget.dialog.TipsDialog;

import java.io.File;

import androidx.constraintlayout.widget.Group;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.anotherjack.avoidonresult.ActivityResultInfo;

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
    @BindView(R.id.gp_teacher_id)
    Group mGpTeacherId;

    private OnResultUtil onResultUtil;

    public static Intent goIntent(Context context) {
        Intent intent = new Intent(context, EditPersonInfoActivity.class);
        return intent;
    }

    @Override
    protected EditPersonlInfoContract.Ipresenter initPresenter() {
        return new EditPersonInfoPresenter(this);
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

        getPresenter().getInfo();
    }

    private void showSelectDialog() {
        final String[] items = {"男", "女"};
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this, items, null);
        actionSheetDialog.isTitleShow(false).show();
        actionSheetDialog.setOnOpenItemClickL((parent, view, position, id) -> {
            actionSheetDialog.dismiss();
            getPresenter().setSex(position + 1);
            UserInfo.notifyUserInfoChange();
        });
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    @OnClick({R.id.iv_head_pic, R.id.ll_nickname, R.id.ll_sex, R.id.ll_account,
            R.id.ll_regist_time, R.id.ll_title, R.id.ll_bright_point})
    public void onViewClicked(View view) {
        if (onResultUtil == null) {
            onResultUtil = new OnResultUtil(this);
        }
        switch (view.getId()) {
            case R.id.iv_head_pic:
                showActionSheetDialog();
                break;
            case R.id.ll_nickname:
                onResultUtil.call(FillInformationActivity.getCallIntent(this, "昵称",
                        "请输入昵称", 1,mTvNickname.getText().toString().trim()))
                        .filter(info -> OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> getPresenter().getInfo());
                break;
            case R.id.ll_sex:
                showSelectDialog();
                break;
            case R.id.ll_account:
                onResultUtil.call(FillInformationActivity.getCallIntent(this, "简介",
                        "简单介绍下自己吧！", 2,mTvAccount.getText().toString().trim()))
                        .filter(info -> OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> getPresenter().getInfo());
                break;
            case R.id.ll_title:
                onResultUtil.call(FillInformationActivity.getCallIntent(this, "头衔",
                        "请输入您的头衔", 3,mTvTitle.getText().toString().trim()))
                        .filter(info -> OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> getPresenter().getInfo());
                break;
            case R.id.ll_bright_point:
                onResultUtil.call(FillInformationActivity.getCallIntent(this, "亮点",
                        "请填写您的亮点，让同学们跟喜欢您", 4,mTvBrightPoint.getText().toString().trim()))
                        .filter(info -> OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> getPresenter().getInfo());
                break;
        }
    }

    @Override
    public void setInfor(PersonInforEntity personInforEntity) {
        if (personInforEntity == null)
            return;
        mGpTeacherId.setVisibility(personInforEntity.isIfTeacher() ? View.VISIBLE : View.GONE);

        //普通信息
        mTvNickname.setText(personInforEntity.getNickname());
        if(personInforEntity.getSex() == 1){
            mTvSex.setText("男");
        }else if(personInforEntity.getSex() == 2){
            mTvSex.setText("女");
        }else if(personInforEntity.getSex() == 0){
            mTvSex.setText("未知");
        }
        mTvAccount.setText(personInforEntity.getIntroduce());
        String createdAt = DateUtils.long2String(personInforEntity.getCreatedAt(), "yyyy-MM-dd");
        mTvRegistTime.setText(createdAt);

        //教员信息
        mTvTitle.setText(personInforEntity.getTitle());
        mTvBrightPoint.setText(personInforEntity.getBrightSpot());
    }

    private void showActionSheetDialog() {
        final String[] stringItems = {"相机拍摄", "从相册导入"};
        final ActionSheetDialog dialog = new ActionSheetDialog(EditPersonInfoActivity.this, stringItems, null);
        dialog.isTitleShow(true).show();
        dialog.title("请选择一种类型");
        dialog.titleTextColor(Color.parseColor("#8e8e94"));
        dialog.titleTextSize_SP(13);
        dialog.itemTextSize(18);
        dialog.setOnOpenItemClickL(new OnOpenItemClickL() {
            @Override
            public void onOpenItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                if (position == 0) {
                    takePhoto();
                } else if (position == 1) {
                    fromTheAlbum();
                }
            }
        });
    }

    private void takePhoto() {
        RxPermissions permissions = new RxPermissions(this);
        permissions.requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
                .subscribe(new NextObserver<Permission>() {
                    @Override
                    public void onNext(Permission permission) {
                        if (permission.granted) {
                            openCamera();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            TipsDialog dialog = new TipsDialog(EditPersonInfoActivity.this,getSupportFragmentManager());
                            dialog.setTips(null, "需要授权才能使用，请进行授权");
                            dialog.setRightBtnText("同意");
                            dialog.setOnLeftConfirmListener(v -> dialog.dismiss());
                            dialog.setOnRightConfirmListener(v -> {
                                dialog.dismiss();
                                takePhoto();
                            });
                            dialog.show();
                        } else {
                            TipsDialog dialog = new TipsDialog(EditPersonInfoActivity.this, getSupportFragmentManager());
                            dialog.setTips(null, "请允许权限才能够进行下一步操作");
                            dialog.setRightBtnText("确认");
                            dialog.setOnLeftConfirmListener(v -> dialog.dismiss());
                            dialog.setOnRightConfirmListener(v -> {
                                NotificationUtils.toSetting(EditPersonInfoActivity.this);
                                dialog.dismiss();

                            });
                            dialog.show();
                        }
                    }
                });
    }

    private void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        File file = new File(AppFileManager.getPictureDir(), "logo_" + System.currentTimeMillis() + ".jpg");
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(AppUtils.getApp(), getContext().getPackageName() + ".FileProvider",
                    file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        new OnResultUtil(EditPersonInfoActivity.this).call(intent)
                .filter(info -> OnResultUtil.isOk(info))
                .subscribe(new NextObserver<ActivityResultInfo>() {
                    @Override
                    public void onNext(ActivityResultInfo activityResultInfo) {
                        boolean b = CameraUtils.savePicture(file.getPath());
                        if (b) {
                            getPresenter().saveUserImg(file);
                        }
                    }
                });
    }

    private void fromTheAlbum() {
        //相册导入
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        new OnResultUtil(EditPersonInfoActivity.this).call(intent)
                .filter(info -> OnResultUtil.isOk(info))
                .subscribe(new NextObserver<ActivityResultInfo>() {
                    @Override
                    public void onNext(ActivityResultInfo activityResultInfo) {
                        String path = CameraUtils.handlerChoosePic(EditPersonInfoActivity.this, activityResultInfo.getData());
                        if (!StringUtils.isEmpty(path)) {
                            getPresenter().saveUserImg(new File(path));
                        }
                    }
                });
    }
}
