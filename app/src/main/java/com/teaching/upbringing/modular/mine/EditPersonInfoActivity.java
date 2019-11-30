package com.teaching.upbringing.modular.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.request.RequestOptions;
import com.lefore.tutoring.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.nanchen.compresshelper.CompressHelper;
import com.outsourcing.library.mvp.observer.NextObserver;
import com.outsourcing.library.utils.DateUtils;
import com.outsourcing.library.utils.OnResultUtil;
import com.outsourcing.library.utils.PreferenceManagers;
import com.outsourcing.library.utils.RxBus;
import com.outsourcing.library.utils.StatusBarUtil;
import com.outsourcing.library.utils.image.ImageLoader;
import com.outsourcing.library.widget.GlideRoundTransform;
import com.outsourcing.library.widget.dialog.ActionSheetDialog;
import com.teaching.upbringing.application.AppApplication;
import com.teaching.upbringing.entity.Choose;
import com.teaching.upbringing.entity.OssEntity;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.entity.UserInfoEntity;
import com.teaching.upbringing.manager.UserInfo;
import com.teaching.upbringing.mvpBase.BaseMVPActivity;
import com.teaching.upbringing.utils.StringUtils;
import com.teaching.upbringing.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.Group;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;


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
    private OSSClient oss;
    private String picPath;
    private File path;
    private String key;
    private int themeId;
    private List<LocalMedia> selectList = new ArrayList<>();

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
        StatusBarUtil.setStatusBarColor(this,R.color.white);
        getPresenter().getInfo(true);
        themeId = R.style.white_pic;

        /*RxBus3.getDefault().toObservable(Choose.class).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new SimpleSubscriber<Choose>() {
                    @Override
                    public void onNext(Choose imagePath) {
                        String imagePath2 = imagePath.getChoose();
                        ToastUtil.showShort(imagePath2);
                    }
                });*/

        RxBus.getDefault().toObservable(Choose.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NextObserver<Choose>() {
                    @Override
                    public void onNext(Choose choose) {
                        String imagePath2 = choose.getChoose();
                       // ToastUtil.showShort(imagePath2);
                      //todo 上传图片
                        getPresenter().setImgUrl(imagePath2);
                    }
                });
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
                //垃圾后台,菜到忍无可忍
                showActionSheetDialog();
//                showActionSheetDialog();
//                PictureSelector.create(EditPersonInfoActivity.this)
//                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                        .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
//                        .maxSelectNum(9)// 最大图片选择数量
//                        .minSelectNum(1)// 最小选择数量
//                        .imageSpanCount(3)// 每行显示个数
//                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
//                        .previewImage(false)// 是否可预览图片
//                        .isCamera(true)// 是否显示拍照按钮
//                        .isZoomAnim(false)// 图片列表点击 缩放效果 默认true
//                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
//                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
//                        .enableCrop(true)// 是否裁剪
//                        .compress(true)// 是否压缩
//                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                        //.compressSavePath(getPath())//压缩图片保存地址
//                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                        .withAspectRatio(3, 3)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                        .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
//                        .isGif(false)// 是否显示gif图片
//                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                        .circleDimmedLayer(false)// 是否圆形裁剪
//                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                        .openClickSound(false)// 是否开启点击声音
////                        .selectionMedia(false)// 是否传入已选图片
//                        //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        //                        .videoMaxSecond(15)
//                        //                        .videoMinSecond(10)
//                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
//                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
//                        .minimumCompressSize(100)// 小于100kb的图片不压缩
//                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                       // .rotateEnabled(true) // 裁剪是否可旋转图片
//                       // .scaleEnabled(true)// 裁剪是否可放大缩小图片
//                        //.videoQuality()// 视频录制质量 0 or 1
//                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
//                        //.recordVideoSecond()//录制视频秒数 默认60s
//                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

                break;
            case R.id.ll_nickname:
                onResultUtil.call(FillInformationActivity.getCallIntent(this, "昵称",
                        "请输入昵称", 1,mTvNickname.getText().toString().trim()))
                        .filter(info -> OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> getPresenter().getInfo(true));
                break;
            case R.id.ll_sex:
                showSelectDialog();
                break;
            case R.id.ll_account:
                onResultUtil.call(FillInformationActivity.getCallIntent(this, "简介",
                        "简单介绍下自己吧！", 2,mTvAccount.getText().toString().trim()))
                        .filter(info -> OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> getPresenter().getInfo(true));
                break;
            case R.id.ll_title:
                onResultUtil.call(FillInformationActivity.getCallIntent(this, "头衔",
                        "请输入您的头衔", 3,mTvTitle.getText().toString().trim()))
                        .filter(info -> OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> getPresenter().getInfo(true));
                break;
            case R.id.ll_bright_point:
                onResultUtil.call(FillInformationActivity.getCallIntent(this, "亮点",
                        "请填写您的亮点，让同学们跟喜欢您", 4,mTvBrightPoint.getText().toString().trim()))
                        .filter(info -> OnResultUtil.isOk(info))
                        .subscribe(activityResultInfo -> getPresenter().getInfo(true));
                break;
        }
    }


    private void showActionSheetDialog() {
        final String[] stringItems = {"相机拍摄", "从相册导入"};
        final ActionSheetDialog dialog = new ActionSheetDialog(EditPersonInfoActivity.this, stringItems, null);
        dialog.isTitleShow(true).show();
        dialog.title("请选择一种类型");
        dialog.titleTextColor(Color.parseColor("#8e8e94"));
        dialog.titleTextSize_SP(13);
        dialog.itemTextSize(18);
        dialog.setOnOpenItemClickL((parent, view, position, id) -> {
            dialog.dismiss();
            if (position == 0) {
                takePhoto();
            } else if (position == 1) {
                fromTheAlbum();
            }
        });
    }

    private void takePhoto(){
        PictureSelector.create(EditPersonInfoActivity.this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(themeId)// 主题样式设置 具体参考 values/styles
                .maxSelectNum(9)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(3, 3)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 废弃 改用cutOutQuality()
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()////显示多少秒以内的视频or音频也可适用
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private void fromTheAlbum(){
        PictureSelector.create(EditPersonInfoActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(3)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(false)// 是否可预览图片
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(3, 3)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                .isGif(false)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                //                        .selectionMedia(false)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                //                        .videoMaxSecond(15)
                //                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的

                    getPresenter().setOss(3);
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getCompressPath());
                         path = new File(media.getCompressPath());
                        RequestOptions myOptions = new RequestOptions()
                                .centerCrop()
                                .fallback(R.mipmap.icon_person_head)
                                .placeholder(R.mipmap.icon_person_head)
                                .error(R.mipmap.icon_person_head)
                                .transform(new GlideRoundTransform(getContext(), 90));
                        ImageLoader.loadOption(getContext(), media.getCompressPath(), new ImageLoader.Option(myOptions), mIvHeadPic);
                        PreferenceManagers.saveValue(PreferenceManagers.HEAD_PIC,media.getCompressPath());
                    }
                    break;
            }
        }
    }

    @Override
    public void setInfor(PersonInforEntity personInforEntity) {
        if (personInforEntity == null)
            return;

        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .fallback(R.mipmap.icon_person_head)
                .placeholder(R.mipmap.icon_person_head)
                .error(R.mipmap.icon_person_head)
                .transform(new GlideRoundTransform(this, 90));
        String head_pic = PreferenceManagers.getString(PreferenceManagers.HEAD_PIC, "");
        ImageLoader.loadOption(this, StringUtils.isEmpty(head_pic) ? "" : head_pic, new ImageLoader.Option(myOptions), mIvHeadPic);

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

    @Override
    public void getOss(OssEntity ossEntity) {
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(ossEntity.getAccessKeyId(), ossEntity.getAccessKeySecret(), ossEntity.getSecurityToken());
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
            conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
            conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
            conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
            OSSLog.enableLog();
            oss = new OSSClient(AppApplication.getInstance(), ossEntity.getEndPoint(), credentialProvider, conf);

//            String objectKey = ossEntity.getRegion()+ StringUtils.get16Random() + ".jpg";
        String objectKey = ossEntity.getFolder()+ StringUtils.get16Random() + ".jpg";
            Log.i("objectKey",objectKey);


     //   upload(fileName, CompressHelper.getDefault(getActivity()).compressToFile(new File(imagePath1)).toString(), tokenBean.getData().getBucketName(), tokenBean.getData().getCallbackUrl());



        final PutObjectRequest put = new PutObjectRequest(ossEntity.getBucketName(), ossEntity.getKey(), CompressHelper.getDefault(this).compressToFile(path).toString());
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setHeader("x-oss-object-acl", "public-read");
//        put.setMetadata(metadata);

        Log.d("uploadName", objectKey + ":" +  CompressHelper.getDefault(this).compressToFile(path).toString()+"  : "+ossEntity.getBucketName());
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");

                Choose choose = new Choose();
                key = null;
                try {
                    key = oss.presignConstrainedObjectURL(ossEntity.getBucketName(), ossEntity.getKey(), 3600);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
                Log.d("stringkey",key);
                choose.setChoose(ossEntity.getKey());
//                RxBus3.getDefault().post(choose);
                RxBus.getDefault().post(choose);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });


    }

    @Override
    public void getImgUrl(UserInfoEntity userInfoEntity) {
        ToastUtil.showShort("上传成功");
    }
}
