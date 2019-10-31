package com.teaching.upbringing.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.outsourcing.library.utils.AppUtils;
import com.outsourcing.library.utils.DensityUtils;
import com.outsourcing.library.utils.KeyboardUtils;
import com.outsourcing.library.utils.NavigationBarUtils;
import com.outsourcing.library.utils.ScreenUtils;
import com.outsourcing.library.utils.UIUtils;
import com.teaching.upbringing.R;
import com.outsourcing.library.utils.StatusBarUtils;

/**
 * @author bb
 * @time 2019/10/25 11:48
 * @des ${TODO}
 **/
public final class BaseDialog extends Dialog {
    private Object tag;
    protected Context context;
    private FrameLayout container;
    private View rootView;
    private TextView tvCancel;
    private TextView tvConfirm;

    private int width = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;
    private LinearLayout llBtns;
    private ViewGroup mRoot;
    private TextView mTvTitle;
    private View mDivider;
    private View mTitleDivider;
    private View viewLldivider;
    private float mWidthScale = 1f;

    public BaseDialog(Context context) {
        super(context, R.style.baseDialog);
        this.context = context;
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                KeyboardUtils.hideSoftInput(tvConfirm.getContext(), tvConfirm);
            }
        });
        initView();
        //        getWindow().setWindowAnimations(R.style.Dialog_Anim_Style);

        setCanceledOnTouchOutside(false);//外部点击不取消,2017.08.21 by-ljj
    }

    public Context getMContext() {
        return context;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    private void initView() {
        width = DensityUtils.dp2px(context, 300);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
        assignViews();
        setContentView(rootView);
    }


    private void assignViews() {
        rootView = LayoutInflater.from(context).inflate(R.layout.dialog_base_default, null);
        mRoot = (ViewGroup) rootView.findViewById(R.id.root);
        container = (FrameLayout) rootView.findViewById(R.id.container);

        mTvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        tvCancel = (TextView) rootView.findViewById(R.id.tv_pos_btn);
        tvConfirm = (TextView) rootView.findViewById(R.id.tv_neg_btn);
        llBtns = (LinearLayout) rootView.findViewById(R.id.ll_btns);
        mDivider = rootView.findViewById(R.id.view_divider);
        mTitleDivider = rootView.findViewById(R.id.view_title_divider);
        viewLldivider = rootView.findViewById(R.id.view_lldivider);
        tvConfirm.setTextColor(AppUtils.getColor(R.color.text_black));
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //        setRadius(5);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        KeyboardUtils.hideSoftInput(rootView.getContext(), rootView);
    }

    public void setSize(int width, int height) {
        //        if (width >= 100) {
        this.width = width;
        //        }
        //        if (height >= 100) {
        this.height = height;
    }

    public void setRootPadding(int padding) {
        container.setPadding(padding, padding, padding, padding);
    }

    public void setRootPadding(int left, int top, int right, int buttom) {
        container.setPadding(left, top, right, buttom);
    }

   /* public void setRadius(float radius){
        mRoot.setRectAdius(radius);
    }*/

    public void setOnClickConfirmListener(View.OnClickListener onClickListener) {
        tvConfirm.setOnClickListener(onClickListener);
    }

    public void setOnClickCancelListener(View.OnClickListener onClickListener) {
        tvCancel.setOnClickListener(onClickListener);
    }

    public void setRootBackground(int color) {
        mRoot.setBackgroundColor(color);
    }

    public void setRootBackgroundResource(int res) {
        mRoot.setBackgroundResource(res);
    }


    public void setTitleText(String s) {
        mTvTitle.setText(s);
    }

    public void setConfirmText(String s) {
        tvConfirm.setText(s);
    }

    public void setConfirmColor(int c) {
        tvConfirm.setTextColor(c);
    }

    public void setCancelText(String s) {
        tvCancel.setText(s);
    }

    public void setCancelColor(int c) {
        tvCancel.setTextColor(c);
    }

    public void isHideTitle(boolean isHide) {
        isHide(mTvTitle, isHide);
        isHide(mTitleDivider, isHide);
    }

    public void hideBottomBtns(boolean isHide) {
        isHide(viewLldivider, isHide);
        isHide(tvConfirm, isHide);
        isHide(tvCancel, isHide);
        isHide(mDivider, isHide);
    }

    public void isHideConfirm(boolean isHide) {
        isHide(tvConfirm, isHide);
    }

    public void isHideCancel(boolean isHide) {
        isHide(tvCancel, isHide);
    }

    public void isHideDivider(boolean isHide) {
        isHide(mDivider, isHide);
    }

    public void isHideCenterDivider(boolean isHide) {
        isHide(viewLldivider, isHide);
    }

    private void isHide(View textView, boolean isHide) {
        if (isHide) {
            textView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }

    public void setContainerView(View view) {
        if (view == null) {
            return;
        }

        container.removeAllViews();
        container.addView(view);
    }

    public void setContainerView(View view, RelativeLayout.LayoutParams params) {
        if (view == null) {
            return;
        }

        container.removeAllViews();
        container.addView(view, params);
    }

    private void removeRootViewParent() {
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
    }

    public void setEnabledConfirm(boolean isEnabled) {
        tvConfirm.setEnabled(isEnabled);
        tvConfirm.setSelected(isEnabled);
    }

    @Override
    public void show() {
        super.show();
        if (mWidthScale != 1) {
            scaleWindowMatchHeight();
            return;
        }
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams params = null;
        if (dialogWindow != null) {
            params = dialogWindow.getAttributes();
            params.height = height;
            if (height == ViewGroup.LayoutParams.MATCH_PARENT) {
                if (UIUtils.isAllScreenDevice(getContext())) {//全面屏
                    int statusHeight = StatusBarUtils.getHeight(context);
                    params.height = ScreenUtils.getRealHeight() - statusHeight;
                }
                //减去虚拟键
                if (NavigationBarUtils.realHasNavigationBar(context)) {
                    int virtualBarHeight = NavigationBarUtils.getVirtualBarHeight(context);
                    params.height -= virtualBarHeight;
                }
            }
            params.width = width;
            dialogWindow.setAttributes(params);
        }
    }

    private void scaleWindowMatchHeight() {
        Window win = this.getWindow();
        if (win == null) {
            return;
        }
        win.setWindowAnimations(R.style.Dialog_animm);
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = win.getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth() * mWidthScale);

        int statusHeight = StatusBarUtils.getHeight(context);
        if (height == ViewGroup.LayoutParams.MATCH_PARENT) {
            params.height = ScreenUtils.getRealHeight();
            params.height -= statusHeight;
            /*if (UIUtils.isAllScreenDevice(getContext())) {//全面屏
                LogUtils.v("params.height:"+params.height);
                LogUtils.v( "全面屏-statusHeight:" + statusHeight);
                params.height -= statusHeight;
                LogUtils.v("params.height:"+params.height);
            }*/
            //减去虚拟键
            if (NavigationBarUtils.realHasNavigationBar(context)) {
                int virtualBarHeight = NavigationBarUtils.getVirtualBarHeight(context);
                params.height -= virtualBarHeight;
            }
        } else {
            params.height = ScreenUtils.getRealHeight() - statusHeight;
        }

        win.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        win.setAttributes(params);
    }

    public void setAnimStyle(int style) {
        Window dialogWindow = getWindow();
        if (dialogWindow == null) return;
        dialogWindow.setWindowAnimations(style);
    }

    protected void setBtnsDismiss() {
        llBtns.setVisibility(View.GONE);
        isHideDivider(true);
    }

    public void setConfirmTextColor(int color) {
        tvConfirm.setTextColor(color);
    }

    public View getRootView() {
        return rootView;
    }

    /**
     * 设置满屏幕高度而且又宽度带比例
     */
    public void setScreenWidthScaleFullHeight(float scale) {
        mWidthScale = scale;
    }
}
