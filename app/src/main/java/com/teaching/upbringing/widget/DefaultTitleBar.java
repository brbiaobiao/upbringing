package com.teaching.upbringing.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.outsourcing.library.widget.AbsTitleBar;
import com.teaching.upbringing.R;

import butterknife.BindView;

public class DefaultTitleBar extends AbsTitleBar {
    
    @BindView(R.id.actionbar_back)
    TextView mActionbarBack;
    @BindView(R.id.actionbar_title)
    TextView mActionbarTitle;
    @BindView(R.id.right_icon)
    ImageView mRightIcon;
    @BindView(R.id.actionbar_confirm)
    TextView mActionbarConfirm;
    @BindView(R.id.view_head_divider)
    View mViewHeadDivider;
    @BindView(R.id.rl_custom_actionbar_title)
    RelativeLayout mRlCustomActionbarTitle;
    
    private View mRootView;
    
    public DefaultTitleBar(Context context) {
        super(context);
        mRootView = LayoutInflater.from(context).inflate(R.layout.custom_actionbar_title_center, null);
        init(mRootView);
    }
    
    @Override
    public void setTitleText(String title) {
        mActionbarTitle.setText(title);
    }
    
    @Override
    public void setTitleRightIcon(Drawable drawable) {
        mRightIcon.setImageDrawable(drawable);
    }
    
    @Override
    public void setTitleRightIcon(int res) {
        mRightIcon.setImageResource(res);
    }
    
    @Override
    public void setTitleRightIconClick(View.OnClickListener listener) {
        mRightIcon.setOnClickListener(listener);
    }
    
    @Override
    public void setTitleRightText(String text) {
        mActionbarConfirm.setText(text);
    }
    
    @Override
    public void setTitleRightTextColor(int color) {
        mActionbarConfirm.setTextColor(color);
    }
    
    @Override
    public void setTitleRightTextClick(View.OnClickListener listener) {
        mActionbarConfirm.setOnClickListener(listener);
    }
    
    @Override
    public void isShowRightIcon(boolean visible) {
        mRightIcon.setVisibility(visible?View.VISIBLE:View.GONE);
    }
    
    @Override
    public void setTitleLeftTextClick(View.OnClickListener listener) {
        mActionbarBack.setOnClickListener(listener);
    }
    
    @Override
    public void isShowHeadDivider(boolean visible) {
        mViewHeadDivider.setVisibility(visible?View.VISIBLE:View.GONE);
    }
    
    @Override
    public void isShowTitleRightText(boolean visible) {
        mActionbarConfirm.setVisibility(visible?View.VISIBLE:View.GONE);
    }
    
    @Override
    public View getTitleBar() {
        return mRlCustomActionbarTitle;
    }
    
    @Override
    public View getTitleView() {
        return mActionbarTitle;
    }
    
    @Override
    public TextView getLeftTextView() {
        return mActionbarBack;
    }
    
    @Override
    public TextView getRightTextView() {
        return mActionbarConfirm;
    }
    
    @Override
    public View getLeftIconView() {
        return mActionbarBack;
    }
    
    @Override
    public View getRightIconView() {
        return mRightIcon;
    }
}
