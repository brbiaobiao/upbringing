package com.teaching.upbringing.mvpBase;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.outsourcing.library.mvp.IProgressAble;
import com.outsourcing.library.mvp.MvpActivity;
import com.outsourcing.library.mvp.rxbase.IBasePresenter;
import com.outsourcing.library.mvp.rxbase.IContextView;
import com.outsourcing.library.utils.StatusBarUtil;
import com.outsourcing.library.widget.AbsTitleBar;
import com.teaching.upbringing.R;
import com.teaching.upbringing.manager.AppManager;
import com.teaching.upbringing.utils.ToastUtil;
import com.teaching.upbringing.widget.DefaultTitleBar;
import com.teaching.upbringing.widget.LoadingDialog;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;

/**
 * use to:
 */
public abstract class BaseMVPActivity<T extends IBasePresenter> extends MvpActivity<T> implements IContextView, IProgressAble {

    protected LoadingDialog mDialog;
    protected AbsTitleBar mTitleBar;

    private boolean isInited = false;
    private FrameLayout mFlTitleBarContent;

    /**
     * 返回视图ID
     */
    protected abstract Integer getContentId();

    /**
     * 初始化
     */
    protected abstract void init();

    protected T initPresenter() {
        return null;
    }

    /**
     * 如果这个activity是需要使用背景透明的theme,需要重写这个方法返回true,不然在android.O上会崩溃
     */
    protected boolean isTransparentActivity() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
        Log.e("当前页面", this.getClass().getSimpleName());
        setContentView(R.layout.activity_bar);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O&&!isTransparentActivity())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        View content = LayoutInflater.from(this).inflate(getContentId(), null);
        FrameLayout contentGroup = findViewById(R.id.fl_content);
        contentGroup.addView(content);
        mFlTitleBarContent = findViewById(R.id.fl_titleBar);
        mTitleBar = initTitleBar();
        if (mTitleBar != null) {
            mFlTitleBarContent.setVisibility(View.VISIBLE);
            mFlTitleBarContent.addView(mTitleBar.getTitleBar());
            mTitleBar.setTitleLeftTextClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        ButterKnife.bind(this);
        setPresenter(this.initPresenter());
        //初始化无网络布局
        //初始化无数据布局

        if (getFullFlag()) {
            ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View parentView = contentFrameLayout.getChildAt(0);
            if (parentView != null && Build.VERSION.SDK_INT >= 14) {
                parentView.setFitsSystemWindows(true);
            }
            StatusBarUtil.transparencyBar(this);
        }
        setImgTransparent(this);
    }

    protected AbsTitleBar initTitleBar() {
        return new DefaultTitleBar(this);
    }

    public void setImgTransparent(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 5.0以上
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 因为字体是白色所以 要设置黑色底
            window.setStatusBarColor(Color.parseColor("#55000000"));

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            // 透明状态栏后且不占用位置
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 6.0 以上可以设置字体为黑色.打开亮色状态栏模式,实现黑色字体  IView.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(Color.TRANSPARENT);
            // 设置小米字体为黑色
            StatusBarUtil.MIUISetStatusBarLightMode(window, true);
            // 白色字体
            //            window.getDecorView().setSystemUiVisibility(
            //                    IView.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | IView.SYSTEM_UI_FLAG_VISIBLE);
            //            window.setStatusBarColor(Color.parseColor("#55000000"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInited) {
            if (getPresenter() != null) {
                getPresenter().initPresenter();
            }
            init();
            if (getPresenter() != null) {
                getPresenter().onViewInited();
            }
            isInited = true;
        }
    }


    @Override
    protected void onDestroy() {
        AppManager.removeActivity(this);
        ToastUtil.cancelToast(this);
        super.onDestroy();
    }

    /**
     * 获取视图根布局
     *
     * @return
     */
    public ViewGroup getRootViewGroup() {
        return (ViewGroup) ((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0);
    }

    public boolean getFullFlag() {
        return true;
    }

    public BaseMVPActivity<T> setTitleText(String title) {
        mTitleBar.setTitleText(title);
        return this;
    }

    public BaseMVPActivity<T> setTitleRightIcon(Drawable drawable) {
        mTitleBar.setTitleRightIcon(drawable);
        return this;
    }

    public BaseMVPActivity<T> setTitleRightIcon(@DrawableRes int res) {
        mTitleBar.setTitleRightIcon(res);
        return this;
    }

    public BaseMVPActivity<T> setTitleLeftIcon(Drawable drawable) {
        mTitleBar.setTitleRightIcon(drawable);
        return this;
    }

    public BaseMVPActivity<T> setTitleLeftIcon(@DrawableRes int res) {
        mTitleBar.setTitleRightIcon(res);
        return this;
    }

    public BaseMVPActivity<T> setTitleRightIconClick(View.OnClickListener listener) {
        mTitleBar.setTitleRightIconClick(listener);
        return this;
    }


    public BaseMVPActivity<T> setTitleRightText(String text) {
        mTitleBar.setTitleRightText(text);
        return this;
    }

    public BaseMVPActivity<T> setTitleRightTextColor(int color) {
        mTitleBar.setTitleRightTextColor(color);
        return this;
    }

    public BaseMVPActivity<T> setTitleRightTextClick(View.OnClickListener listener) {
        mTitleBar.setTitleRightTextClick(listener);
        return this;
    }

    public BaseMVPActivity<T> isShowRightIcon(boolean visible) {
        mTitleBar.isShowRightIcon(visible);
        return this;
    }

    public BaseMVPActivity<T> isShowHeadDivider(boolean visible) {
        mTitleBar.isShowHeadDivider(visible);
        return this;
    }

    public BaseMVPActivity<T> isShowTitleRightText(boolean visible) {
        mTitleBar.isShowTitleRightText(visible);
        return this;
    }



    public TextView getTitleRightText() {
        return mTitleBar.getRightTextView();
    }

    public void hideHeadBar() {
        mFlTitleBarContent.setVisibility(View.GONE);
    }

    public View getHeadBar() {
        return mTitleBar.getTitleBar();
    }

    public View getTitleView() {
        return mTitleBar.getTitleView();
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress() {
        if (mDialog == null)
            mDialog = new LoadingDialog(getContext(), "加载中，请稍候...");
        mDialog.show();
    }

    @Override
    public void hideProgress() {
        if (mDialog != null)
            mDialog.dismiss();
    }
}
