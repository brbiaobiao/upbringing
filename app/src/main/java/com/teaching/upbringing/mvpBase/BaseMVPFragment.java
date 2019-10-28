package com.teaching.upbringing.mvpBase;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lypeer.fcpermission.FcPermissions;
import com.outsourcing.library.mvp.MvpFragment;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ChenHh
 * @time 2019/10/27 13:17
 * @des baseFragment
 **/
public abstract class BaseMVPFragment<T extends IBasePresenter> extends MvpFragment<T> {
    protected View mRootView;

    protected boolean mIsInited = false;
    private Unbinder mUnbinder;
    protected Context mContext;
    private boolean mIsVisibleToUser;
    private boolean mIsViewCreated;
    private boolean mIsUserViewPager;

    protected T initPresenter() {
        return null;
    }

    protected abstract Integer getContentId();

    protected abstract void init();

    @Nullable
    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(initPresenter());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsViewCreated = true;
        mRootView = view;
        mUnbinder = ButterKnife.bind(this, view);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //第三方权限申请库
        FcPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onResume() {
        if (mIsUserViewPager) {
            if (!mIsInited &&mIsViewCreated&&mIsVisibleToUser) {
                executeInit();
            }
        }else {
            if (!mIsInited &&mIsViewCreated) {
                executeInit();
            }
        }
        super.onResume();
    }

    private void executeInit() {
        if (getPresenter() != null) {
            getPresenter().initPresenter();
        }
        init();
        if (getPresenter() != null) {
            getPresenter().onViewInited();
        }
        mIsInited = true;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mIsViewCreated = false;
        mIsInited = false;
        /*if (getPresenter() != null && getPresenter() instanceof Presenter) {
            ((Presenter) getPresenter()).mIsInited = false;
        }*/
    }

    /**
     * 使用viewpager进行显示的话调用此方法以正确懒加载
     */
    public void isUseViewPager() {
        mIsUserViewPager = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        if (!mIsInited && mIsVisibleToUser&&mIsViewCreated) {
            executeInit();
            mIsInited = true;
        }
    }
}
