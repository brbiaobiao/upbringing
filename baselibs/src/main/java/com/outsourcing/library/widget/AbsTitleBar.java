package com.outsourcing.library.widget;

import android.content.Context;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author: biao
 * @create: 2019/4/10
 * @Describe:
 */
public abstract class AbsTitleBar implements ITitleView {

    private final Context mContext;

    public AbsTitleBar(Context context) {
        mContext = context;
    }

    protected void init( View viewGroup) {
        ButterKnife.bind(this, viewGroup);
    }
}
