package com.outsourcing.library.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public abstract class BottomBaseDialog<T extends BottomBaseDialog<T>> extends BottomTopBaseDialog {
    public BottomBaseDialog(Context context, View animateView) {
        super(context);
        this.animateView = animateView;

        innerShowAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0);

        innerDismissAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
    }

    public BottomBaseDialog(Context context) {
        this(context, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ll_top.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        ll_top.setGravity(Gravity.BOTTOM);
        getWindow().setGravity(Gravity.BOTTOM);
        ll_top.setPadding(left, top, right, bottom);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        showWithAnim();
    }


    @Override
    public void dismiss() {
        dismissWithAnim();
    }

}
