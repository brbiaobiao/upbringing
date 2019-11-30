package com.teaching.upbringing.modular.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.lefore.tutoring.R;
import com.outsourcing.library.mvp.observer.NextObserver;
import com.outsourcing.library.mvp.rxbase.RxLife;
import com.outsourcing.library.utils.PreferenceManagers;
import com.outsourcing.library.utils.image.ImageLoader;
import com.outsourcing.library.widget.GlideRoundTransform;
import com.teaching.upbringing.entity.PersonInforEntity;
import com.teaching.upbringing.entity.PersonerFuncWrapper;
import com.teaching.upbringing.manager.UserInfo;
import com.teaching.upbringing.modular.setting.SettingActivity;
import com.teaching.upbringing.mvpBase.BaseMVPFragment;
import com.teaching.upbringing.utils.FragmentHelper;
import com.teaching.upbringing.utils.StringUtils;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import io.github.anotherjack.avoidonresult.ActivityResultInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author ChenHh
 * @time 2019/10/27 16:03
 * @des
 **/
public class PersonlFragment extends BaseMVPFragment<PersonlContract.Ipresenter> implements PersonlContract.IView {

    @BindView(R.id.rl_head)
    RelativeLayout mRlHead;
    @BindView(R.id.iv_setting)
    ImageView mIvSetting;
    @BindView(R.id.iv_heat)
    ImageView mIvHeat;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_sign)
    TextView mTvSign;
    @BindView(R.id.iv_to_right)
    ImageView mIvToRight;
    @BindView(R.id.fl_inform)
    FrameLayout mFlInform;
    @BindView(R.id.fl_my_order)
    FrameLayout mFlMyOrder;
    @BindView(R.id.fl_used_service)
    FrameLayout mFlUsedService;
    @BindView(R.id.fl_flatform)
    FrameLayout mFlFlatform;

    @Override
    protected Integer getContentId() {
        return R.layout.fragment_personl;
    }

    @Override
    protected PersonlContract.Ipresenter initPresenter() {
        return new PersonlPresenter(this);
    }

    @Override
    protected void init() {
        FragmentHelper.addFragment(getActivity(),R.id.fl_inform,new MyInfromationFragment(),null,
                FragmentHelper.NONE,FragmentHelper.NONE);
        FragmentHelper.addFragment(getActivity(),R.id.fl_my_order,new MyOrderFragment(),null,
                FragmentHelper.NONE,FragmentHelper.NONE);
        FragmentHelper.addFragment(getActivity(),R.id.fl_used_service,UseredFragment.newInstance(getUserWrapperList()),null,
                FragmentHelper.NONE,FragmentHelper.NONE);
        FragmentHelper.addFragment(getActivity(),R.id.fl_flatform,FlatformFragment.newInstance(getFlatformWrapperList()),null,
                FragmentHelper.NONE,FragmentHelper.NONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().initData();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UserInfo.getUserInfoChageOb()
                .compose(bindLife(RxLife.Event.DESTROY_VIEW))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NextObserver<UserInfo.UserInfoChangeEvent>() {
                    @Override
                    public void onNext(UserInfo.UserInfoChangeEvent userInfoChangeEvent) {
                        getPresenter().initData();
                        mTvNickname.setText("昵称");
                        mTvSign.setText("简介");
                    }
                });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @OnClick({R.id.iv_setting, R.id.iv_heat, R.id.tv_nickname, R.id.tv_sign, R.id.iv_to_right})
    public void onViewClicked(View view) {
        UserInfo.toLoginIfUnLoginContinue(getActivity(), new NextObserver<ActivityResultInfo>() {
            @SuppressLint("CheckResult")
            @Override
            public void onNext(ActivityResultInfo activityResultInfo) {
                switch (view.getId()) {
                    case R.id.iv_setting:
                        SettingActivity.goIntent(getActivity());
                        break;
                    case R.id.iv_heat:
                    case R.id.tv_nickname:
                    case R.id.tv_sign:
                    case R.id.iv_to_right:
                        PersonInfoActivity.goIntent(getActivity());
                        break;
                }
            }
        });
    }

    @Override
    public void setInfo(PersonInforEntity personInforEntity) {
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .fallback(R.mipmap.icon_person_head)
                .placeholder(R.mipmap.icon_person_head)
                .error(R.mipmap.icon_person_head)
                .transform(new GlideRoundTransform(getActivity(), 90));
        String head_pic = PreferenceManagers.getString(PreferenceManagers.HEAD_PIC, "");
        ImageLoader.loadOption(getActivity(), StringUtils.isEmpty(head_pic) ? "" : head_pic, new ImageLoader.Option(myOptions), mIvHeat);
        mTvNickname.setText(StringUtils.isEmpty(personInforEntity.getNickname())?"昵称":personInforEntity.getNickname());
        mTvSign.setText(StringUtils.isEmpty(personInforEntity.getIntroduce())?"简介":personInforEntity.getIntroduce());
    }

    private ArrayList<PersonerFuncWrapper> getUserWrapperList() {
        ArrayList<PersonerFuncWrapper> PersonerFuncWrapperList = new ArrayList<>();
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_customcourse, "定制课程"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_after_sales,"售后"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_like, "收藏"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_wallet,"钱包"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_evaluation_managa, "评价管理"));
        return PersonerFuncWrapperList;
    }

    private ArrayList<PersonerFuncWrapper> getFlatformWrapperList() {
        ArrayList<PersonerFuncWrapper> PersonerFuncWrapperList = new ArrayList<>();
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_certification, "认证"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_switch,"身份切换"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_teacher_recruitment, "教师招募"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_advice_feedback,"建议反馈"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_contact, "联系客服"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_police, "紧急报警"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_about_us, "关于我们"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_software_updates, "检测更新"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_change_password, "修改密码"));
        PersonerFuncWrapperList.add(new PersonerFuncWrapper(R.mipmap.icon_my_cooperation, "商务合作"));
        return PersonerFuncWrapperList;
    }

}
