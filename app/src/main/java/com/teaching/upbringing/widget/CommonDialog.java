package com.teaching.upbringing.widget;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;

/**
 * @author bb
 * @time 2019/10/25 11:45
 * @des ${TODO}
 **/
public abstract class CommonDialog {

    protected Context mContext;
    protected FragmentManager mManager;
    protected CommonDialogFragment mDialog;
    protected BaseDialog mBaseDialog;
    protected View mInflate;

    public CommonDialog(Context context, FragmentManager manager) {
        this(context, manager, null);
    }

    public CommonDialog(Context context, FragmentManager manager, CommonDialogFragment.OnDialogCancelListener listener) {
        mContext = context;
        mManager = manager;
        mInflate = initView(context);
        ButterKnife.bind(this, mInflate);
        mBaseDialog = new BaseDialog(mContext);
        mBaseDialog.setContainerView(mInflate);
        mDialog = CommonDialogFragment.newInstance(context1 -> mBaseDialog, false, listener);
        setMore(mBaseDialog);
    }

    public void setCancelable(boolean can) {
        mDialog.setCancelable(can);
    }

    public void setLiftListener(CommonDialogFragment.DialogFragmentLiftListener liftListener) {
        mDialog.setLiftListener(liftListener);
    }

    /**
     * 初始化DialogView，如果没有重写该方法，会创建
     */
    protected abstract View initView(Context context);

    /**
     * 在显示前调用的方法
     */
    protected void perShow(){};

    /**
     * 设置修改更多参数
     */
    protected abstract void setMore(BaseDialog dialog);

    public BaseDialog getCoreDialog() {
        return mBaseDialog;
    }

    public DialogFragment getDialogFragment() {
        return mDialog;
    }

    public void setOnLeftConfirmListener(View.OnClickListener listener) {
        mBaseDialog.setOnClickCancelListener(listener);
    }

    public void setOnRightConfirmListener(View.OnClickListener listener) {
        mBaseDialog.setOnClickConfirmListener(listener);
    }

    public void hideLeftBtn(boolean isHide) {
        mBaseDialog.isHideCancel(isHide);
    }

    public void hideRightBtn(boolean isHide) {
        mBaseDialog.isHideConfirm(isHide);
    }

    public void hideBottomBtns(boolean ishide) {
        mBaseDialog.hideBottomBtns(ishide);
    }

    public void setLeftBtnText(String string) {
        mBaseDialog.setCancelText(string);
    }

    public void setRightBtnText(String string) {
        mBaseDialog.setConfirmText(string);
    }

    public void setBackgroundColor(int color) {
        mBaseDialog.setRootBackground(color);
    }

    public boolean isShowing() {
        return mBaseDialog.isShowing();
    }

    public void setAnimStyle(int style) {
        mBaseDialog.setAnimStyle(style);
    }

    protected Context getContext() {
        return mContext;
    }

    public void show() {
        perShow();
        mDialog.show(mManager, this.getClass().getSimpleName());
    }

    public void dismiss() {
        mDialog.dismiss();
    }
}
